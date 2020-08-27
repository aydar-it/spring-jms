package com.orders;

import com.orders.consumer.OrderConsumer;
import com.orders.producer.OrderProducer;
import com.rabbitmq.client.ConnectionFactory;

import java.util.ArrayList;
import java.util.List;

public class MainApp {

    public static void main(String[] args) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        List<Long> messages = new ArrayList<>();
        OrderConsumer consumer = new OrderConsumer();
        consumer.startConsume(messages, factory);
        OrderProducer producer = new OrderProducer();
        producer.startProduce(messages, factory);
    }
}
