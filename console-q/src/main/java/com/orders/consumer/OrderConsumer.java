package com.orders.consumer;

import com.orders.MainApp;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

import java.util.List;

public class OrderConsumer {
    public static final String QUEUE_FOR_PROCESSING_NAME = "processingQueue";

    public void startConsume(List<Long> messages, ConnectionFactory factory) throws Exception {
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        System.out.println("[*] Waiting for tasks");
        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            System.out.println("[<-] " + message);
            messages.add(Long.parseLong(message));
        };

        channel.basicConsume(QUEUE_FOR_PROCESSING_NAME, true, deliverCallback, consumerTag -> {
        });
    }
}
