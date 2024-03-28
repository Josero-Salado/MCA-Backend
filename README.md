<em> Aplicación para obetener una lista de videojuegos relacionados con la saga.</em>

Para el desarrollo de esta aplicación he empezado por descargar instalar y configurar todo el entorno de desarrollo, para ello
he tenido que descargar eclipse y mysql para la base de datos, docker para la dockerisación y que kafka funcione, crear una rama
en github y descargar sourcetree para operar sobre esa rama main, a la cual se le ha sacado una rama development sobre la que se trabaja 
y conectar el yeml de la api con swagger.

Para el desarrollo en eclipse de la aplicación he creado un objeto llamado VideoGame el cual contiene como datos:

	private String id;
	
	private String title;
	
	private String price;
	
	private Boolean availability;
Ya que los indicados en el yaml no son posibles puesto que no hay datos en la base de datos para genre ni realease date y además 
los datos a los que se le hace assert en los test unitarios son sobre esos 4 datos.

Tras esto he procedido a desarrollar el controlador que es un restcontroller para poder hacer llamadas rest api el cual tiene dentro el metodo
RelatedGameSagas que pilla de la url por la que se llama un id de un videojuego y devuelve un string en forma de json con la lista de videojuegos relacionados.
la url a seguir es game/{gameId}/saga, donde el gameId es la id de un juego existente en la base de datos. Para poder hacer llamada a la api hayq ue ejecutar la aplicación
de springboot y en un navegador usar la url: localhost:8080/game/{gameId}/saga.

Dentro de este método se hace una llamada a la api ya generada anteriormente: http://localhost:3000/game-saga/{gameId}/related-sagas" que 
devuelve un json con los id de lois juegos relacionados al parametro de entrada. Tras eso se hace una llamada a la base de datos para cada
id que devuelva los datos correspondientes al juego:  			  
select * from VIDEOGAME left join promotion on videogame.id = promotion.videogame_id left join stock
on videogame.id = stock.videogame_id where videogame.id = " + RelatedGamesId[i] + " and promotion.valid_from = stock.last_updated

Estos videojuegos además se han filtado por el dia de edición en stock junto con el dia de validez de la promoción mostrando solo la 
promoción valida según la edición, ya que hay dos promociones por videojuego crei que era lo mas normal por hacer, lo cualme llevó a editar las
pruebas unitarias haceindo que los precios correspondan a mi observación.

Una vez recuperado la lista de videojuegos relacionados con sus propiedades se vuelven a convertir en json y se devuelven.

En este desarrollo me he encontrado varios retos:

-Para empezar no tengo conocimientos de kafka y docker y he tenido que investigar por mi cuenta como dockerizar el proyecto y por que kafka no me ejecutaba, además de que al principio kafka
me daba errores de compilación.
-Llevaba bastante tiempo trabajando con soap en vez de con rest y volver a utilizar rest, usando una api existente y creando una mia ha sido un trabajo de hacer memoria.
-El readme aportado por la empresa daba poca información y el tiempo para hacerlo ha sido limitado ya que estando de vacaciones hay ciertos momentos en los que he tenido que dejar de trabajarlo
y no me han podido responder alguna duda.

Por lo general estoy contento con mi desarrollo y creo que pese a mi falta de conocimiento en kafka en general he desarrollado un buen proyecto.

