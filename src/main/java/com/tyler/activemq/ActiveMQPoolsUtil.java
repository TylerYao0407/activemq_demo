package com.tyler.activemq;

import javax.jms.JMSException;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.pool.PooledConnection;
import org.apache.activemq.pool.PooledConnectionFactory;
/**
 * Created by tyler on 2017/5/17.
 */
public final class ActiveMQPoolsUtil {

    /**
     * 连接
     */
    private static PooledConnection connection;

    private ActiveMQPoolsUtil() {

    }

    // 初始化连接池等工作
    static {
        String url = "failover:(tcp://192.168.6.194:61616)?initialReconnectDelay=1000";
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory();
        activeMQConnectionFactory.setUserName("admin");
        activeMQConnectionFactory.setPassword("admin");
        activeMQConnectionFactory.setBrokerURL(url);

        try {

            PooledConnectionFactory pooledConnectionFactory = new PooledConnectionFactory(
                    activeMQConnectionFactory);

            // session数
            int maximumActive = 200;
            pooledConnectionFactory.setMaximumActiveSessionPerConnection(maximumActive);
            pooledConnectionFactory.setIdleTimeout(120);
            pooledConnectionFactory.setMaxConnections(5);
            pooledConnectionFactory.setBlockIfSessionPoolIsFull(true);
            connection = (PooledConnection) pooledConnectionFactory.createConnection();
            // 必须start，否则无法接收消息
            connection.start();
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    /**
     * 关闭连接
     */
    public static void close() {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取一个连接
     */
    public static PooledConnection getConnection() {
        return connection;
    }

    /**
     * 设置连接
     */
    public static void setConnection(PooledConnection connection) {
        ActiveMQPoolsUtil.connection = connection;
    }

}
