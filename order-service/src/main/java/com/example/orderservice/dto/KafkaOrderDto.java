package com.example.orderservice.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class KafkaOrderDto implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4492262117625649411L;
	private Schema schema;
	private Payload payload;
	
}
