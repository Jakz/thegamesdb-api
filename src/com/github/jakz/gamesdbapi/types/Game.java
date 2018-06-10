package com.github.jakz.gamesdbapi.types;

import java.util.Date;

import javax.xml.bind.annotation.*;

public class Game
{
  @XmlElement private int id;
  @XmlElement(name="GameTitle") private String gameTitle;
  @XmlElement(name="ReleaseDate") private Date releaseDate;
  @XmlElement(name="Platform") private String platform;
  
  public String toString()
  {
    return String.format("{ %d, %s, %s }", id, gameTitle, platform);
  }
}
