package com.mapper.order;

import com.form.order.OrderForm;

import java.util.List;

public interface OrderMapperExt {


    void reduceStock(OrderForm.addForm form);

    List<String> selectGoodsIdsByCountTop();

    List<String> selectByCount();


}