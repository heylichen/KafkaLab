package com.heylichen.amq.jmsbasic.p2p;

import com.alibaba.fastjson.JSON;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jms.*;

import static com.heylichen.amq.jmsbasic.p2p.MyMessageProducer.QUEUE;

/**
 * Created by lichen2 on 2016/6/1.
 */
public class TransientAsyncMessageConsumer implements MessageListener, Runnable {
  private long ttl = 1000;
  private static final Logger logger = LoggerFactory.getLogger(TransientAsyncMessageConsumer.class);

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
      Destination destination = session.createQueue(QUEUE);

      consumer = session.createConsumer(destination);
      consumer.setMessageListener(this);

      //keep connection for a while, enough to let this get msg
      Thread.sleep(ttl);
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

  @Override
  public void onMessage(Message message) {
    try {
      if (message instanceof TextMessage) {
        TextMessage tm = (TextMessage) message;
        logger.info("got:{}", tm.getText());
      } else {
        logger.info("got:{}", JSON.toJSONString(message));
      }
    } catch (Exception e) {
      e.printStackTrace();
    }

  }

  public void setTtl(long ttl) {
    this.ttl = ttl;
  }
}
