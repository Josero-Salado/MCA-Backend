package com.mca.infrastructure.model;

import java.sql.Timestamp;

import org.springframework.cache.annotation.Cacheable;

import lombok.Builder;
import lombok.Data;


@Cacheable
@Builder
@Data
public class VideoGameEvent {
	
	private Long stock_id;

	private boolean availability;

	private Timestamp time_update;

	

}
