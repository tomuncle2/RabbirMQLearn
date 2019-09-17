package com.caidi.middleware.simple;

import com.caidi.middleware.utils.ConnectionUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.QueueingConsumer;

public class SimpleRec2 {

    private final static String QUEUE_NAME = "test_queue";

    public static void main(String[] args) throws Exception {
        // 获取到连接以及mq通道
        Connection connection = ConnectionUtils.getConnection();
        Channel channel = connection.createChannel();

        // 申明队列（若生产者已经声明了同名队列，则不创建队列）
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);

        // 定义消费者
        QueueingConsumer queueingConsumer = new QueueingConsumer(channel);
        // 监听队列(将消费者和队列绑定到一起)
        channel.basicConsume(QUEUE_NAME,true,queueingConsumer);

        while(true){
            QueueingConsumer.Delivery delivery  = queueingConsumer.nextDelivery();
            String message = new String(delivery.getBody());
            System.out.println("消费者" + SimpleRec.class.getSimpleName() + " 消费：" + message);
        }
    }
}
