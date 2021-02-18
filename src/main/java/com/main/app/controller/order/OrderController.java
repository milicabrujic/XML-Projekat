package com.main.app.controller.order;

import com.main.app.domain.dto.Entities;
import com.main.app.domain.dto.order.OrderDto;
import com.main.app.domain.model.order.CustomerOrder;
import com.main.app.domain.model.user.User;
import com.main.app.service.order.OrderService;
import com.sun.mail.iap.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.main.app.converter.order.OrderConverter.toDto;

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<OrderDto> createOrder(@RequestBody OrderDto orderDto) {
        return ResponseEntity.ok().body(toDto(orderService.createOrder(orderDto)));
    }

    @GetMapping(path = "/search")
    public ResponseEntity<Entities<CustomerOrder>> getAllBySearchParam(Pageable pageable, @RequestParam(name = "searchParam") String searchParam) {
        return new ResponseEntity<>(orderService.getAllBySearchParam(searchParam, pageable), HttpStatus.OK);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<OrderDto> getById(@PathVariable Long id){
        return new ResponseEntity<>(toDto(orderService.getOne(id)), HttpStatus.OK);
    }


}
