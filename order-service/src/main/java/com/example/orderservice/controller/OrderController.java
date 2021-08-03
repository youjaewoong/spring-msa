package com.example.orderservice.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.orderservice.dto.OrderDto;
import com.example.orderservice.jpa.OrderEntity;
import com.example.orderservice.messagequeue.KafkaProducer;
import com.example.orderservice.messagequeue.OrderProducer;
import com.example.orderservice.service.OrderService;
import com.example.orderservice.vo.RequestOrder;
import com.example.orderservice.vo.ResponseOrder;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("/order-service")
public class OrderController {
	
	private Environment env;
	private OrderService orderService;
	private KafkaProducer kafkaProducer;
	
	private OrderProducer orderProducer;

	public OrderController(Environment env, OrderService orderService, KafkaProducer kafkaProducer, OrderProducer orderProducer) {
		this.env = env;
		this.orderService = orderService;
		this.kafkaProducer = kafkaProducer;
		this.orderProducer = orderProducer;
	}

	@GetMapping("/heath-check")
	public String status() {
		return String.format("It's Working in Order Service on PORT %s", 
				env.getProperty("local.server.port"));
	}

	@PostMapping("/{userId}/orders")
	public ResponseEntity<ResponseOrder> createOrder(
			@PathVariable("userId") String userId,
			@RequestBody RequestOrder order) {
		log.info("Before add orders data");
		ModelMapper mapper = new ModelMapper();
		mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		
		OrderDto orderDto = mapper.map(order, OrderDto.class);
		orderDto.setUserId(userId);
		
		/* jpa */
		OrderDto createdOrder = orderService.createOrder(orderDto);
		ResponseOrder responseOrder = mapper.map(createdOrder, ResponseOrder.class);
		
		/* kafka */
//		orderDto.setOrderId(UUID.randomUUID().toString());
//		orderDto.setTotalPrice(orderDto.getQty() * orderDto.getUnitPrice());
		
		/* send this order to the kafka */
		kafkaProducer.send("example-catalog-topic", orderDto);
//		orderProducer.send("orders", orderDto);
		
//		ResponseOrder responseOrder = mapper.map(orderDto, ResponseOrder.class);
		
		log.info("After add orders data");
		return ResponseEntity.status(HttpStatus.CREATED).body(responseOrder);
	}
	
	@GetMapping("/{userId}/orders")
	public ResponseEntity<List<ResponseOrder>> getorder(@PathVariable("userId") String userId) throws Exception {
		log.info("Before retrieve  orders data");
		Iterable<OrderEntity> orderList = orderService.getOrdersByUserId(userId);
		
		List<ResponseOrder> result = new ArrayList<>();
		orderList.forEach(v ->{
			result.add(new ModelMapper().map(v, ResponseOrder.class));
		});
		
		/*
		try {
			Thread.sleep(1000);
			throw new Exception("장애 발생");
		}catch(InterruptedException ex) {
			log.warn(ex.getMessage());
		}
		*/
		
		log.info("Add retrieve  orders data");
		return ResponseEntity.status(HttpStatus.OK).body(result);
	}
	

}
