package com.example.orderservice.service;

import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Service;

import com.example.orderservice.dto.OrderDto;
import com.example.orderservice.jpa.OrderEntity;
import com.example.orderservice.jpa.OrderRepository;

@Service
public class OrderServiceImpl implements OrderService{

	OrderRepository orderRepository;
	
	public OrderServiceImpl(OrderRepository orderRepository) {
		this.orderRepository = orderRepository;
	}

	@Override
	public OrderDto createOrder(OrderDto orderDto) {
		orderDto.setOrderId(UUID.randomUUID().toString());
		orderDto.setTotalPrice(orderDto.getQty() * orderDto.getUnitPrice());
		
		ModelMapper mapper = new ModelMapper();
		mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		OrderEntity orderEntity = mapper.map(orderDto, OrderEntity.class);
		
		orderRepository.save(orderEntity);
		
		OrderDto returnVaule= mapper.map(orderEntity, OrderDto.class);
		
		return returnVaule;
	}

	@Override
	public OrderDto getOrderByOrderId(String orderId) {
		OrderEntity orderEntity = orderRepository.findByOrderId(orderId);
		OrderDto orderDto = new ModelMapper().map(orderEntity, OrderDto.class);
		return orderDto;
	}
	
	@Override
	public Iterable<OrderEntity> getOrdersByUserId(String userId) {
		return orderRepository.findByUserId(userId);
	}
	
	
}
