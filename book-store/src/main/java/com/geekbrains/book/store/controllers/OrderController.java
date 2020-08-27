package com.geekbrains.book.store.controllers;

import com.geekbrains.book.store.beans.Cart;
import com.geekbrains.book.store.entities.Order;
import com.geekbrains.book.store.entities.User;
import com.geekbrains.book.store.services.OrderService;
import com.geekbrains.book.store.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@RequestMapping("/orders")
@AllArgsConstructor
public class OrderController {
    public static final String EXCHANGE_FOR_PROCESSING_TASK = "processingExchanger";
    private UserService userService;
    private OrderService orderService;
    private Cart cart;
    private RabbitTemplate rabbitTemplate;

    @Autowired
    public void setRabbitTemplate(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @GetMapping("/create")
    public String createOrder(Principal principal, Model model) {
        User user = userService.findByUsername(principal.getName()).get();
        model.addAttribute("user", user);
        return "order_info";
    }

    @PostMapping("/confirm")
    @ResponseBody
    public String confirmOrder(Principal principal) {
        User user = userService.findByUsername(principal.getName()).get();
        Order order = new Order(user, cart);
        order.setStatus(Order.Status.IN_PROGRESS);
        order = orderService.saveOrder(order);
        rabbitTemplate.convertAndSend(
                OrderController.EXCHANGE_FOR_PROCESSING_TASK,
                null,
                String.valueOf(order.getId())
        );
        return order.getId() + " " + order.getPrice();
    }
}
