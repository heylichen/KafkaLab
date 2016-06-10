package com.heylichen.amq.jmsbasic.p2p;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jms.*;

/**
 * Created by lichen2 on 2016/6/1.
 */
public class MyMessageProducer implements Runnable {
  public static final String QUEUE = "QUICKSTART.QUEUE";
  private static final Logger logger = LoggerFactory.getLogger(MyMessageProducer.class);

  public void run() {
    Connection connection = null;
    Session session = null;
    MessageProducer producer = null;
    try {
      // Create a ConnectionFactory
      ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");

      // Create a Connection
      connection = connectionFactory.createConnection();
      connection.start();

      // Create a Session
      session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

      // Create the destination (Topic or Queue)
      Destination destination = session.createQueue(QUEUE);

      // Create a MessageProducer from the Session to the Topic or Queue
      producer = session.createProducer(destination);
      producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);

      // Create a messages
      String text = "Hello world! From: " + Thread.currentThread().getName() + " : " + this.hashCode();
      TextMessage message = session.createTextMessage(text);

      // Tell the producer to send the message
      logger.info("Sent message: " + message.hashCode() + " : " + Thread.currentThread().getName());
      producer.send(message);
    } catch (Exception e) {
      System.out.println("Caught: " + e);
      e.printStackTrace();
    } finally {
      try {
        // Clean up
        if (producer != null) {
          producer.close();
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
