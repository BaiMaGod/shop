package com.service.order;

import cn.hutool.core.util.NumberUtil;
import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.config.AlipayConfig;
import com.form.order.AlipayForm;
import com.form.order.OrderForm;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mapper.order.OrderMapper;
import com.mapper.order.OrderMapperExt;
import com.mapper.product.GoodsImgMapper;
import com.mapper.product.GoodsMapper;
import com.model.order.Order;
import com.model.order.OrderExample;
import com.model.product.*;
import com.result.Page;
import com.result.Result;
import com.result.ResultStatus;
import com.utils.CommonUtil;
import com.utils.UserUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * 订单 操作 业务逻辑层
 */
@Service
@Transactional
public class OrderServiceImpl implements OrderService{
    @Resource
    OrderMapper orderMapper;
    @Resource
    OrderMapperExt orderMapperExt;

    @Resource
    GoodsMapper goodsMapper;
    @Resource
    GoodsImgMapper goodsImgMapper;

    /**
     * 根据id查询 订单 详情信息
     * @param form
     * @return
     */
    @Override
    public Result findById(OrderForm.findByIdForm form) {

        Order order = orderMapper.selectByPrimaryKey(form.getId());
        if(order!=null){
            // 转换商品信息
            order.setGoods(JSONObject.parseObject(order.getGoodsInfo(), Goods.class));
        }

        return Result.success(order);
    }

    /**
     * 查询满足条件的 订单 列表
     * @param form
     * @return
     */
    @Override
    public Result list(OrderForm.listForm form) {
        OrderExample example = new OrderExample();
        OrderExample.Criteria criteria = example.createCriteria();

        // 筛选条件
		if(!StringUtils.isEmpty( form.getGoodsInfo() )){
			criteria.andGoodsInfoLike("%"+form.getGoodsInfo()+"%");
		}
        if(form.getUserId()!=null){
            criteria.andUserIdEqualTo(form.getUserId());
        }


        // 排序
        if(!StringUtils.isEmpty(form.getOrderByClause())){
            example.setOrderByClause(form.getOrderByClause());
        }

        // 使用PageHelper插件分页
        PageHelper.startPage(form.getPage(),form.getLimit());
        List<Order> orderList = orderMapper.selectByExample(example);

        //分页信息
        PageInfo<Order> pageInfo = new PageInfo<>(orderList);
        Page page = form.pageHelperResult(pageInfo);

        // 转换商品信息
        for (Order order : orderList) {
            order.setGoods(JSONObject.parseObject(order.getGoodsInfo(), Goods.class));
        }

        return Result.success(orderList,page);
    }

    /**
     * 新增 订单
     * @param form
     * @return
     */
    @Override
    public Result add(OrderForm.addForm form,HttpServletResponse httpResponse) throws IOException {
        Order order = new Order();

        //首先查出商品信息
        Goods goods = goodsMapper.selectByPrimaryKey(form.getGoodsId());
        if(goods==null){
            return Result.fail(ResultStatus.ERROR_Add.getCode(),"商品不存在");
        }

        //查询图片
        GoodsImgExample imgExample = new GoodsImgExample();
        imgExample.createCriteria().andGoodsIdEqualTo(goods.getGoodsId());
        List<GoodsImg> goodsImgs = goodsImgMapper.selectByExample(imgExample);
        goods.setImgUrls( convertImg(goodsImgs) );

        //库存控制,如果库存数量小于 购买数量,则不能下单，返回错误提示
        if (goods.getStock() == null || goods.getStock() < form.getNumber()){
            return Result.fail(ResultStatus.ERROR_Add.getCode(),"库存不足");
        }

        //入参转实体对象
        BeanUtils.copyProperties(form,order);

        //设置创建信息
        order.setOrderId(CommonUtil.getLongId());
        order.setCreateTime(new Date());
        order.setUserId(UserUtil.getUserInfo().getUserId());
        order.setGoods(goods);
        order.setGoodsInfo(JSONObject.toJSONString(goods));
        order.setState("待付款");

        //计算总金额：票价*数量
        BigDecimal money = goods.getPrice().multiply(NumberUtil.toBigDecimal(form.getNumber() + ""));
        order.setMoney(money);

        //使用支付宝支付
        alipay(order,httpResponse);

        //修改库存
        orderMapperExt.reduceStock(form);
        //插入数据库
        orderMapper.insertSelective(order);

        return null;
    }

    /**
     * 提取商品url
     * @param goodsImgs
     * @return
     */
    public List<String> convertImg(List<GoodsImg> goodsImgs){
        List<String> result = new ArrayList<>();

        for (GoodsImg goodsImg : goodsImgs) {
            result.add(goodsImg.getImgUrl());
        }

        return result;
    }



    /**
     * 修改订单状态
     * @param form
     */
    @Override
    public void updateState(AlipayForm form) {
        Order order = new Order();

        //订单id
        order.setOrderId(form.getOut_trade_no());
        //如果支付宝交易成功，则修改订单状态为订单完成
        if("TRADE_SUCCESS".equals(form.getTrade_status())){
            order.setState("订单完成");
        }else {
            order.setState("支付失败");
        }


        orderMapper.updateByPrimaryKeySelective(order);
    }


    public void alipay(Order order,HttpServletResponse httpResponse) throws IOException {
        Random r=new Random();
        //实例化客户端,填入所需参数
        AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.GATEWAY_URL, AlipayConfig.APP_ID, AlipayConfig.APP_PRIVATE_KEY, AlipayConfig.FORMAT, AlipayConfig.CHARSET, AlipayConfig.ALIPAY_PUBLIC_KEY, AlipayConfig.SIGN_TYPE);
        AlipayTradePagePayRequest request = new AlipayTradePagePayRequest();
        //在公共参数中设置回跳和通知地址
        request.setReturnUrl(AlipayConfig.RETURN_URL);
        request.setNotifyUrl(AlipayConfig.NOTIFY_URL);

        //商户订单号，商户网站订单系统中唯一订单号，必填
        //订单Id
        String out_trade_no = order.getOrderId();
        //付款金额，必填
        String total_amount = order.getMoney().toString();
        //订单名称，必填
        String subject = order.getGoods().getGoodsName();
        //商品描述，可空
        String body = order.getGoods().getIntroduction();
        request.setBizContent("{\"out_trade_no\":\""+ out_trade_no +"\","
                + "\"total_amount\":\""+ total_amount +"\","
                + "\"subject\":\""+ subject +"\","
                + "\"body\":\""+ body +"\","
                + "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");
        String form = "";
        try {
            form = alipayClient.pageExecute(request).getBody(); // 调用SDK生成表单
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        httpResponse.setContentType("text/html;charset=" + AlipayConfig.CHARSET);
        httpResponse.getWriter().write(form);// 直接将完整的表单html输出到页面
        httpResponse.getWriter().flush();
        httpResponse.getWriter().close();
    }

}
