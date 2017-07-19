package com.heylichen.labs.kafka.newapi.basic;

import com.heylichen.labs.test.common.AbstractTestContext;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Value;

/**
 * Created by Chen Li on 2017/5/14.
 */

public class SimpleKafkaConsumerTest extends AbstractTestContext {
  @Value("${bootstrap.servers}")
  private String kafkaServers;

  @Test
  public void subscribe() throws Exception {
    SimpleKafkaConsumer consumer = new SimpleKafkaConsumer();
    consumer.setTopic("test-3-1");
    consumer.setBootstrapServers(kafkaServers);
    consumer.subscribe();
  }
}