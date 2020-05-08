package com.service.product;

import com.form.product.GoodsForm;
import com.result.Result;

public interface GoodsService {

    Result findById(GoodsForm.findByIdForm form);

    Result list(GoodsForm.listForm form);

    Result add(GoodsForm.addForm form);

    Result update(GoodsForm.updateForm form);

    Result delete(GoodsForm.deleteForm form);

    Result hotOrderProduct();

    Result hotProduct();

    Result newProduct();
}
