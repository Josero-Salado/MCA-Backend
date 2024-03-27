package com.mca.infrastructure;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;


@Component
public class KafkaMessageProducer<K extends String, V extends String> {

	@Autowired
	private KafkaTemplate<K, V> kafkaTemplate;

	@Value(value = "${topic}")
	private String topicName;

	public void sendMessage(final K id, final V message) {
		this.kafkaTemplate.send(this.topicName, message);
	}
}
