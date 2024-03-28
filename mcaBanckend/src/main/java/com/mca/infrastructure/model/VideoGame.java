package com.mca.infrastructure.model;

import org.springframework.cache.annotation.Cacheable;

import lombok.Builder;
import lombok.Data;

@Cacheable
@Builder
@Data
public class VideoGame {
	
	private String id;
	
	private String title;
	
	private String price;
	
	private Boolean availability;

	

	

}