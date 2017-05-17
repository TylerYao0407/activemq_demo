package com.tyler.object;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jms.*;

/**
 * Created by tyler on 2017/5/17.
 */
public class ConsumerApp implements MessageListener {
    private static final Logger LOGGER = LoggerFactory.getLogger(ConsumerApp.class);
    private static final String SUBJECT = "test-activemq-queue";

    public static void main(String[] args) throws JMSException {
        //初始化ConnectionFactory
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(
                ActiveMQConnectionFactory.DEFAULT_USER,
                ActiveMQConnectionFactory.DEFAULT_PASSWORD,
                "tcp://192.168.6.194:61616"
        );
        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(
                ActiveMQConnectionFactory.DEFAULT_USER,
                ActiveMQConnectionFactory.DEFAULT_PASSWORD,
                "tcp://192.168.6.194:61616");
        factory.setTrustAllPackages(true);
        //创建mq连接
        Connection conn = factory.createConnection();
        //启动连接
        conn.start();

        //创建会话
        Session session = conn.createSession(false, Session.AUTO_ACKNOWLEDGE);

        //通过会话创建目标
        Destination dest = session.createQueue(SUBJECT);
        //创建mq消息的消费者
        MessageConsumer consumer = session.createConsumer(dest);

        //初始化MessageListener
        final ConsumerApp me = new ConsumerApp();

        //给消费者设定监听对象
//        consumer.setMessageListener(new MessageListener() {
//            public void onMessage(Message message) {
//                try {
//                    Car car = (Car)((ObjectMessage)message).getObject();
//                    System.out.println(car);
//                } catch (JMSException e) {
//                    LOGGER.error("error {}", e);
//                }
//            }
//        });
        consumer.setMessageListener(me);
    }

    public void onMessage(Message message) {
        try {
            Car car = (Car)((ObjectMessage)message).getObject();
            System.out.println(car);
        } catch (JMSException e) {
            LOGGER.error("error {}", e);
        }
    }

}
