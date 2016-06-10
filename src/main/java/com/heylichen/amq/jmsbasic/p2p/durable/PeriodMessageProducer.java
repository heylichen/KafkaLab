package com.heylichen.amq.jmsbasic.p2p.durable;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jms.*;

/**
 * Created by lichen2 on 2016/6/1.
 */
public class PeriodMessageProducer implements Runnable {
  public static final String QUEUE = "QUICKSTART.QUEUE";
  private static final Logger logger = LoggerFactory.getLogger(PeriodMessageProducer.class);
  private long msgTtl = 0;// message time to live, in ms


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
      producer.setTimeToLive(msgTtl);
      producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);

      // Tell the producer to send the message
      for (int i = 0; i < 10; i++) {
        String text = "msg:" + i + "from  " + Thread.currentThread().getName();
        TextMessage message = session.createTextMessage(text);
        producer.send(message);
        Thread.sleep(1000);
      }
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

  public long getMsgTtl() {
    return msgTtl;
  }

  public void setMsgTtl(long msgTtl) {
    this.msgTtl = msgTtl;
  }
}
