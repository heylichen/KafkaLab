package com.heylichen.labs.kafka.jms.basic;

import com.heylichen.labs.kafka.jms.basic.pubsub.MyAsyncMessageSubscriber;
import com.heylichen.labs.kafka.jms.basic.pubsub.durable.DurableAsyncMessageSubscriber;
import com.heylichen.labs.kafka.jms.basic.pubsub.durable.PeriodMessagePublisher;
import com.heylichen.labs.kafka.jms.basic.pubsub.MyMessagePublisher;
import com.heylichen.labs.kafka.jms.basic.pubsub.MySyncMessageSubscriber;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by lichen2 on 2016/6/1.
 */
public class MyMessageTopicTest {

  private static final Logger logger = LoggerFactory.getLogger(MyMessageTopicTest.class);
  private ExecutorService exec = Executors.newFixedThreadPool(6);

  @Test
  public void pubWithSyncSubs() throws Exception {
    MySyncMessageSubscriber consumer = new MySyncMessageSubscriber();
    exec.submit(consumer);
    MySyncMessageSubscriber consumer1 = new MySyncMessageSubscriber();
    exec.submit(consumer1);
    MyMessagePublisher p = new MyMessagePublisher();
    exec.submit(p);
    Thread.sleep(3000);
  }

  @Test
  public void pubWithAsyncSubs() throws Exception {
    MyAsyncMessageSubscriber sub1 = new MyAsyncMessageSubscriber();
    MyAsyncMessageSubscriber sub2 = new MyAsyncMessageSubscriber();
    MyAsyncMessageSubscriber sub3 = new MyAsyncMessageSubscriber();
    MyMessagePublisher p = new MyMessagePublisher();
    exec.submit(sub1);
    exec.submit(sub2);
    exec.submit(sub3);
    exec.submit(p);
    Thread.sleep(3000);
  }


  @Test
  public void durableTest() throws Exception {
    boolean durable =false;
    PeriodMessagePublisher p = new PeriodMessagePublisher();
    DurableAsyncMessageSubscriber subscriber = new DurableAsyncMessageSubscriber(durable);

    exec.submit(subscriber);
    exec.submit(p);
    Thread.sleep(9000);

    subscriber = new DurableAsyncMessageSubscriber(durable);
    exec.submit(subscriber);

    Thread.sleep(3000);
  }
}
