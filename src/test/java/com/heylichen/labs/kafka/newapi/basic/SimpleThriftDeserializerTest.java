package com.heylichen.labs.kafka.newapi.basic;

import com.heylichen.labs.kafka.vo.Persion;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.Properties;

/**
 * Created by Chen Li on 2017/5/7.
 */
public class SimpleThriftDeserializerTest {
  private Logger logger = LoggerFactory.getLogger(SimpleThriftDeserializerTest.class);

  @Test
  public void producer() throws Exception {
    Properties kafkaProps = new Properties();
    kafkaProps.put("bootstrap.servers", "192.168.1.102:9092");
    kafkaProps.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
    kafkaProps.put("value.serializer", "com.heylichen.labs.kafka.common.serialization.thrift.SimpleThriftSerializer");
    KafkaProducer<String, Persion> producer = new KafkaProducer<>(kafkaProps);

    Persion p = new Persion();
    p.setName("zhangsan");
    p.setAge(23);

    ProducerRecord<String, Persion> record =
        new ProducerRecord<>("test-3-1", p);
    try {
      producer.send(record);
      Thread.sleep(500);
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      producer.close();
    }

  }

  @Test
  public void consumer() throws Exception {
    Properties props = new Properties();
    props.put("bootstrap.servers", "192.168.1.102:9092");
    props.put("group.id", "CountryCounter");

    props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
    props.put("value.deserializer", "com.heylichen.labs.kafka.common.serialization.thrift.SimpleThriftDeserializer");
    props.put("thrift.serializer.class", "com.heylichen.labs.kafka.vo.Persion");


    KafkaConsumer<String, Persion> consumer = new KafkaConsumer<>(props);
    consumer.subscribe(Collections.singletonList("test-3-1"));

    try {
      while (true) {
        ConsumerRecords<String, Persion> records = consumer.poll(2000);
        logger.info("polling, size={}", records.count());
        for (ConsumerRecord<String, Persion> record : records) {
          logger.info("topic = {}, partition = {}, offset = {}, key = {}, value={}, valueType = {}",
              record.topic(), record.partition(), record.offset(), record.key(), record.value(), record.value().getClass());
        }
      }
    } finally {
      consumer.close();
    }

  }
}