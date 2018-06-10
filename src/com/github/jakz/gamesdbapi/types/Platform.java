package com.github.jakz.gamesdbapi.types;

import javax.xml.bind.annotation.XmlElement;

public class Platform
{
  @XmlElement private int id;
  @XmlElement private String name;
  @XmlElement private String alias;
}
