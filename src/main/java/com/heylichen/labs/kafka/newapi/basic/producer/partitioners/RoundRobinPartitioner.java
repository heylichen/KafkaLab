package com.heylichen.labs.kafka.newapi.basic.producer.partitioners;

import org.apache.kafka.clients.producer.Partitioner;
import org.apache.kafka.common.Cluster;
import org.apache.kafka.common.record.InvalidRecordException;
import org.apache.kafka.common.utils.Utils;

import java.util.Map;

/**
 * Created by Chen Li on 2017/5/15.
 */
public class RoundRobinPartitioner implements Partitioner {

  @Override
  public void configure(Map<String, ?> configs) {
    
  }

  private static int toPositive(int number) {
    return number & 0x7fffffff;
  }
  
  @Override
  public int partition(String topic, Object key, byte[] keyBytes, Object value, byte[] valueBytes, Cluster cluster) {
    if (keyBytes == null || !(key instanceof String)) {
      throw new InvalidRecordException("We expect all messages to have non-null string as key!");
    }
    return RoundRobinPartitioner.toPositive(Utils.murmur2(keyBytes)) % cluster.partitionCountForTopic(topic);
  }

  @Override
  public void close() {

  }
}
