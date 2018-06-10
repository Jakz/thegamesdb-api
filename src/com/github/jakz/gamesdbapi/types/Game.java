package com.github.jakz.gamesdbapi.types;

import java.net.URL;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.*;

public class Game
{
  @XmlElement private int id;
  @XmlElement(name="GameTitle") private String gameTitle;
  @XmlElement(name="PlatformId", nillable=true) private int platformId;
  @XmlElement(name="Platform") private String platform;
  @XmlElement(name="ReleaseDate") private Date releaseDate;
  
  @XmlElement(name="Overview", nillable=true) private String overview;
  @XmlElement(name="ESRB", nillable=true) private String esrb;
  
  @XmlElementWrapper(name="Genres")
  @XmlElement(name="genre", nillable=true) private List<String> genres; //TODO: should become type
  
  @XmlElement(name="YouTube", nillable=true) private URL youTube;
  
  @XmlElement(name="Images", nillable=true) private Images images;
  
  
  public String toString()
  {
    return String.format("{ %d, %s, %s }", id, gameTitle, platform);
  }
}
