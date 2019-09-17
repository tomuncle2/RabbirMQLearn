package com.caidi.middleware.exchange.fanout;


import com.caidi.middleware.utils.ConnectionUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
/**
 * 订阅发布模式（fanout）
 * 当定义了交换机后，再发布消息，如果这时候，没有和交换机绑定的队列。就不会有存入消息到相应队列
 * 交换机将消息复制到每一个与其绑定的队列
 * */
public class FanoutSender {

    private final static String QUEUE_NAME = "test_queue_fanout";
    private final static String EXCHANGE_NAME = "test_exchange_fanout";

    public static void main(String[] args) throws Exception {
        Connection connection = ConnectionUtils.getConnection();
        Channel channel = connection.createChannel();
        // 声明队列
//        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        // 定义exchange
        channel.exchangeDeclare(EXCHANGE_NAME,"fanout");

        // 消息内容
        String message = "hello word!";
        // 定义了交换机后，消息优先发布到交换机
        channel.basicPublish(EXCHANGE_NAME,"",null,message.getBytes());
        // 消息中间件
        System.out.println(FanoutSender.class.getSimpleName() + "发送了消息: " + message);
        channel.close();
        connection.close();
    }
}
