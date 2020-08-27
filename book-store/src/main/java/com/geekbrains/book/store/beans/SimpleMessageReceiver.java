package com.geekbrains.book.store.beans;

import com.geekbrains.book.store.entities.Order;
import com.geekbrains.book.store.exceptions.ResourceNotFoundException;
import com.geekbrains.book.store.repositories.OrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

@Component
@Slf4j
public class SimpleMessageReceiver {
    private RabbitTemplate rabbitTemplate;
    private OrderRepository orderRepository;

    @Autowired
    public void setRabbitTemplate(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Autowired
    public void setOrderRepository(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public void receiveMessage(byte[] message) {
        log.info("[<-] " + new String(message));
        Long id = Long.parseLong(new String(message, StandardCharsets.UTF_8));
        if (!orderRepository.existsById(id)) {
            log.error("[!] Bad id");
            return;
        }
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("[!] Order from queue not found"));
        order.setStatus(Order.Status.OK);
        orderRepository.save(order);
    }
}
