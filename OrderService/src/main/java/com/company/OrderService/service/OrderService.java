package com.company.OrderService.service;

import com.company.OrderService.model.dto.OrderDTO;
import com.company.OrderService.model.form.OrderCreateForm;

public interface OrderService {
    Long placeOrder(OrderCreateForm orderCreateForm);

    OrderDTO getOrderById(Long id);
}
