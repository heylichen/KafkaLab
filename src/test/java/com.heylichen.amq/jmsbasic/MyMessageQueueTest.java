package com.heylichen.amq.jmsbasic;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.heylichen.amq.jmsbasic.p2p.MyAsyncMessageConsumer;
import com.heylichen.amq.jmsbasic.p2p.MyMessageProducer;
import com.heylichen.amq.jmsbasic.p2p.MySyncMessageConsumer;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
    MyAsyncMessageConsumer asyncMessageConsumer = new MyAsyncMessageConsumer();

    exec.submit(asyncMessageConsumer);
    MyMessageProducer p = new MyMessageProducer();
    exec.submit(p);
    Thread.sleep(2000);
  }
}
