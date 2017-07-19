package com.heylichen.labs.kafka.newapi.basic;

import com.heylichen.labs.test.common.AbstractTestContext;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Value;

/**
 * Created by Chen Li on 2017/5/14.
 */
public class SimpleKafkaProducerTest extends AbstractTestContext {
  @Value("${bootstrap.servers}")
  private String kafkaServers;

  @Test
  public void send() throws Exception {
    SimpleKafkaProducer producer = new SimpleKafkaProducer();
    producer.setBootstrapServers(kafkaServers);
    producer.initProducer();

    producer.send("test-3-1", null, "hello, this is a test message, 中文显示！");
  }

  @Test
  public void syncSend() throws Exception {
    SimpleKafkaProducer producer = new SimpleKafkaProducer();
    producer.setBootstrapServers(kafkaServers);
    producer.initProducer();

    producer.syncSend("test-3-1", null, "hello, this is a test message, 中文显示！");
  }

  @Test
  public void aSyncSend() throws Exception {
    SimpleKafkaProducer producer = new SimpleKafkaProducer();
    producer.setBootstrapServers(kafkaServers);
    producer.initProducer();

    producer.asyncSend("test-3-1", null, "hello, this is a test message, 中文显示！");
  }
}