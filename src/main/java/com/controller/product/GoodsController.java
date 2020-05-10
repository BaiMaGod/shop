package com.controller.product;

import com.form.product.GoodsForm;
import com.result.Result;
import com.service.product.GoodsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Api(value = "用户端-商品 操作接口",tags = {"用户端-商品 操作接口"})
@RestController
@RequestMapping("json/goods")
public class GoodsController {

    @Autowired
    GoodsService goodsService;

    @ApiOperation(value = "根据id查询商品详情信息",notes = "根据id查询 商品 详情信息")
    @GetMapping("/findById")
    public Result findById(@Valid GoodsForm.findByIdForm form){

        return goodsService.findById(form);
    }

    @ApiOperation(value = "查询商品列表",notes = "查询满足条件的 商品 列表（商品名模糊查询 商品分类精准查询）")
    @GetMapping("/list")
    public Result list(@Valid GoodsForm.listForm form){

        return goodsService.list(form);
    }

    @ApiOperation(value = "首页-轮播商品推荐",notes = "订单金额前三的商品，仅交易成功的订单")
    @GetMapping("/hotProduct")
    public Result hotProduct(){
        return goodsService.hotProduct();
    }

    @ApiOperation(value = "首页-热销商品推荐",notes = "订单量前十的商品，包含所以交易类型的订单")
    @GetMapping("/hotOrderProduct")
    public Result hotOrderProduct(){
        return goodsService.hotOrderProduct();
    }

    @ApiOperation(value = "首页-新品上架推荐",notes = "上架时间倒序前十的商品")
    @GetMapping("/newProduct")
    public Result newProduct(){
        return goodsService.newProduct();
    }

}
