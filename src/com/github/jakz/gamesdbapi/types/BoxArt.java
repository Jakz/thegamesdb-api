package com.github.jakz.gamesdbapi.types;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlValue;

@XmlAccessorType(XmlAccessType.FIELD)
public class BoxArt
{
  @XmlAttribute private String side; //TODO: to enum
  @XmlAttribute private int width;
  @XmlAttribute private int height;
  @XmlAttribute private String thumb;
  @XmlValue private String url;
  
  public String url() { return url; }
}
