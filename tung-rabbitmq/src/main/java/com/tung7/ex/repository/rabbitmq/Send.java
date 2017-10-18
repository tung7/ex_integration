package com.tung7.ex.repository.rabbitmq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * TODO Fill The Description!
 *
 * @author Tung
 * @version 1.0
 * @date 2017/10/2.
 * @update
 */

public class Send {
    private final static String QUEUE_NAME = "hello";
    private final static String HOST = "localhost";

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(HOST);

        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        String message = "Hello World!";

        channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
        System.out.println(" [x] Sent '" + message + "'");


        channel.close();
        connection.close();

    }
}