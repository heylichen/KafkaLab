package com.heylichen.amq.publish.impl;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsOperations;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Component;

import com.heylichen.amq.publish.AlertService;
import com.heylichen.amq.vo.Spittle;

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