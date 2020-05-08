package com.controller.order;

import com.form.order.OrderForm;
import com.result.Result;
import com.service.order.OrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Api(value = "管理端-订单 操作接口",tags = {"管理端-订单 操作接口"})
@RestController
@RequestMapping("json/admin/order")
public class AdminOrderController {
    @Autowired
    OrderService orderService;

    @ApiOperation(value = "根据id查询订单详情信息",notes = "根据id查询 订单 详情信息")
    @GetMapping("/findById")
    public Result findById(@Valid OrderForm.findByIdForm form){

        return orderService.findById(form);
    }

    @ApiOperation(value = "查询订单列表",notes = "查询满足条件的 订单 列表")
    @GetMapping("/list")
    public Result list(@Valid OrderForm.listForm form){

        return orderService.list(form);
    }

}
