package com.controller.product;

import com.form.product.CartForm;
import com.result.Result;
import com.service.product.CartService;
import com.utils.UserUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Api(value = "用户端-购物车 操作接口",tags = {"用户端-购物车 操作接口"})
@RestController
@RequestMapping("json/user/cart")
public class CartController {
    @Autowired
    CartService cartService;


    @ApiOperation(value = "查询我的购物车列表",notes = "查询我的购物车列表")
    @GetMapping("/list")
    public Result list(){

        return cartService.list();
    }

    @ApiOperation(value = "新增购物车商品",notes = "新增 购物车商品")
    @PostMapping("/add")
    public Result add(@Valid CartForm.addForm form){

        return cartService.add(form);
    }


    @ApiOperation(value = "删除购物车商品",notes = "根据id 删除 购物车商品，可批量删除，多个id逗号分隔")
    @DeleteMapping("/delete")
    public Result delete(@Valid CartForm.deleteForm form){

        return cartService.delete(form);
    }
}
