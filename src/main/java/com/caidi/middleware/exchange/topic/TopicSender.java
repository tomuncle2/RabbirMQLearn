package com.caidi.middleware.exchange.topic;

import com.caidi.middleware.exchange.direct.DirectSender;
import com.caidi.middleware.utils.ConnectionUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

public class TopicSender {
    private final static String EXCHANGE_NAME = "test_exchange_topic";

    public static void main(String[] args) throws Exception {
        Connection connection = ConnectionUtils.getConnection();
        Channel channel = connection.createChannel();
        // 声明队列
//        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        // 定义exchange
        channel.exchangeDeclare(EXCHANGE_NAME,"topic");

        // 消息内容
        String message = "hello word!";
        // 定义了交换机后，消息优先发布到交换机
        channel.basicPublish(EXCHANGE_NAME,"background.update.date",null,message.getBytes());
        // 消息中间件
        System.out.println(DirectSender.class.getSimpleName() + "发送了消息: " + message);
        channel.close();
        connection.close();
    }
}
