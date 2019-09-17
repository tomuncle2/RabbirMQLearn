package com.caidi.middleware.exchange.fanout;

import com.caidi.middleware.utils.ConnectionUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.QueueingConsumer;

public class FanoutRec2 {

    private final static String QUEUE_NAME = "test_queue_fanout_one";
    private final static String EXCHANGE_NAME = "test_exchange_fanout";

    public static void main(String[] args) throws Exception {
        Connection connection = ConnectionUtils.getConnection();
        Channel channel = connection.createChannel();
        // 声明队列
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        // 定义exchange
        channel.exchangeDeclare(EXCHANGE_NAME,"fanout");
        // 绑定队列到交换机
        channel.queueBind(QUEUE_NAME,EXCHANGE_NAME,"");
        // 同一时刻服务器只会发一条消息给消费者
        channel.basicQos(1);

        // 定义队列的消费者
        QueueingConsumer queueingConsumer = new QueueingConsumer(channel);
        // 监听队列
        channel.basicConsume(QUEUE_NAME,false,queueingConsumer);

        while(true){
            // 类似迭代器
            QueueingConsumer.Delivery delivery = queueingConsumer.nextDelivery();
            System.out.println("消费者" + FanoutRec2.class.getSimpleName() + "消费了 :" + new String(delivery.getBody()));
            channel.basicAck(delivery.getEnvelope().getDeliveryTag(),false);
        }
    }
}
