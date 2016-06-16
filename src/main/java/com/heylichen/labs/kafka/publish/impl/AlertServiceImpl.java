package com.heylichen.labs.kafka.publish.impl;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import com.heylichen.labs.kafka.publish.AlertService;
import com.heylichen.labs.kafka.vo.Spittle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsOperations;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Component;

@Component
public class AlertServiceImpl implements AlertService {
	@Autowired
	private JmsOperations jmsOperations;

	public void sendSpittleAlert(final Spittle spittle) {
		jmsOperations.send("spittle.alert.queue", new MessageCreator() {
			public Message createMessage(Session session) throws JMSException {
				return session.createObjectMessage(spittle);
			}
		});
	}
}