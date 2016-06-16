package com.heylichen.labs.kafka;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import com.heylichen.labs.kafka.publish.AlertService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.heylichen.labs.kafka.consume.AlertReceiver;
import com.heylichen.labs.kafka.vo.Spittle;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {

	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	@Autowired
	private AlertService alertService;
	// @Autowired
	private AlertReceiver alertReceiver;

	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);

		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);

		String formattedDate = dateFormat.format(date);

		model.addAttribute("serverTime", formattedDate);

		return "home";
	}

	@RequestMapping(value = "/startProducer", method = RequestMethod.GET)
	public String startProducer(Locale locale, Model model) {
		Spittle spittle = new Spittle();
		spittle.setAge(12);
		spittle.setName("张三");
		alertService.sendSpittleAlert(spittle);
		logger.info("producer send:{}", spittle);
		return "startProducer";
	}

	@RequestMapping(value = "/startConsumer", method = RequestMethod.GET)
	public String startConsumer(Locale locale, Model model) {
		Spittle spittle = alertReceiver.receive();
		logger.info("message received:{}", spittle);
		return "startConsumer";
	}
}
