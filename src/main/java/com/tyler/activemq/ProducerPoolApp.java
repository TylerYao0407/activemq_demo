package com.tyler.activemq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jms.*;
import java.util.*;
import java.util.concurrent.DelayQueue;

/**
 * Created by tyler on 2017/5/17.
 */
public class ProducerPoolApp {
    private static final Logger LOGGER = LoggerFactory.getLogger(ProducerPoolApp.class);
    private static final String SUBJECT = "test-activemq-queue";

    public static void main(String[] args) throws JMSException {
        //获得连接
        Connection conn = ActiveMQPoolsUtil.getConnection("192.168.6.194","61616","admin","admin");
        //启动连接
//        conn.start();

        //创建Session，此方法第一个参数表示会话是否在事务中执行，第二个参数设定会话的应答模式
        Session session = conn.createSession(false, Session.AUTO_ACKNOWLEDGE);

        //创建队列,Destination消息的目的地，发送给谁
        Destination dest = session.createQueue(SUBJECT);
        //createTopic方法用来创建Topic
        //session.createTopic("TOPIC");

        //通过session可以创建消息的生产者
        MessageProducer producer = session.createProducer(dest);
        for (int i=0;i<100;i++) {
            //初始化一个mq消息
            TextMessage message = session.createTextMessage("hello active mq 中文" + i);
            //发送消息
            producer.send(message);
            LOGGER.debug("send message {}", i);
        }
        System.out.println("ok了");
        System.out.println();
        //关闭mq连接
        ActiveMQPoolsUtil.close();
    }
}