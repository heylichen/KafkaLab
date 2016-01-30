package com.heylichen.amq.consume.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsOperations;

import com.heylichen.amq.consume.AlertReceiver;
import com.heylichen.amq.vo.Spittle;

/*@Component*/
public class AlertReceiverImpl implements AlertReceiver {
	@Autowired
	private JmsOperations jmsOperations;

	public Spittle receive() {
		return (Spittle) jmsOperations.receiveAndConvert("spittle.alert.queue");
	}
}
