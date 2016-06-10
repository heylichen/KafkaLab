package com.heylichen.amq.jms.connectors;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by lichen2 on 2016/6/1.
 * test pub-sub using different connectors
 */
public class ConnectorTopicTest {

  private static final Logger logger = LoggerFactory.getLogger(ConnectorTopicTest.class);
  private ExecutorService exec = Executors.newFixedThreadPool(6);

  @Test
  public void variousConnectors() throws Exception {
    String url = "tcp://localhost:61616";
    ConnectorPublisher publisher = new ConnectorPublisher(url);
    ConnectorSubscriber subscriber = new ConnectorSubscriber(url);
    exec.submit(publisher);
    exec.submit(subscriber);
    Thread.sleep(9000);
  }
}
