package com.mca.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.websocket.server.PathParam;


@Controller
@RequestMapping("/game")
public class SagaController {
  // TODO: Insert code in this controller
	
	
	  @GetMapping("/{gameId}/saga")
	  public String RelatedGameSagas(@PathParam("gameId")Integer gameId) {
		
		  
		  
		  
		  return null;
		  
	  
	  }
}
