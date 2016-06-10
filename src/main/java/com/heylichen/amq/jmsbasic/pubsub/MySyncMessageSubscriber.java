package com.heylichen.amq.jmsbasic.pubsub;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jms.*;

import static com.heylichen.amq.jmsbasic.pubsub.MyMessagePublisher.TOPIC;

/**
 * Created by lichen2 on 2016/6/1.
 */
public class MySyncMessageSubscriber implements Runnable {

  private static final Logger logger = LoggerFactory.getLogger(MySyncMessageSubscriber.class);

  public void run() {
    Connection connection = null;
    Session session = null;
    MessageConsumer consumer = null;
    try {
      // Create a ConnectionFactory
      ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");

      // Create a Connection
      connection = connectionFactory.createConnection();
      connection.start();

      // Create a Session
      session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

      // Create the destination (Topic or Queue)
      Topic destination = session.createTopic(TOPIC);

      consumer = session.createConsumer(destination);
      TextMessage message = (TextMessage) consumer.receive(1000);

      // Tell the producer to send the message
      logger.info("received message:" + message.getText());
    } catch (Exception e) {
      System.out.println("Caught: " + e);
      e.printStackTrace();
    } finally {
      try {
        // Clean up
        if (consumer != null) {
          consumer.close();
        }
        if (session != null) {
          session.close();
        }
        if (connection != null) {
          connection.close();
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }
}
