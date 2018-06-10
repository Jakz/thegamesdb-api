package com.github.jakz.gamesdbapi.types;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="Data")
public class PlatformList
{
  @XmlElementWrapper(name="Platforms")
  @XmlElement(name="Platform")
  public List<Platform> platforms;
}
