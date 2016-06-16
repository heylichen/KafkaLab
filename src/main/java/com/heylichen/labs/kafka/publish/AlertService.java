package com.heylichen.labs.kafka.publish;

import com.heylichen.labs.kafka.vo.Spittle;

public interface AlertService {

  void sendSpittleAlert(Spittle spittle);

}