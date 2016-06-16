package com.heylichen.labs.kafka.jms.basic.pubsub.durable;

import com.alibaba.fastjson.JSON;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jms.*;

import static com.heylichen.labs.kafka.jms.basic.pubsub.MyMessagePublisher.TOPIC;

/**
 * Created by lichen2 on 2016/6/1.
 */
public class DurableAsyncMessageSubscriber implements MessageListener, Runnable {
  private boolean durable = false;
  private static final Logger logger = LoggerFactory.getLogger(DurableAsyncMessageSubscriber.class);
  Connection connection = null;
  Session session = null;
  MessageConsumer consumer = null;

  public DurableAsyncMessageSubscriber(boolean durable) {
    this.durable = durable;
  }

  public void run() {
    try {
      // Create a ConnectionFactory
      ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");

      // Create a Connection
      connection = connectionFactory.createConnection();
      connection.setClientID("testClient");
      connection.start();

      // Create a Session


      session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
      // Create the destination (Topic or Queue)
      Topic topic = session.createTopic(TOPIC);

      if (durable) {
        consumer = session.createDurableSubscriber(topic, TOPIC + ".Durable.Consumer");
      } else {
        consumer = session.createConsumer(topic);
      }

      consumer.setMessageListener(this);

      //keep connection for a while, enough to let this get msg
      Thread.sleep(3000);
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
}
