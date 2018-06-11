package com.github.jakz.gamesdbapi;

import java.awt.Dimension;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import javax.swing.JFrame;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;

import com.github.jakz.gamesdbapi.requests.RequestException;
import com.github.jakz.gamesdbapi.requests.RequestFactory;
import com.github.jakz.gamesdbapi.types.Game;
import com.github.jakz.gamesdbapi.types.GameList;
import com.github.jakz.gamesdbapi.types.Images;
import com.github.jakz.gamesdbapi.types.Platform;
import com.github.jakz.gamesdbapi.ui.ArtDownloaderPanel;

import jdk.incubator.http.HttpClient;
import jdk.incubator.http.HttpRequest;
import jdk.incubator.http.HttpResponse;

public class Main
{
  public static void test()
  { 
    try
    {
      JFrame frame = new JFrame();
      ArtDownloaderPanel artPanel = new ArtDownloaderPanel();
      artPanel.setPreferredSize(new Dimension(600,600));

      frame.add(artPanel);
      frame.pack();
      frame.setLocationRelativeTo(null);
      frame.setVisible(true);
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

      Images images = RequestFactory.getArt(123);
      artPanel.onResponse(images);
      
    } 
    catch (RequestException e)
    {
      e.printStackTrace();
    }

  }
  
  public static void main(String[] args)
  {
    test();
    if (true)
      return;
    
    try {
      /*List<Game> games = RequestFactory.getGameList("quake");
      games.forEach(e -> System.out.println(e));*/
      
      Images images = RequestFactory.getArt(123);
      System.out.println(images);
      
      //List<Platform> platforms = RequestFactory.getPlatformList();
      //System.out.println(platforms);
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
