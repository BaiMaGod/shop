package com.controller.product;

import com.form.product.GoodsForm;
import com.form.product.ScenicSpotForm;
import com.result.Result;
import com.service.product.GoodsService;
import com.service.product.ScenicSpotService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
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

    @ApiOperation(value = "根据id查询景点详情信息",notes = "根据id查询 景点 详情信息")
    @GetMapping("/findById")
    public Result findById(@Valid GoodsForm.findByIdForm form){

        return goodsService.findById(form);
    }

    @ApiOperation(value = "查询景点列表",notes = "查询满足条件的 景点 列表")
    @GetMapping("/list")
    public Result list(@Valid GoodsForm.listForm form){

        return goodsService.list(form);
    }

    @ApiOperation(value = "新品上架推荐",notes = "上架时间倒序前十的商品")
    @GetMapping
    public Result newProduct(){
        return goodsService.newProduct();
    }

}
