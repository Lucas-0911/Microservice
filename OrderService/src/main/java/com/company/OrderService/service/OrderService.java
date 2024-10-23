package com.company.OrderService.service;

import com.company.OrderService.model.form.OrderCreateForm;

public interface OrderService {
    Long placeOrder(OrderCreateForm orderCreateForm);
}
