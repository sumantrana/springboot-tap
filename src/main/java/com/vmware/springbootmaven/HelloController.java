package com.vmware.springbootmaven;

import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@RestController
public class HelloController {

	private static final Logger LOG = LoggerFactory.getLogger(HelloController.class);

    @Value("${configfrom:Hardcoded}")
	String configfrom;

	@Value("${message:Secure Software Supply Chains Are Great!}")
	String msgSubject;

    @Value("${msg_body:Message Body Text Here.}")
	String msgBody;

	@RequestMapping("/messages")
	public Map<String, String> index() {
		LOG.info("A request has been received for the /messages endpoint.");
        LOG.debug("Config is coming from {}", configfrom);
		Map<String, String> data = new HashMap<String, String>();
		data.put("msg_subject", msgSubject);
        data.put("msg_body", msgBody);
		LOG.debug("Returning {}.", data.toString());
		return data;
	}
}