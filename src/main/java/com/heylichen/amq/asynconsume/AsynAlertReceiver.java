package com.heylichen.amq.asynconsume;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import com.heylichen.amq.vo.Spittle;

@Component
public class AsynAlertReceiver implements MessageListener {
	private static final Logger logger = LoggerFactory.getLogger(AsynAlertReceiver.class);

	@JmsListener(containerFactory = "connectionFactory", destination = "spitte.alert.queue")
	@Override
	public void onMessage(Message m) {
		if (m instanceof ObjectMessage) {
			ObjectMessage om = (ObjectMessage) m;
			try {
				Spittle spittle = (Spittle) om.getObject();
				logger.info("message received:{}", spittle);
			} catch (Exception e) {
				logger.error("error", e);

			}

		}
	}

}
