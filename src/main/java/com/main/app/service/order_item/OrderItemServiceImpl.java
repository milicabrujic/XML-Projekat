package com.main.app.service.order_item;

import com.main.app.domain.model.order_item.OrderItem;
import com.main.app.domain.model.shopping_cart_item.ShoppingCartItem;
import com.main.app.repository.order.OrderItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class OrderItemServiceImpl implements OrderItemService {

    private final OrderItemRepository orderItemRepository;

    @Override
    public OrderItem create(ShoppingCartItem shoppingCartItem) {
        OrderItem orderItem = new OrderItem();
        orderItem.setVariation(shoppingCartItem.getVariation());
        orderItem.setQuantity(shoppingCartItem.getQuantity());
        return orderItemRepository.save(orderItem);
    }
}
