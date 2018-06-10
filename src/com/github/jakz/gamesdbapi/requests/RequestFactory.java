package com.github.jakz.gamesdbapi.requests;

import java.io.IOException;

import java.io.StringReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.concurrent.CompletionException;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ExecutionException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;

import com.github.jakz.gamesdbapi.types.ArtList;
import com.github.jakz.gamesdbapi.types.Game;
import com.github.jakz.gamesdbapi.types.GameList;
import com.github.jakz.gamesdbapi.types.Images;
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
  
  private static <T> CompletionStage<T> handleAsyncRequest(String uri, Class<T> type)
  {
      System.out.println("Request: " + uri);
      
      try
      {
        HttpRequest request = HttpRequest.newBuilder()
            .uri(new URI(uri))
            .GET()
            .build();
         
        //TODO: switch to InputStream for efficiency?
        //CompletionStage<HttpResponse<String>> future = ;
        return 
          client.sendAsync(request, HttpResponse.BodyHandler.asString())
          .thenApply(response -> {
            try
            {
              if (response.statusCode() != 200)
                throw new RequestException("Bad HTTP status code: ", response.statusCode());
              
              System.out.println("Response: \n" + response.body());
              
              JAXBContext jaxbContext = JAXBContext.newInstance(type);
              Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
              
              @SuppressWarnings("unchecked")
              T t = (T) unmarshaller.unmarshal(new StreamSource(new StringReader(response.body())));
              
              return t;
            }
            catch (Exception e)
            {
              throw new RuntimeException(e);
            }
          })
          .exceptionally(e -> {
            try
            {
              if (e instanceof CompletionException)
                e = e.getCause().getCause();
              
              if (e instanceof IOException || e instanceof InterruptedException)
                throw new RequestException(e, "HTTP Request error");
              else if (e instanceof JAXBException)
                throw new RequestException(e, "JAXB Parsing error");
              else
                throw e;
            }
            catch (Throwable ex)
            {
              throw new RuntimeException(ex);
            }
          });
      }
      catch (URISyntaxException e)
      {
        e.printStackTrace();
        return null;
      }
  }
  
  private static <T> T handleRequest(String uri, Class<T> type) throws RequestException
  {
    CompletionStage<T> stage = handleAsyncRequest(uri, type);
    
    try
    {
      return stage.toCompletableFuture().get();
    }
    catch (ExecutionException e)
    {
      Throwable t = e.getCause().getCause();

      if (t instanceof RequestException)
        throw (RequestException)t;
      return null;
    }
    catch (InterruptedException e)
    {
      e.printStackTrace();
      return null;
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
  
  public static Images getArt(int id) throws RequestException
  {
    String uri = "http://thegamesdb.net/api/GetArt.php?";
    uri = appendParameter(uri, "id", String.valueOf(id));
    
    return handleRequest(uri, ArtList.class).images;
  }
  
  public static List<Platform> getPlatformList() throws RequestException
  {
    String uri = "http://thegamesdb.net/api/GetPlatformsList.php";
    PlatformList platforms = handleRequest(uri, PlatformList.class);
    return platforms != null ? platforms.platforms : null;
  }
}
