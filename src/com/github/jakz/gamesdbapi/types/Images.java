package com.github.jakz.gamesdbapi.types;

import java.util.List;

import javax.xml.bind.Unmarshaller;
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
  public List<Image> screenshots() { return screenshots; }
  
  void afterUnmarshal(Unmarshaller u, Object parent)
  {
    if (screenshots != null)
      for (int i = 0; i < screenshots.size(); ++i)
        screenshots.get(i).setCaption("screenshot"+(i+1));
    
    if (fanart != null)
      for (int i = 0; i < fanart.size(); ++i)
        fanart.get(i).setCaption("fanart"+(i+1));
  }
}
