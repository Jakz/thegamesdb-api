package com.github.jakz.gamesdbapi.types;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="Data")
@XmlAccessorType(XmlAccessType.FIELD)
public class ArtList
{
  @XmlElement public String baseImgUrl;
  
  @XmlElement(name="Images") public Images images;
}
