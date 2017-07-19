package com.heylichen.labs.kafka.newapi.basic;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.Properties;


/**
 * Created by Chen Li on 2017/5/7.
 */

public class SimpleKafkaConsumer {
  private static final Logger LOGGER = LoggerFactory.getLogger(SimpleKafkaConsumer.class);

  private String bootstrapServers;
  private String topic;

  public void subscribe() {
    Properties props = newBasicConsumerProps();
    KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
    consumer.subscribe(Collections.singletonList(topic));

    pollAndProcess(consumer);
  }


  private Properties newBasicConsumerProps() {
    Properties props = new Properties();
    props.put("bootstrap.servers", bootstrapServers);
    props.put("group.id", "SimpleKafkaConsumer");

    props.put("key.deserializer", StringDeserializer.class.getName());
    props.put("value.deserializer", StringDeserializer.class.getName());
    return props;
  }


  private void pollAndProcess(KafkaConsumer<String, String> consumer) {
    try {
      while (true) {
        ConsumerRecords<String, String> records = consumer.poll(2000);
        LOGGER.info("polling, size={}", records.count());
        for (ConsumerRecord<String, String> record : records) {
          LOGGER.info("topic = {}, partition = {}, offset = {}, key = {}, value={}",
              record.topic(), record.partition(), record.offset(), record.key(), record.value());
        }
      }
    } finally {
      consumer.close();
    }
  }

  public String getBootstrapServers() {
    return bootstrapServers;
  }

  public void setBootstrapServers(String bootstrapServers) {
    this.bootstrapServers = bootstrapServers;
  }

  public String getTopic() {
    return topic;
  }

  public void setTopic(String topic) {
    this.topic = topic;
  }
}
