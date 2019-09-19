package com.caidi.middleware.exchange.topic;

import com.caidi.middleware.exchange.direct.DirectRec2;
import com.caidi.middleware.utils.ConnectionUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.QueueingConsumer;

public class TopicRec2 {
    private final static String QUEUE_NAME = "test_queue_topic_two";
    private final static String EXCHANGE_NAME = "test_exchange_topic";

    public static void main(String[] args) throws Exception {
        Connection connection = ConnectionUtils.getConnection();
        Channel channel = connection.createChannel();
        // 声明队列
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        // 绑定队列到交换机  (后台有关的数据) 符号“#”匹配一个或多个词，符号“*”匹配不多不少一个 单词间以"."分隔
        channel.queueBind(QUEUE_NAME,EXCHANGE_NAME,"background.#");

        // 同一时刻服务器只会发一条消息给消费者
        channel.basicQos(1);

        // 定义队列的消费者
        QueueingConsumer queueingConsumer = new QueueingConsumer(channel);

        // 监听队列(将消费者和队列绑定到一起)
        channel.basicConsume(QUEUE_NAME,false,queueingConsumer);


        while(true){
            // 类似迭代器
            QueueingConsumer.Delivery delivery = queueingConsumer.nextDelivery();
            System.out.println("消费者" + DirectRec2.class.getSimpleName() + "消费了 :" + new String(delivery.getBody()));
            channel.basicAck(delivery.getEnvelope().getDeliveryTag(),false);
        }
    }
}
