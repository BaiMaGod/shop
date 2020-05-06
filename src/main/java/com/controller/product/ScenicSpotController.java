package com.controller.product;

import com.form.product.ScenicSpotForm;
import com.result.Result;
import com.service.product.ScenicSpotService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Api(value = "用户端-景点 操作接口",tags = {"用户端-景点 操作接口"},hidden = true)
@RestController
@RequestMapping("json/scenicSpot")
public class ScenicSpotController {
    @Autowired
    ScenicSpotService scenicSpotService;

    @ApiOperation(value = "根据id查询景点详情信息",notes = "根据id查询 景点 详情信息",hidden = true)
    @GetMapping("/findById")
    public Result findById(@Valid ScenicSpotForm.findByIdForm form){

        return scenicSpotService.findById(form);
    }

    @ApiOperation(value = "查询景点列表",notes = "查询满足条件的 景点 列表",hidden = true)
    @GetMapping("/list")
    public Result list(@Valid ScenicSpotForm.listForm form){

        return scenicSpotService.list(form);
    }

}
