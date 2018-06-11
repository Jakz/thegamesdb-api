package com.github.jakz.gamesdbapi.ui;

import java.awt.image.BufferedImage;

import javax.swing.JButton;

import com.github.jakz.gamesdbapi.types.BoxArt;

public class ArtworkHolder
{
  BoxArt boxArt;
  BufferedImage image;
  BufferedImage thumbnail;
  JButton button;
  
  ArtworkHolder()
  {
    this(null, null);
  }
  
  ArtworkHolder(BoxArt boxArt, BufferedImage image)
  {
    this.boxArt = boxArt;
    this.image = image;
  }

  ArtworkHolder(BoxArt boxArt, BufferedImage image, BufferedImage thumb)
  {
    this(boxArt, image);
    this.thumbnail = thumb;
  }
}
