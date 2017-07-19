package com.heylichen.labs.kafka.common.serialization.thrift;

import org.apache.kafka.common.serialization.Deserializer;
import org.apache.thrift.TBase;
import org.apache.thrift.TDeserializer;
import org.apache.thrift.protocol.TCompactProtocol;

import java.util.Map;

/**
 * Created by Chen Li on 2017/5/7.
 */
public class SimpleThriftDeserializer implements Deserializer {

  private Class entityClass;


  @Override
  public void configure(Map configs, boolean isKey) {
    Object className = configs.get("thrift.serializer.class");
    try {
      entityClass = Class.forName((String) className);
    } catch (ClassNotFoundException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public Object deserialize(String topic, byte[] data) {

    TDeserializer deserializer = new TDeserializer(new TCompactProtocol.Factory());
    Object instance = null;
    try {
      instance = entityClass.newInstance();
      deserializer.deserialize((TBase) instance, data);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
    return instance;
  }

  @Override
  public void close() {

  }
}
