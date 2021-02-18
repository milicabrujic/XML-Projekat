package com.main.app.service.order;

import com.main.app.domain.dto.Entities;
import com.main.app.domain.dto.order.OrderDto;
import com.main.app.domain.model.order.CustomerOrder;
import com.main.app.domain.model.user.User;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public interface OrderService {

    public CustomerOrder createOrder(OrderDto orderDto);

    Entities getAllBySearchParam(String searchParam, Pageable pageable);

    CustomerOrder getOne(Long id);

}
