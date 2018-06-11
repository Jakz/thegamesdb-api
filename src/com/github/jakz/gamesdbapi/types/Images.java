package com.github.jakz.gamesdbapi.types;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;

public class Images
{
  @XmlElement(name="boxart")
  List<BoxArt> boxarts;
  
  @XmlElement(name="screenshot")
  List<Image> screenshots;
  
  @XmlElement(name="fanarts")
  List<Image> fanart;
  
  public List<BoxArt> boxarts() { return boxarts; }
}
