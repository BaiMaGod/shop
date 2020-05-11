package com.service.product;

import com.form.product.CartForm;
import com.result.Result;

public interface CartService {

    Result list();

    Result add(CartForm.addForm form);

    //删除指定用户存在购物车中的指定商品
    void deleteGoods(String goodsId, Integer userId);

    Result delete(CartForm.deleteForm form);
}
