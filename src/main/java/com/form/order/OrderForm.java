package com.form.order;

import com.form.PageForm;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class OrderForm {

    @Data
    public static class findByIdForm{
        @ApiModelProperty(value = "id",required = true)
        @NotNull(message = "id 不能为空")
        private String id;

    }

    @Data
    public static class listForm extends PageForm{
		@ApiModelProperty(value = "商品信息,模糊匹配")
		private String goodsInfo;

		@ApiModelProperty(value = "用户id,模糊匹配",hidden = true)
		private Integer userId;
    }

    @Data
    public static class addForm {
		@ApiModelProperty(value = "商品id",required = true)
		@NotBlank(message = "商品id 不能为空")
		private String goodsId;
		@ApiModelProperty(value = "购买数量",required = true)
		@NotNull(message = "购买数量 必选大于0")
		@Min(value = 0,message = "购买数量 必选大于0")
		private Integer number;
		@ApiModelProperty(value = "收货地址",required = true)
		@NotBlank(message = "收货地址 不能为空")
		private String address;
    }

	@Data
    public class statForm {
		@ApiModelProperty(value = "开始时间,yyyy-MM-dd",required = true)
		@NotNull(message = "开始时间 不能为空")
		private String startTime;
		@ApiModelProperty(value = "结束时间,yyyy-MM-dd",required = true)
		@NotNull(message = "结束时间 不能为空")
		private String endTime;

    }
}
