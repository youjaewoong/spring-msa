package com.example.catalogservice.controller;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.catalogservice.jpa.CatalogEntity;
import com.example.catalogservice.service.CatalogService;
import com.example.catalogservice.vo.ResponseCatalog;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("/catalog-service")
public class CatalogController {
	
	private Environment env;
	private CatalogService catalogService;

	public CatalogController(Environment env, CatalogService catalogService) {
		this.env = env;
		this.catalogService = catalogService;
	}

	@GetMapping("/heath-check")
	public String status() {
		return String.format("It's Working in User Service on PORT %s", 
				env.getProperty("local.server.port"));
	}

	@GetMapping("/catalogs")
	public ResponseEntity<List<ResponseCatalog>> getUsers() {
		
		Iterable<CatalogEntity> catalogList = catalogService.getAllCatalogs();
		
		List<ResponseCatalog> result = new ArrayList<>();
		catalogList.forEach(v ->{
			result.add(new ModelMapper().map(v, ResponseCatalog.class));
		});
		
		return ResponseEntity.status(HttpStatus.OK).body(result);
	}
	

}
