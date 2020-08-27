package com.orders.producer;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeoutException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class OrderProducer {
    public static final String EXCHANGER_FOR_PROCESSING_RESULTS = "processingResultsExchanger";
    private static final Pattern inputPattern = Pattern.compile("/готово (\\d+)");

    public void startProduce(List<Long> messages, ConnectionFactory factory) throws IOException, TimeoutException {
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        Scanner scanner = new Scanner(System.in);
        while (true) {
            String input = scanner.nextLine();
            Matcher matcher = inputPattern.matcher(input);
            if (matcher.matches()) {
                Long orderId = Long.parseLong(matcher.group(1));
                if (messages.contains(orderId)) {
                    channel.basicPublish(EXCHANGER_FOR_PROCESSING_RESULTS, "", null, String.valueOf(orderId).getBytes());
                    messages.remove(orderId);
                }
            }
        }
    }
}
