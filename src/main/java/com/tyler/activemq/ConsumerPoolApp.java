package com.tyler.activemq;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jms.*;

/**
 * Created by tyler on 2017/5/17.
 */
public class ConsumerPoolApp implements MessageListener {
    private static final Logger LOGGER = LoggerFactory.getLogger(ConsumerPoolApp.class);
    private static final String SUBJECT = "test-activemq-queue";

    public static void main(String[] args) throws JMSException {
        //创建mq连接
        Connection conn = ActiveMQPoolsUtil.getConnection("192.168.6.194","61616","admin","admin");
        //启动连接
//        conn.start();

        //创建会话
        Session session = conn.createSession(false, Session.AUTO_ACKNOWLEDGE);

        //通过会话创建目标
        Destination dest = session.createQueue(SUBJECT);
        //创建mq消息的消费者
        MessageConsumer consumer = session.createConsumer(dest);

        //初始化MessageListener
        ConsumerPoolApp me = new ConsumerPoolApp();

        //给消费者设定监听对象
        consumer.setMessageListener(me);
    }

    public void onMessage(Message message) {
        TextMessage txtMessage = (TextMessage)message;
        try {
            LOGGER.info ("get message " + txtMessage.getText());
        } catch (JMSException e) {
            LOGGER.error("error {}", e);
        }
    }
}
