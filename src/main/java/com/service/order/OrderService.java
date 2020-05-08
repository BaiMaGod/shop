package com.service.order;

import com.form.order.AlipayForm;
import com.form.order.OrderForm;
import com.result.Result;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface OrderService {
    Result findById(OrderForm.findByIdForm form);

    Result list(OrderForm.listForm form);

    Result add(OrderForm.addForm form, HttpServletResponse httpResponse) throws IOException;

    void updateState(AlipayForm form);
}
