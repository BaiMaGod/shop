package com.mapper.order;

import com.form.order.OrderForm;
import com.vo.OrderStatVo;

public interface OrderMapperExt {


    void reduceStock(OrderForm.addForm form);

}