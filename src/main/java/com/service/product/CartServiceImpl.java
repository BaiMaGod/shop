package com.service.product;

import com.form.product.CartForm;
import com.mapper.product.CartMapper;
import com.mapper.product.GoodsImgMapper;
import com.mapper.product.GoodsMapper;
import com.model.product.*;
import com.result.Result;
import com.result.ResultStatus;
import com.utils.UserUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 购物车 操作 业务逻辑层
 */
@Service
public class CartServiceImpl implements CartService{
    @Resource
    CartMapper cartMapper;
    @Resource
    GoodsMapper goodsMapper;
    @Resource
    GoodsImgMapper goodsImgMapper;

    /**
     * 查询我的购物车商品列表
     * @return
     */
    @Override
    public Result list() {
        //sql构造器
        CartExample example = new CartExample();
        CartExample.Criteria criteria = example.createCriteria();

        // 筛选条件，查询指定用户id的购物车商品
        criteria.andUserIdEqualTo(UserUtil.getUserInfo().getUserId());

        // 排序，id倒序
        example.setOrderByClause("id desc");

        //查询数据库
        List<Cart> cartList = cartMapper.selectByExample(example);

        //循环查询商品信息
        for (Cart cart : cartList) {
            //查询商品
            Goods goods = goodsMapper.selectByPrimaryKey(cart.getGoodsId());

            //如果商品信息存在，则查询图片
            if(goods!=null){
                //查询图片
                GoodsImgExample imgExample = new GoodsImgExample();
                imgExample.createCriteria().andGoodsIdEqualTo(goods.getGoodsId());
                List<GoodsImg> goodsImgs = goodsImgMapper.selectByExample(imgExample);
                goods.setImgUrls( convertImg(goodsImgs) );
            }
            //商品不存在，说明被删除，则删除购物车记录
            else {
                cartMapper.deleteByPrimaryKey(cart.getId());
            }

            cart.setGoods(goods);
        }

        return Result.success(cartList);
    }

    /**
     * 新增 购物车商品
     * @param form
     * @return
     */
    @Override
    @Transactional
    public Result add(CartForm.addForm form) {
        Cart cart = new Cart();

        //设置商品id
        cart.setGoodsId(form.getGoodsId());
        //设置用户id
        cart.setUserId(UserUtil.getUserInfo().getUserId());


        //删除以前加入过购物车的同一个商品
        deleteGoods(cart.getGoodsId(),cart.getUserId());

        //插入数据库
        int num = cartMapper.insertSelective(cart);
        if(num>0){
            return Result.success(num);
        }

        return Result.fail(ResultStatus.ERROR_Add);
    }

    //删除指定用户存在购物车中的指定商品
    public void deleteGoods(String goodsId, Integer userId) {
        //sql构造器
        CartExample example = new CartExample();
        CartExample.Criteria criteria = example.createCriteria();

        //构造where条件
        criteria.andGoodsIdEqualTo(goodsId).andUserIdEqualTo(userId);

        //删除数据库
        cartMapper.deleteByExample(example);
    }


    /**
     * 根据id，删除 购物车商品，可批量删除，多个id逗号分隔
     * @param form
     * @return
     */
    @Override
    @Transactional
    public Result delete(CartForm.deleteForm form) {
   
        for (Integer id : form.getIds()) {
            cartMapper.deleteByPrimaryKey(id);
          
        }

        return Result.success(1);
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
}
