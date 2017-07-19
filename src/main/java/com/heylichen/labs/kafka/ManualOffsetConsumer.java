package com.heylichen.labs.kafka;

import com.alibaba.fastjson.JSON;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

/**
 * Created by Chen Li on 2016/10/10.
 */
public class ManualOffsetConsumer {

  public static void main(String[] args) {
    Properties props = new Properties();
    props.put("bootstrap.servers", "192.168.1.102:9092");
    props.put("group.id", "g1");
    props.put("enable.auto.commit", "false");
    props.put("auto.commit.interval.ms", "1000");
    props.put("session.timeout.ms", "30000");
    props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
    props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
    KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
    consumer.subscribe(Arrays.asList("test2"));
    final int minBatchSize = 5;
    List<ConsumerRecord<String, String>> buffer = new ArrayList<>();
    while (true) {
      ConsumerRecords<String, String> records = consumer.poll(100);
      for (ConsumerRecord<String, String> record : records) {
        buffer.add(record);
      }
      if (buffer.size() >= minBatchSize) {
        List<String> values = buffer.stream().map(r->r.partition()+":"+r.value()).collect(Collectors.toList());
        System.out.println(JSON.toJSONString(values));

        consumer.commitSync();
        buffer.clear();
      }
    }

  }
}
