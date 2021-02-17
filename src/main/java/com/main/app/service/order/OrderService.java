package com.main.app.service.order;

import com.main.app.domain.dto.order.OrderDto;
import com.main.app.domain.model.order.CustomerOrder;
import org.springframework.stereotype.Service;

@Service
public interface OrderService {

    public CustomerOrder createOrder(OrderDto orderDto);


}
