package com.cts.moviebookingapp.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class Kafka {

	public static final String topic = "mtb";

	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;

	public KafkaTemplate<String, String> getKafkaTemplate() {
		return kafkaTemplate;
	}

	public void setKafkaTemplate(String message) {
		this.kafkaTemplate.send(topic, message);
	}

	public static String getTopic() {
		return topic;
	}

}
