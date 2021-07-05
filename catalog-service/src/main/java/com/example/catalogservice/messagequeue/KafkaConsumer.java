package com.example.catalogservice.messagequeue;

import java.util.HashMap;
import java.util.Map;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.example.catalogservice.jpa.CatalogEntity;
import com.example.catalogservice.jpa.CatalogRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class KafkaConsumer {
	CatalogRepository repository;
	
	public KafkaConsumer(CatalogRepository repository) {
		this.repository = repository;
	}

	//전달받은 데이터
	@KafkaListener(topics = "example-catalog-topic")
	public void updateQty(String kafkaMessage) {
		log.info("kafka message: ->" + kafkaMessage);
		
		Map<Object, Object> map = new HashMap<>();
		ObjectMapper mapper = new ObjectMapper();
		
		try {
			map = mapper.readValue(kafkaMessage, new TypeReference<Map<Object, Object>>(){});
		} catch(JsonProcessingException ex) {
			ex.printStackTrace();
		}
		
		CatalogEntity entity = repository.findByProductId((String) map.get("productId"));
		
		if(entity != null) {
			entity.setStock(entity.getStock() - (Integer)map.get("qty"));
			repository.save(entity);
		}
	}
}
