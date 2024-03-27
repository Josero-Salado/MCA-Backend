package com.mca.infrastructure.model;

import java.sql.Timestamp;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class VideoGameEvent {
	
	private Long stock_id;

	private boolean availability;

	private Timestamp time_update;

	

}
