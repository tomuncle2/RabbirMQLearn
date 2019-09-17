package com.caidi.middleware.work;

import com.caidi.middleware.utils.ConnectionUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.QueueingConsumer;

public class WorkRec1 {

    private final static String QUEUE_NAME = "test_queue_work";

    public static void main(String[] args) throws Exception {
        Connection connection = ConnectionUtils.getConnection();
        Channel channel = connection.createChannel();
        // 声明队列
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);
        // 创建消费者（在channel上）
        QueueingConsumer queueingConsumer = new QueueingConsumer(channel);
        // 绑定队列和消费者 （手动确认模式）
        channel.basicConsume(QUEUE_NAME,false,queueingConsumer);

        // 消费消息
        while(true) {
            // 默认得到队列所有的消息
            QueueingConsumer.Delivery delivery = queueingConsumer.nextDelivery();
            String message = new String(delivery.getBody());
            System.out.println("消费者: " + WorkRec1.class.getSimpleName() + " 消费了消息 " + message);

            // 手动确认消息
            channel.basicAck(delivery.getEnvelope().getDeliveryTag(),false);

            Thread.sleep(1000);
        }
    }
}
