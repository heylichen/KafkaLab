package com.heylichen.amq.jms.commons;

import com.google.common.base.Throwables;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jms.*;

/**
 * Created by lc on 2016/6/10.
 */
public class TopicProducer {
  private static final Logger logger = LoggerFactory.getLogger(TopicProducer.class);
  private Connection connection = null;
  private Session session = null;
  private MessageProducer producer = null;
  private String connectorUrI;
  private String topic;

  public TopicProducer(String connectorUrI, String topic) {
    this.connectorUrI = connectorUrI;
    this.topic = topic;
    try {
      // Create a ConnectionFactory
      ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(connectorUrI);

      // Create a Connection
      connection = connectionFactory.createConnection();
      connection.start();

      // Create a Session
      session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

      // Create the destination (Topic or Queue)
      Topic destination = session.createTopic(topic);

      // Create a MessageProducer from the Session to the Topic or Queue
      producer = session.createProducer(destination);
      producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void close() {
    if (connection != null) {
      try {
        connection.close();
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }

  public void sendMsg(String textMsg) {
    try {
      Message message = session.createTextMessage(textMsg);
      producer.send(message);
    } catch (JMSException e) {
      logger.error("sendMsg error, msg:{}, stack:{}", new Object[]{textMsg, Throwables.getStackTraceAsString(e)});
    }

  }
}
