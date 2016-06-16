package com.heylichen.labs.kafka.jms.basic;

import com.heylichen.labs.kafka.jms.basic.p2p.MySyncMessageConsumer;
import com.heylichen.labs.kafka.jms.basic.p2p.MyMessageProducer;
import com.heylichen.labs.kafka.jms.basic.p2p.TransientAsyncMessageConsumer;
import com.heylichen.labs.kafka.jms.basic.p2p.durable.PeriodMessageProducer;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by lichen2 on 2016/6/1.
 */
public class MyMessageQueueTest {

  private static final Logger logger = LoggerFactory.getLogger(MyMessageQueueTest.class);
  private ExecutorService exec = Executors.newFixedThreadPool(6);

  @Test
  public void pubWithSyncConsumers() throws Exception {
    MySyncMessageConsumer consumer = new MySyncMessageConsumer();
    MyMessageProducer p = new MyMessageProducer();
    exec.submit(consumer);
    exec.submit(p);
    Thread.sleep(2000);
  }

  @Test
  public void pubWithAsyncConsumers() throws Exception {
    TransientAsyncMessageConsumer asyncMessageConsumer = new TransientAsyncMessageConsumer();

    exec.submit(asyncMessageConsumer);
    MyMessageProducer p = new MyMessageProducer();
    exec.submit(p);
    Thread.sleep(2000);
  }

  /**
   * queue hold the message until consumer reconnect or message runs out of ttl(time to live)
   * default ttl :0
   * so the consumer will not miss any message when reconnecting.
   *
   * @throws Exception
   */
  @Test
  public void durableTest() throws Exception {

    PeriodMessageProducer p = new PeriodMessageProducer();
    TransientAsyncMessageConsumer subscriber = new TransientAsyncMessageConsumer();
    subscriber.setTtl(3000);
    exec.submit(subscriber);
    exec.submit(p);
    Thread.sleep(9000);

    subscriber = new TransientAsyncMessageConsumer();
    subscriber.setTtl(2000);
    exec.submit(subscriber);
    Thread.sleep(3000);
  }

  /**
   * queue hold the message until consumer reconnect or message runs out of ttl(time to live)
   * set ttl: 3000, let some msg run out of ttl,
   * so the consumer will miss some messages when reconnecting.
   *
   * @throws Exception
   */
  @Test
  public void durableWithTTLTest() throws Exception {
    PeriodMessageProducer p = new PeriodMessageProducer();
    p.setMsgTtl(3000);
    TransientAsyncMessageConsumer subscriber = new TransientAsyncMessageConsumer();
    subscriber.setTtl(3000);
    exec.submit(subscriber);
    exec.submit(p);
    Thread.sleep(9000);

    subscriber = new TransientAsyncMessageConsumer();
    subscriber.setTtl(2000);
    exec.submit(subscriber);
    Thread.sleep(3000);
  }
}
