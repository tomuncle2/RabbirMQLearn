package com.caidi.middleware.simple;

import com.caidi.middleware.utils.ConnectionUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

/** rabbitmq为java提供的客户端驱动(类似jdbc)
 * 简单模式，一个消息只能被一个消费者消费
 */
public class SimpleSender {

    // 队列名，类似mysql数据库的表名
    private final static String QUEUE_NAME = "test_queue";

    public static void main(String[] args) throws Exception {
        Connection connection = ConnectionUtils.getConnection();
        Channel channel = connection.createChannel();
        // 创建队列
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);

        // 发送消息
        for(int i =0 ; i < 10;i++) {
            String message = "hello word rabbitmq!!";
            channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
            System.out.println("生产者" + SimpleSender.class.getSimpleName() + "发送了消息");
        }
        // 关闭资源
        channel.close();
        connection.close();
    }
}