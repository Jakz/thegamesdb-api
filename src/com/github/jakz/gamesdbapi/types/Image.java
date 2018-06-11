package com.github.jakz.gamesdbapi.types;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlValue;

public class Image implements Downloadable
{
  public static class Original
  {
    @XmlAttribute private int width;
    @XmlAttribute private int height;
    @XmlValue private String url;
  }
  
  @XmlElement(name="original") private Original original;
  @XmlElement(name="thumb") private String thumb;
  private String caption;
  
  @Override public String url() { return original.url; }
  

  public void setCaption(String caption) { this.caption = caption; }
  @Override public String toString() { return caption; }
}
