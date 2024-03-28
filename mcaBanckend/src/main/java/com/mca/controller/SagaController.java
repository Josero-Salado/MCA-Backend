package com.mca.controller;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;  
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.mca.config.KafkaConfig;
import com.mca.infrastructure.model.VideoGame;


@RestController
@RequestMapping("/game")
public class SagaController {
	
	Logger logger = LoggerFactory.getLogger(SagaController.class);
	
	@Autowired
	public  KafkaConfig kafkaConfig;
	
	  @Cacheable
	  @GetMapping("/{gameId}/saga")
	  public String RelatedGameSagas(@PathVariable(name = "gameId")String gameId) throws SQLException, IOException {
		  		  
		  String result = null;
		  
		  String[] relatedGamesId = relatedSagaCallApi(gameId);
		  logger.info("Los ids de los juegos relacionados a "+gameId+": "+relatedGamesId);
		 
		  
		  List<VideoGame> juegos = listVideoGames(relatedGamesId);
		 
		  result = new Gson().toJson(juegos);
		  logger.info("lista de videojuegos relacionados: " +result);
		  return result;	 	  
	  }
	  
	  
	  
	  
	  public String[] relatedSagaCallApi(String id) throws IOException {
		  URL url = new URL("http://localhost:3000/game-saga/"+id+"/related-sagas");
		  HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		  conn.setRequestMethod("GET");
		  conn.connect();
		  String inline = "";
		  Scanner scanner = new Scanner(url.openStream());
		  while (scanner.hasNext()) {
		       inline += scanner.nextLine();
		    }
		  scanner.close();
		  inline = inline.replace("[", "");
		  inline = inline.replace("]", "");
		  
		return inline.split(",");  
	  }
	  
	  public ResultSet mySqlCall(String id) throws SQLException {
		  ResultSet result = null;
		  Connection conexion = DriverManager.getConnection ("jdbc:mysql://localhost:3306/system","sys", "system");
		  Statement s = conexion.createStatement();
		  String query = "select * from VIDEOGAME left join promotion on videogame.id = promotion.videogame_id left join stock on videogame.id = stock.videogame_id where videogame.id = " + id + " and promotion.valid_from = stock.last_updated";
		  result = s.executeQuery (query);
		  return result ;
	  }
	  
	  public List<VideoGame> listVideoGames(String[] relatedGamesId) throws SQLException{
		  List<VideoGame> juegos = new ArrayList<VideoGame>();
		  for (int i = 0; i < relatedGamesId.length; i++) {
			  
			  ResultSet rs = mySqlCall(relatedGamesId[i]);
			  while (rs.next()) {
			
				  
				  VideoGame game = VideoGame.builder().id(rs.getString(6)).title(rs.getString(2)).price(rs.getString(5)).availability(rs.getBoolean(8)).build();				 
				  logger.info("videojuego relacionado: " +game.toString());
				  juegos.add(game);		  
			  }	  
		  }
		  return juegos;
	  }  
	  
}
