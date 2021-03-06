package com.tyler.object;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jms.*;

/**
 * Created by tyler on 2017/5/17.
 */
public class ProducerApp {
    private static final Logger LOGGER = LoggerFactory.getLogger(ProducerApp.class);
    private static final String SUBJECT = "test-activemq-queue";

    public static void main(String[] args) throws JMSException {
        //初始化连接工厂
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(
                ActiveMQConnectionFactory.DEFAULT_USER,
                ActiveMQConnectionFactory.DEFAULT_PASSWORD,
                "tcp://192.168.6.194:61616"
        );
        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(
                ActiveMQConnectionFactory.DEFAULT_USER,
                ActiveMQConnectionFactory.DEFAULT_PASSWORD,
                "tcp://192.168.6.194:61616");
        //The setTrustAllPackages() allows you to turn off security check and trust all classes.
        // It's useful for testing purposes.
        factory.setTrustAllPackages(true);
        //获得连接
        Connection conn = factory.createConnection();
        //启动连接
        conn.start();

        //创建Session，此方法第一个参数表示会话是否在事务中执行，第二个参数设定会话的应答模式
        Session session = conn.createSession(false, Session.AUTO_ACKNOWLEDGE);

        //创建队列
        Destination dest = session.createQueue(SUBJECT);
        //createTopic方法用来创建Topic
        //session.createTopic("TOPIC");

        //通过session可以创建消息的生产者
        MessageProducer producer = session.createProducer(dest);
        for (int i=0;i<100;i++) {
            //初始化一个mq消息
            ObjectMessage objectMessage = session.createObjectMessage();
            objectMessage.setObject(new Car("保时捷"+i,i+"000w"));
            //发送消息
            producer.send(objectMessage);

            LOGGER.debug("send message {}", i);
        }
        System.out.println("生产者已经生产并发送消息");
        //关闭mq连接
        conn.close();
    }
}