package com.controller.product;

import com.form.product.GoodsForm;
import com.result.Result;
import com.service.product.GoodsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Api(value = "管理端-商品 操作接口",tags = {"管理端-商品 操作接口"})
@RestController
@RequestMapping("json/admin/goods")
public class AdminGoodsController {
    @Autowired
    GoodsService goodsService;

    @ApiOperation(value = "根据id查询商品详情信息",notes = "根据id查询 商品 详情信息")
    @GetMapping("/findById")
    public Result findById(@Valid GoodsForm.findByIdForm form){

        return goodsService.findById(form);
    }

    @ApiOperation(value = "查询商品列表",notes = "查询满足条件的 商品 列表")
    @GetMapping("/list")
    public Result list(@Valid GoodsForm.listForm form){

        return goodsService.list(form);
    }

    @ApiOperation(value = "新增商品",notes = "新增 商品")
    @PostMapping("/add")
    public Result add(@Valid GoodsForm.addForm form){

        return goodsService.add(form);
    }

    @ApiOperation(value = "修改商品",notes = "根据id修改 商品")
    @PutMapping("/update")
    public Result update(@Valid GoodsForm.updateForm form){

        return goodsService.update(form);
    }

    @ApiOperation(value = "删除商品",notes = "根据id 删除 商品，可批量删除，多个id逗号分隔")
    @DeleteMapping("/delete")
    public Result delete(@Valid GoodsForm.deleteForm form){

        return goodsService.delete(form);
    }
}
