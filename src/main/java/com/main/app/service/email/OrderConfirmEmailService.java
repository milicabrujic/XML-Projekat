package com.main.app.service.email;

import com.main.app.domain.dto.order_item.OrderItemDto;
import com.main.app.domain.model.order.CustomerOrder;
import com.main.app.domain.model.order_item.OrderItem;

import java.util.List;

public interface OrderConfirmEmailService {
    void sendEmail(CustomerOrder order, String emailFrom, String emailTo, List<OrderItemDto> canDelivery, List<OrderItemDto> cantDelivery, List<OrderItemDto> selfTransportDelivery);
}
