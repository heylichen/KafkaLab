package com.heylichen.labs.kafka.consume.impl;

import com.heylichen.labs.kafka.consume.AlertReceiver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsOperations;

import com.heylichen.labs.kafka.vo.Spittle;

/*@Component*/
public class AlertReceiverImpl implements AlertReceiver {
	@Autowired
	private JmsOperations jmsOperations;

	public Spittle receive() {
		return (Spittle) jmsOperations.receiveAndConvert("spittle.alert.queue");
	}
}
