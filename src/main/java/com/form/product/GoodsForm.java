package com.form.product;

import com.form.PageForm;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class GoodsForm {

    @Data
    public static class findByIdForm{
        @ApiModelProperty(value = "goodsId",required = true)
        @NotNull(message = "goodsId 不能为空")
        private String goodsId;

    }

    @Data
    public static class listForm extends PageForm{
		@ApiModelProperty(value = "商品名称,模糊匹配")
		private String goodsName;
		@ApiModelProperty(value = "商品分类,精准查询")
		private String classify;

    }

    @Data
    public static class addForm {
		@ApiModelProperty(value = "商品名称",required = true)
		@NotBlank(message = "商品名称 不能为空")
		private String goodsName;
		@ApiModelProperty(value = "商品简介",required = true)
		@NotBlank(message = "商品简介 不能为空")
		private String introduction;
		@ApiModelProperty(value = "商品分类",required = true)
		@NotBlank(message = "商品分类 不能为空")
		private String classify;
		@ApiModelProperty(value = "商品价格",required = true)
		@NotNull(message = "商品价格 不能为空")
		private BigDecimal price;
		@ApiModelProperty(value = "说明信息",required = true)
		@NotBlank(message = "说明信息 不能为空")
		private String information;
		@ApiModelProperty(value = "库存",required = true)
		@NotNull(message = "库存 不能为空")
		private Integer stock;
		@ApiModelProperty(value = "图片url，多个用逗号隔开")
		private List<String> imgUrls = new ArrayList<>();



    }

    @Data
    public static class updateForm {
		@ApiModelProperty(value = "商品id",required = true)
		@NotBlank(message = "商品id 不能为空")
		private String goodsId;
		@ApiModelProperty(value = "商品名称")
		private String goodsName;
		@ApiModelProperty(value = "商品简介")
		private String introduction;
		@ApiModelProperty(value = "商品分类")
		private String classify;
		@ApiModelProperty(value = "商品价格")
		private BigDecimal price;
		@ApiModelProperty(value = "说明信息")
		private String information;

		@ApiModelProperty(value = "图片url，多个用逗号隔开")
		private List<String> imgUrls = new ArrayList<>();

    }

    @Data
    public static class deleteForm {
        @ApiModelProperty(value = "id,多个id用逗号分隔",required = true)
        @NotEmpty(message = "至少传入一个id")
        private List<String> ids;

    }
}
