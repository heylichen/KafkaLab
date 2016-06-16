package com.heylichen.labs.kafka.consume;

import com.heylichen.labs.kafka.vo.Spittle;

public interface AlertReceiver {

  Spittle receive();

}