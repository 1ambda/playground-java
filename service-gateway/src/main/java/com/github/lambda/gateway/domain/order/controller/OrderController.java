package com.github.lambda.gateway.domain.order.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("api")
public class OrderController {

  @Autowired
  public OrderController() {

  }
}
