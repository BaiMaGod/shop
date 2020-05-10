package com.service.product;

import com.form.product.CartForm;
import com.result.Result;

public interface CartService {

    Result list();

    Result add(CartForm.addForm form);

    Result delete(CartForm.deleteForm form);
}
