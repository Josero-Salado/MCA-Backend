package com.mca.controller;

import java.sql.Connection; 
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.gson.Gson;
import com.mca.config.KafkaConfig;
import com.mca.infrastructure.model.VideoGameEvent;

import jakarta.websocket.server.PathParam;



@Controller
@RequestMapping("/game")
public class SagaController {
  // TODO: Insert code in this controller
	@Autowired
	public  KafkaConfig kafkaConfig;
	
	  @Cacheable
	  @GetMapping("/{gameId}/saga")
	  public String RelatedGameSagas(@PathParam("gameId")Integer gameId) throws SQLException {
		  Connection conexion = DriverManager.getConnection ("jdbc:mysql://localhost/system","system", "system");
		  Statement s = conexion.createStatement();
		  ResultSet rs = s.executeQuery ("select * from VIDEOGAME left join promotion on videogame.id = promotion.videogame_id left join stock on videogame.id = stock.videogame_id where videogame.id = " + gameId + "order by promotion.price");
		 List<VideoGameEvent> juegos = new ArrayList<VideoGameEvent>();
		 String result = null;
		  while (rs.next())
		  {
			  List<String> parametros = new ArrayList<String>();
			  parametros.add(rs.getString (1));
			  parametros.add(rs.getString (8));
			  parametros.add(rs.getString (4));

			  VideoGameEvent game = kafkaConfig.convertStock(parametros);
			 
			  juegos.add(game);
			  
		  }
		  result = new Gson().toJson(juegos);

		  return result;
		 	  
	  }
}
