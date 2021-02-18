package com.main.app.service.order;

import com.main.app.domain.dto.Entities;
import com.main.app.domain.dto.order.OrderDto;
import com.main.app.domain.model.order.CustomerOrder;
import com.main.app.domain.model.order_item.OrderItem;
import com.main.app.domain.model.shopping_cart.ShoppingCart;
import com.main.app.domain.model.shopping_cart_item.ShoppingCartItem;
import com.main.app.repository.order.OrderItemRepository;
import com.main.app.repository.order.OrderRepository;
import com.main.app.repository.shopping_cart.ShoppingCartRepository;
import com.main.app.repository.shopping_cart_item.ShoppingCartItemRepository;
import com.main.app.service.order_item.OrderItemService;
import com.main.app.service.shopping_cart.ShoppingCartService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

import static com.main.app.converter.order.OrderConverter.*;
import static com.main.app.static_data.Messages.SHOPPING_CART_IS_EMPTY;
import static com.main.app.util.Util.ordersToIds;


@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class OrderServiceImpl implements OrderService {

    private final ShoppingCartService shoppingCartService;

    private final ShoppingCartRepository shoppingCartRepository;

    private final OrderItemService orderItemService;

    private final OrderRepository orderRepository;

    private final OrderItemRepository orderItemRepository;

    private final ShoppingCartItemRepository shoppingCartItemRepository;

    @Override
    public CustomerOrder createOrder(OrderDto orderDto) {
        ShoppingCart shoppingCart = shoppingCartService.findShoppingCartById(orderDto.getShoppingCartId());
        if(shoppingCart.getItems().size() <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, SHOPPING_CART_IS_EMPTY);
        }
        CustomerOrder order = orderRepository.save(toEntity(orderDto));
        order.setOrderItems(new ArrayList<OrderItem>());

        //TODO Provjera da li je current user,shopping cart user?
        order.setUser(shoppingCart.getUser());

        for(ShoppingCartItem shoppingCartItem: shoppingCart.getItems()){
            OrderItem orderItem = orderItemService.create(shoppingCartItem);
            order.getOrderItems().add(orderItem);
            orderItem.setCustomerOrder(order);
            orderItemRepository.save(orderItem);
            shoppingCartItem.setDeleted(true);
            shoppingCartItemRepository.save(shoppingCartItem);
        }

        int total = 0;
        for (OrderItem item: order.getOrderItems()) {
            total += item.getQuantity() * item.getVariation().getPrice();
        }

        order.setTotalPrice(total);
        order.setStatus("Poruceno.Na Cekanju");

        shoppingCart.setDeleted(true);
        shoppingCartRepository.save(shoppingCart);

        CustomerOrder orderSaved = orderRepository.save(order);

        return orderSaved;
    }

    @Override
    public Entities getAllBySearchParam(String searchParam, Pageable pageable) {
        if(searchParam == "" || searchParam == null ){

            Page<CustomerOrder> pagedOrders = orderRepository.findAll(pageable);
            List<Long> ids = ordersToIds(pagedOrders);

            Pageable mySqlPaging = PageRequest.of(0, pageable.getPageSize(), pageable.getSort());
            List<CustomerOrder> orders = orderRepository.findAllByIdInAndDeletedFalse(ids, mySqlPaging).getContent();

            List<OrderDto> ordersDto = ordersListToOrdersDTOList(orders);


            Entities entities = new Entities();
            entities.setEntities(ordersDto);
            entities.setTotal(pagedOrders.getTotalElements());

            return entities;
        }else{return null;}
    }

    @Override
    public CustomerOrder getOne(Long id) {
        return orderRepository.getOne(id);
    }
}
