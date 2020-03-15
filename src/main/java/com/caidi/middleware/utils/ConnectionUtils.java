package com.caidi.middleware.utils;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import java.io.IOException;

/**
 * 获取rabbitmq连接对象
 * */
public class ConnectionUtils  {

    /***
     * rabbitmq管理页面账号 host(虚拟主机)相当于mysql数据库
     * * 1.guest guest  host:/test
     * 2.admin 123456 host:/
     * 3.taotao 123456 host:/taotao
     */
    private static final String HOST = "localhost";
    private static final String USERNAME = "taotao";
    
    private static final String PASSWORD = "123456";
    private static final Integer PORT = 5672;
    private static final String VIRTUAL_HOST = "/taotao";

    public static Connection getConnection() throws IOException {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost(HOST);
        connectionFactory.setPort(PORT);
        connectionFactory.setVirtualHost(VIRTUAL_HOST);
        connectionFactory.setUsername(USERNAME);
        connectionFactory.setPassword(PASSWORD);
        return connectionFactory.newConnection();
    }
}
