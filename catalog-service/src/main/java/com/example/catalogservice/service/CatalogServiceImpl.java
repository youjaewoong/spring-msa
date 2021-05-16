package com.example.catalogservice.service;

import org.springframework.stereotype.Service;

import com.example.catalogservice.jpa.CatalogEntity;
import com.example.catalogservice.jpa.CatalogRepository;

@Service
public class CatalogServiceImpl implements CatalogService{

	CatalogRepository catalogRepository;
	
	public CatalogServiceImpl(CatalogRepository catalogRepository) {
		this.catalogRepository = catalogRepository;
	}

	@Override
	public Iterable<CatalogEntity> getAllCatalogs() {
		return catalogRepository.findAll();
	}

	
}
