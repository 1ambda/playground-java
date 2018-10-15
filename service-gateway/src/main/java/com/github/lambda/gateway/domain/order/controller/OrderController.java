package com.github.lambda.gateway.domain.order.controller;

import com.github.lambda.gateway.domain.order.entity.Order;
import com.github.lambda.gateway.domain.order.repository.OrderRepository;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

@Controller
@RequestMapping("api")
public class OrderController {

  OrderRepository orderRepository;

  @Autowired
  public OrderController(OrderRepository orderRepository) {
    this.orderRepository = orderRepository;
  }

  @ResponseStatus(code = HttpStatus.OK)
  @RequestMapping(value = "", method = RequestMethod.GET)
  public List<Order> getAllOrders() {
    Iterable<Order> orders = orderRepository.findAll();
    return Lists.newArrayList(orders);
  }
}
