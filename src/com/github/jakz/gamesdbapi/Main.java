package com.github.jakz.gamesdbapi;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;

import com.github.jakz.gamesdbapi.requests.RequestException;
import com.github.jakz.gamesdbapi.requests.RequestFactory;
import com.github.jakz.gamesdbapi.types.Game;
import com.github.jakz.gamesdbapi.types.GameList;

import jdk.incubator.http.HttpClient;
import jdk.incubator.http.HttpRequest;
import jdk.incubator.http.HttpResponse;

public class Main
{
  public static void main(String[] args)
  {
    try {
      /*List<Game> games = RequestFactory.getGameList("quake");
      games.forEach(e -> System.out.println(e));*/
      
      Game game = RequestFactory.getGameByID(123);
      System.out.println(game);
    } 
    catch (RequestException e) 
    {
      e.printStackTrace();
    }
    
    
    /*try 
    { 
      HttpClient client = HttpClient.newHttpClient();
      
      HttpRequest request = HttpRequest.newBuilder()
        .uri(new URI("http://thegamesdb.net/api/GetGamesList.php?name=halo"))
        .header("name", "halo")
        .GET()
        .build();
      
      
      System.out.println("Request: "+request.toString());
      
      HttpResponse<String> response = client.send(request, HttpResponse.BodyHandler.asString());
      
      System.out.println(response.statusCode());
      System.out.println(response.body());
      
      if (response.statusCode() == 200)
      {
        JAXBContext jaxbContext = JAXBContext.newInstance(GameList.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        GameList games = (GameList) unmarshaller.unmarshal(new StreamSource(new StringReader(response.body())));
        
        games.games.forEach(e -> System.out.println(e.toString()));
        
      }
      
      

      
    } 
    catch (URISyntaxException | IOException | InterruptedException e)
    {
      e.printStackTrace();
    } catch (JAXBException e)
    {
      e.printStackTrace();
    }*/
  }
}