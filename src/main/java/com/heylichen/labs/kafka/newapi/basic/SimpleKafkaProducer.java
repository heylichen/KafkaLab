package com.heylichen.labs.kafka.newapi.basic;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Closeable;
import java.io.IOException;
import java.util.Properties;

/**
 * Created by Chen Li on 2017/5/7.
 * use pattern:
 * 1. call initProducer
 * 2. send any number of messages
 * 3. close at the end.
 */
public class SimpleKafkaProducer implements Closeable {
  private static final Logger LOGGER = LoggerFactory.getLogger(SimpleKafkaConsumer.class);
  private String bootstrapServers;

  //producer is thread safe, and should be shared among threads.
  private KafkaProducer<String, String> producer;

  public void initProducer() {
    Properties props = new Properties();
    props.put("bootstrap.servers", bootstrapServers);
    props.put("key.serializer", StringSerializer.class.getName());
    props.put("value.serializer", StringSerializer.class.getName());
    producer = new KafkaProducer<>(props);
  }


  public void send(String topic, String key, String message) {
    ProducerRecord<String, String> record = new ProducerRecord<>(topic, key, message);
    try {
      producer.send(record);
    } catch (Exception e) {
      LOGGER.error("error sending.", e);
    }
  }

  public void syncSend(String topic, String key, String message) {
    ProducerRecord<String, String> record = new ProducerRecord<>(topic, key, message);
    try {
      producer.send(record).get();
    } catch (Exception e) {
      LOGGER.error("error sending.", e);
    }
  }

  public void asyncSend(String topic, String key, String message) {
    ProducerRecord<String, String> record = new ProducerRecord<>(topic, key, message);
    try {
      producer.send(record, (meta, e) -> {
        if (e == null) {
          LOGGER.info("send success, meta:{}", meta);
        } else {
          LOGGER.error("error sending!", e);
        }
      });
    } catch (Exception e) {
      LOGGER.error("error sending.", e);
    }
  }

  @Override
  public void close() throws IOException {
    try {
      producer.close();
    } catch (Exception e) {
      LOGGER.error("error closing producer.", e);
    }
  }

  public String getBootstrapServers() {
    return bootstrapServers;
  }

  public void setBootstrapServers(String bootstrapServers) {
    this.bootstrapServers = bootstrapServers;
  }
}
