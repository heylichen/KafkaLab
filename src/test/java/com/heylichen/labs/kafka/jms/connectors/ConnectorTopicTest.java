package com.heylichen.labs.kafka.jms.connectors;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by lichen2 on 2016/6/10.
 * test pub-sub using different connectors
 */
public class ConnectorTopicTest {

  private static final Logger logger = LoggerFactory.getLogger(ConnectorTopicTest.class);
  private ExecutorService exec = Executors.newFixedThreadPool(6);

  /**
   * activemq support various connectors.
   * @throws Exception
   */
  @Test
  public void variousConnectors() throws Exception {
    Map<String,String> urls = new HashMap<>();
    urls.put("tcp","tcp://localhost:61616");
    urls.put("nio","nio://localhost:61618");
    urls.put("udp","udp://localhost:61619");
    urls.put("ssl","ssl://localhost:61617");
    urls.put("http","http://localhost:8081");

    String url = urls.get("ssl");
    ConnectorPublisher publisher = new ConnectorPublisher(url);
    ConnectorSubscriber subscriber = new ConnectorSubscriber(url);
    exec.submit(publisher);
    exec.submit(subscriber);
    Thread.sleep(9000);
  }

  /**
   * Network Connectors, static networks.
   * producer connect to brokerA, which is at tcp://localhost:61616, config brokerA.xml.
   * consumer connect to brokerB, which is at tcp://localhost:61617, config brokerB.xml.
   * two brokers are started as two activemq instances, with different jetty ports.
   * @throws Exception
   */
  @Test
  public void connectorNetwork() throws Exception{
    String brokerA = "tcp://localhost:61616";
    String brokerB = "tcp://localhost:61617";
    ConnectorPublisher publisher = new ConnectorPublisher(brokerA);
    ConnectorSubscriber subscriber = new ConnectorSubscriber(brokerB);
    exec.submit(publisher);
    exec.submit(subscriber);
    Thread.sleep(9000);
  }
}
