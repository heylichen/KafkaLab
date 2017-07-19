package com.heylichen.labs.kafka.common.serialization.thrift;

import org.apache.kafka.common.serialization.Serializer;
import org.apache.thrift.TBase;
import org.apache.thrift.TException;
import org.apache.thrift.TSerializer;
import org.apache.thrift.protocol.TCompactProtocol;

import java.util.Map;

/**
 * Created by Chen Li on 2017/5/7.
 */
public class SimpleThriftSerializer implements Serializer<TBase> {
  @Override
  public void configure(Map configs, boolean isKey) {

  }

  @Override
  public byte[] serialize(String topic, TBase data) {
    TSerializer serializer = new TSerializer(new TCompactProtocol.Factory());
    try {
      return serializer.serialize(data);
    } catch (TException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public void close() {

  }
}
