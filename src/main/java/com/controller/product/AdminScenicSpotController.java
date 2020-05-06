package com.controller.product;

import com.form.product.ScenicSpotForm;
import com.result.Result;
import com.service.product.ScenicSpotService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Api(value = "管理端-景点 操作接口",tags = {"管理端-景点 操作接口"},hidden = true)
@RestController
@RequestMapping("json/admin/scenicSpot")
public class AdminScenicSpotController {
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

    @ApiOperation(value = "新增景点",notes = "新增 景点",hidden = true)
    @PostMapping("/add")
    public Result add(@Valid ScenicSpotForm.addForm form){

        return scenicSpotService.add(form);
    }

    @ApiOperation(value = "修改景点",notes = "根据id修改 景点",hidden = true)
    @PutMapping("/update")
    public Result update(@Valid ScenicSpotForm.updateForm form){

        return scenicSpotService.update(form);
    }

    @ApiOperation(value = "删除景点",notes = "根据id 删除 景点，可批量删除，多个id逗号分隔",hidden = true)
    @DeleteMapping("/delete")
    public Result delete(@Valid ScenicSpotForm.deleteForm form){

        return scenicSpotService.delete(form);
    }
}
