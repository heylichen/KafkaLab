package com.heylichen.amq.jms.connectors;

import com.heylichen.amq.jms.commons.TopicProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by lc on 2016/6/10.
 */
public class ConnectorPublisher implements Runnable {
  private static final Logger logger = LoggerFactory.getLogger(TopicProducer.class);
  private String url;
  public static final String TOPIC = "Connector.Topic";

  public ConnectorPublisher(String url) {
    logger.info("using url {}", url);
    this.url = url;
  }

  @Override
  public void run() {
    TopicProducer publisher = new TopicProducer(url, TOPIC);
    try {
      for (int i = 0; i < 5; i++) {
        String text = "msg" + i;
        publisher.sendMsg(text);
        Thread.sleep(1000);
      }
    } catch (InterruptedException e) {
      logger.error("error run.", e);
    }

    publisher.close();
  }
}
