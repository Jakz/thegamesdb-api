package com.github.jakz.gamesdbapi.requests;

import java.io.IOException;

import java.io.StringReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;

import com.github.jakz.gamesdbapi.types.Game;
import com.github.jakz.gamesdbapi.types.GameList;
import com.github.jakz.gamesdbapi.types.Platform;
import com.github.jakz.gamesdbapi.types.PlatformList;

import jdk.incubator.http.HttpClient;
import jdk.incubator.http.HttpRequest;
import jdk.incubator.http.HttpResponse;

/*
GetGamesList +
GetGame
GetArt
GetPlatformsList +
GetPlatform
GetPlatformGames
PlatformGames
Updates
UserRating
UserFavorites
 */

public class RequestFactory
{
  private final static HttpClient client;
 
  static
  {
    client = HttpClient.newHttpClient();
  }
  
  private static String appendParameter(String uri, String key, String value)
  {
    if (value != null && !value.isEmpty())
      return uri + (uri.endsWith("?") ? "" : "&") + key + "=" + value;
    else
      return uri;
  }
  
  private static <T> T handleRequest(String uri, Class<T> type) throws RequestException
  {
    try 
    {
      System.out.println("Request: " + uri);
      
      HttpRequest request = HttpRequest.newBuilder()
          .uri(new URI(uri))
          .GET()
          .build();
      
      //TODO: switch to InputStream for efficiency?
      HttpResponse<String> response = client.send(request, HttpResponse.BodyHandler.asString());
      
      if (response.statusCode() != 200)
        throw new RequestException("Bad HTTP status code: ", response.statusCode());
      
      System.out.println("Response: \n" + response.body());
      
      JAXBContext jaxbContext = JAXBContext.newInstance(type);
      Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
      
      @SuppressWarnings("unchecked")
      T t = (T) unmarshaller.unmarshal(new StreamSource(new StringReader(response.body())));
      
      return t;
    } 
    catch (URISyntaxException e)
    {
      e.printStackTrace();
      return null;
    } 
    catch (IOException|InterruptedException e)
    {
      throw new RequestException(e, "HTTP Request error");
    } 
    catch (JAXBException e)
    {
      throw new RequestException(e, "JAXB Parsing error");
    }
  }
  
  
  
  public static List<Game> getGameList(String name) throws RequestException { return getGameList(name, null, null); }
  public static List<Game> getGameList(String name, String platform, String genre) throws RequestException
  {
    String uri = "http://thegamesdb.net/api/GetGamesList.php?";
    
    if (name == null || name.isEmpty())
      throw new RequestException("Name argument can't be null or empty");
    
    uri = appendParameter(uri, "name", name);
    uri = appendParameter(uri, "platform", platform);
    uri = appendParameter(uri, "genre", genre);
    
    return handleRequest(uri, GameList.class).games;
  }
  
  private static List<Game> getGame(String type, String name, String platform) throws RequestException
  {
    String uri = "http://thegamesdb.net/api/GetGame.php?";
    
    uri = appendParameter(uri, type, name);
    uri = appendParameter(uri, "platform", platform);
    
    return handleRequest(uri, GameList.class).games;
  }
  
  public static Game getGameByID(int id, String platform) throws RequestException { return getGame("id", Integer.toString(id), platform).get(0); }
  public static Game getGameByID(int id) throws RequestException { return getGame("id", Integer.toString(id), null).get(0); }
  
  public static List<Platform> getPlatformList() throws RequestException
  {
    String uri = "http://thegamesdb.net/api/GetPlatformsList.php";
    return handleRequest(uri, PlatformList.class).platforms;
  }
}
