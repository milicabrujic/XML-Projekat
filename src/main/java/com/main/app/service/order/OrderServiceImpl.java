package com.main.app.service.order;

import com.main.app.domain.dto.Entities;
import com.main.app.domain.dto.order.OrderDto;
import com.main.app.domain.model.order.CustomerOrder;
import com.main.app.domain.model.order_item.OrderItem;
import com.main.app.domain.model.shopping_cart.ShoppingCart;
import com.main.app.domain.model.shopping_cart_item.ShoppingCartItem;
import com.main.app.elastic.dto.order.OrdersElasticDTO;
import com.main.app.elastic.repository.order.OrderElasticRepository;
import com.main.app.elastic.repository.order.OrderElasticRepositoryBuilder;
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
import java.util.Optional;

import static com.main.app.converter.order.OrderConverter.*;
import static com.main.app.static_data.Messages.*;
import static com.main.app.util.Util.dtoOrdersToIds;
import static com.main.app.util.Util.ordersToIds;


@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class OrderServiceImpl implements OrderService {

    private final ShoppingCartService shoppingCartService;

    private final ShoppingCartRepository shoppingCartRepository;

    private final OrderElasticRepository orderElasticRepository;

    private final OrderElasticRepositoryBuilder orderElasticRepositoryBuilder;

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
        orderElasticRepository.save(new OrdersElasticDTO(orderSaved));

        return orderSaved;
    }

    @Override
    public Entities getAllBySearchParam(String searchParam, Pageable pageable, String startDate, String endDate, String startPrice, String endPrice) {


            Page<OrdersElasticDTO> pageOrders = orderElasticRepository.search(orderElasticRepositoryBuilder.generateQuery(searchParam,startDate,endDate,startPrice,endPrice), pageable);

            List<Long> ids = dtoOrdersToIds(pageOrders);

            Pageable mySqlPaging = PageRequest.of(0, pageable.getPageSize(), pageable.getSort());
            List<CustomerOrder> orders = orderRepository.findAllByIdInAndDeletedFalse(ids, mySqlPaging).getContent();

            List<OrderDto> ordersDto = ordersListToOrdersDTOList(orders);


            Entities entities = new Entities();
            entities.setEntities(ordersDto);
            entities.setTotal(pageOrders.getTotalElements());

            return entities;
    }

    @Override
    public CustomerOrder getOne(Long id) {
        return orderRepository.getOne(id);
    }

    @Override
    public CustomerOrder removeOrderItem(Long id, Long itemId) {

        CustomerOrder customerOrder = orderRepository.findById(id).get();
        OrderItem orderItem = orderItemRepository.findById(itemId).get();
        customerOrder.getOrderItems().remove(orderItem);
        orderItemService.removeItemById(itemId);

        int total = 0;
        for (OrderItem item: customerOrder.getOrderItems()) {
            total += item.getQuantity() * item.getVariation().getPrice();
        }

        customerOrder.setTotalPrice(total);

        CustomerOrder savedOrder = orderRepository.save(customerOrder);

        orderElasticRepository.save(new OrdersElasticDTO(savedOrder));

        return savedOrder;

    }

    @Override
    public CustomerOrder changeStatusOfOrder(Long id) {

      CustomerOrder customerOrder = orderRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, ORDER_NOT_EXIST));
      if(customerOrder.getStatus().equals("Poslato")){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ORDER_STATUS_IS_FINISHED);
      }else{
        customerOrder.setStatus("Poslato");
      }
      CustomerOrder savedCustomOrder = orderRepository.save(customerOrder);

      orderElasticRepository.save(new OrdersElasticDTO(savedCustomOrder));


        return savedCustomOrder;
    }
}
