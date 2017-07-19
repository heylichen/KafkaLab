package com.heylichen.labs.kafka;

import org.apache.kafka.clients.producer.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

/**
 * Created by Chen Li on 2016/10/6.
 */
public class ProducerDemo {
  private static final Logger logger = LoggerFactory.getLogger(ProducerDemo.class);

  public static void main(String[] args) {
    Properties props = new Properties();
    props.put("bootstrap.servers", "192.168.1.102:9092");
    props.put("acks", "all");
    props.put("retries", 0);
    props.put("batch.size", 16384);
    props.put("linger.ms", 1);
    props.put("buffer.memory", 33554432);
    props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
    props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

    Producer<String, String> producer = new KafkaProducer<>(props);
    for (int i = 0; i < 1000; i++) {
      String value = "msg:100"+Integer.toString(i);
      producer.send(new ProducerRecord<>("topic3-3", value, value), new Callback() {
        @Override
        public void onCompletion(RecordMetadata metadata, Exception e) {
          if (e != null)
            logger.error("the producer has a error:" + e.getMessage());
          else {
            logger.info("[{},{}]: {}",metadata.partition(),metadata.offset(),value);
          }
        }
      });

      try {
        Thread.sleep(3000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }

    producer.flush();
    producer.close();
  }
}
