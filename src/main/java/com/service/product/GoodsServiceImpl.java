package com.service.product;

import com.form.product.GoodsForm;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mapper.order.OrderMapper;
import com.mapper.product.GoodsImgMapper;
import com.mapper.product.GoodsMapper;
import com.model.product.*;
import com.result.Page;
import com.result.Result;
import com.result.ResultStatus;
import com.utils.CommonUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 商品 操作 业务逻辑层
 */
@Service
public class GoodsServiceImpl implements GoodsService{
    @Resource
    GoodsMapper goodsMapper;
    @Resource
    GoodsImgMapper goodsImgMapper;
    @Resource
    OrderMapper orderMapper;

    /**
     * 根据id查询 商品 详情信息
     * @param form
     * @return
     */
    @Override
    public Result findById(GoodsForm.findByIdForm form) {
        //查询商品
        Goods goods = goodsMapper.selectByPrimaryKey(form.getGoodsId());

        if(goods!=null){
            //查询图片
            GoodsImgExample imgExample = new GoodsImgExample();
            imgExample.createCriteria().andGoodsIdEqualTo(goods.getGoodsId());
            List<GoodsImg> goodsImgs = goodsImgMapper.selectByExample(imgExample);
            goods.setImgUrls( convertImg(goodsImgs) );
        }


        return Result.success(goods);
    }

    /**
     * 查询满足条件的 商品 列表
     * @param form
     * @return
     */
    @Override
    public Result list(GoodsForm.listForm form) {

        GoodsExample example = new GoodsExample();
        GoodsExample.Criteria criteria = example.createCriteria();

        // 筛选条件
        //1、商品名
		if(!StringUtils.isEmpty( form.getGoodsName() )){
			criteria.andGoodsNameLike("%"+form.getGoodsName()+"%");
		}
		//2、商品分类
		if(!StringUtils.isEmpty(form.getClassify())){
            criteria.andClassifyEqualTo(form.getClassify());
        }

        // 排序
        if(!StringUtils.isEmpty(form.getOrderByClause())){
            example.setOrderByClause(form.getOrderByClause());
        }

        // 使用PageHelper插件分页
        PageHelper.startPage(form.getPage(),form.getLimit());
        List<Goods> goodsList = goodsMapper.selectByExample(example);

        //分页信息
        PageInfo<Goods> pageInfo = new PageInfo<>(goodsList);
        Page page = form.pageHelperResult(pageInfo);

        //查询商品图片
        for (Goods goods : goodsList) {
            GoodsImgExample imgExample = new GoodsImgExample();
            imgExample.createCriteria().andGoodsIdEqualTo(goods.getGoodsId());
            List<GoodsImg> goodsImgs = goodsImgMapper.selectByExample(imgExample);

            goods.setImgUrls( convertImg(goodsImgs) );
        }

        return Result.success(goodsList,page);
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
     * 新增 商品
     * @param form
     * @return
     */
    @Override
    @Transactional
    public Result add(GoodsForm.addForm form) {

//        判断库存
        if(form.getStock()<=0){
            return Result.fail(ResultStatus.ERROR_Parameter_Stock);
        }
        Goods goods = new Goods();

        //入参转实体对象
        BeanUtils.copyProperties(form,goods);

        //设置创建信息
        String id = CommonUtil.getLongId();
        goods.setGoodsId(id);
        goods.setUpdateTime(new Date());

        //保存图片
        for (String imgUrl : form.getImgUrls()) {
            //图片实体对象
            GoodsImg img = new GoodsImg();
            img.setImgUrl(imgUrl);
            img.setGoodsId(id);

            //存入数据库
            goodsImgMapper.insertSelective(img);
        }


        //插入数据库
        int num = goodsMapper.insertSelective(goods);
        if(num>0){
            return Result.success(num);
        }

        return Result.fail(ResultStatus.ERROR_Add);
    }

    /**
     * 根据id，修改 商品
     * @param form
     * @return
     */
    @Override
    public Result update(GoodsForm.updateForm form) {
        Goods goods = new Goods();

        //入参转实体对象
        BeanUtils.copyProperties(form,goods);

        //设置更新信息
        goods.setUpdateTime(new Date());

        //如果修改图片
        if (!form.getImgUrls().isEmpty()){
            //删除原图片
            GoodsImgExample example = new GoodsImgExample();
            example.createCriteria().andGoodsIdEqualTo(form.getGoodsId());
            goodsImgMapper.deleteByExample(example);


            //保存新图片
            for (String imgUrl : form.getImgUrls()) {
                //图片实体对象
                GoodsImg img = new GoodsImg();
                img.setImgUrl(imgUrl);
                img.setGoodsId(form.getGoodsId());

                //存入数据库
                goodsImgMapper.insertSelective(img);
            }

        }

        //更新数据库
        int num = goodsMapper.updateByPrimaryKeySelective(goods);
        if(num>0){
            return Result.success(num);
        }

        return Result.fail(ResultStatus.ERROR_Update);
    }

    /**
     * 根据id，删除 商品，可批量删除，多个id逗号分隔
     * @param form
     * @return
     */
    @Override
    @Transactional
    public Result delete(GoodsForm.deleteForm form) {
   
        for (String id : form.getIds()) {
            goodsMapper.deleteByPrimaryKey(id);
          
        }

        return Result.success(1);
    }

    @Override
    public Result hotProduct() {

        ArrayList<Goods> goodsList = new ArrayList<>();

//        得到前三的商品id
        List<String> GoodsIds = orderMapper.selectGoodsIdsByCountTop();
        for(String goodsId:GoodsIds){

            Goods goods = goodsMapper.selectByPrimaryKey(goodsId);
            GoodsImgExample imgExample = new GoodsImgExample();
            imgExample.createCriteria().andGoodsIdEqualTo(goodsId);
            List<GoodsImg> goodsImgs = goodsImgMapper.selectByExample(imgExample);
            goods.setImgUrls( convertImg(goodsImgs) );

            goodsList.add(goods);
        }

        return Result.success(goodsList);
    }

    @Override
    public Result hotOrderProduct() {

        ArrayList<Goods> goodsList = new ArrayList<>();

//        得到订单量前十的商品id
        List<String> GoodsIds = orderMapper.selectByCount();
        for(String goodsId:GoodsIds){

            Goods goods = goodsMapper.selectByPrimaryKey(goodsId);
            GoodsImgExample imgExample = new GoodsImgExample();
            imgExample.createCriteria().andGoodsIdEqualTo(goodsId);
            List<GoodsImg> goodsImgs = goodsImgMapper.selectByExample(imgExample);
            goods.setImgUrls( convertImg(goodsImgs) );

            goodsList.add(goods);
        }
        return Result.success(goodsList);
    }

    @Override
    public Result newProduct() {

        List<Goods> goodsList = goodsMapper.newProduct();
        //查询商品图片
        for (Goods goods : goodsList) {
            GoodsImgExample imgExample = new GoodsImgExample();
            imgExample.createCriteria().andGoodsIdEqualTo(goods.getGoodsId());
            List<GoodsImg> goodsImgs = goodsImgMapper.selectByExample(imgExample);

            goods.setImgUrls( convertImg(goodsImgs) );
        }

        return Result.success(goodsList);
    }


}
