package com.github.jakz.gamesdbapi.types;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlValue;

public class Image
{
  public static class Original
  {
    @XmlAttribute private int width;
    @XmlAttribute private int height;
    @XmlValue private String url;
  }
  
  @XmlElement(name="original") private Original original;
  @XmlElement(name="thumb") private String thumb;
}
