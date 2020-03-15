package com.caidi.middleware.work;

import com.caidi.middleware.utils.ConnectionUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

/**
 *   work模式（能者多劳）
 */
public class WorkSender {

    private final static String QUEUE_NAME = "test_queue_work";

    public static void main(String[] args) throws Exception {
        Connection connection = ConnectionUtils.getConnection();
        Channel channel = connection.createChannel();
        // 声明队列
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);

        for(int i = 0; i< 20 ;i++){
            // 消息内容
            String message = "hello word!! == [work] " + i;
            channel.basicPublish("",QUEUE_NAME,null,message.getBytes());
            System.out.println(" work模式 === 生产者发送到队列 消息: " + message);
            Thread.sleep(i * 10);
        }

        channel.close();
        connection.close();
    }
}
