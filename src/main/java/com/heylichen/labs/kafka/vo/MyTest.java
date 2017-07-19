package com.heylichen.labs.kafka.vo;

import org.apache.thrift.TDeserializer;
import org.apache.thrift.TException;
import org.apache.thrift.TSerializer;
import org.apache.thrift.protocol.TCompactProtocol;

/**
 * Created by Chen Li on 2017/5/2.
 */
public class MyTest {
  public static void main(String[] args) throws TException {
    Persion person = new Persion();
    person.setName("lisi");



    TSerializer serializer = new TSerializer(new TCompactProtocol.Factory());

    byte[] bytes = serializer.serialize(person);

    TDeserializer deserializer = new TDeserializer(new TCompactProtocol.Factory());
    Persion person1 = new Persion();
    deserializer.deserialize(person1, bytes);
    System.out.println(person.equals(person1));

    System.out.println("age set:"+person.isSet(Persion._Fields.findByName("age")));
  }
}
