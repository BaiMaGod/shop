package com.form.product;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import java.util.List;

public class CartForm {


    @Data
    public static class addForm {
        @ApiModelProperty(value = "商品id",required = true)
        @NotBlank(message = "商品id 不能为空")
        private String goodsId;


    }


    @Data
    public static class deleteForm {
        @ApiModelProperty(value = "id,多个id用逗号分隔",required = true)
        @NotEmpty(message = "至少传入一个id")
        private List<Integer> ids;

    }
}
