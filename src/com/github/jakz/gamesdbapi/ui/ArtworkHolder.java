package com.github.jakz.gamesdbapi.ui;

import java.awt.image.BufferedImage;

import javax.swing.JButton;

import com.github.jakz.gamesdbapi.types.Downloadable;

public class ArtworkHolder
{
  Downloadable boxArt;
  BufferedImage image;
  BufferedImage thumbnail;
  JButton button;
  
  ArtworkHolder()
  {
    this(null, null);
  }
  
  ArtworkHolder(Downloadable boxArt, BufferedImage image)
  {
    this.boxArt = boxArt;
    this.image = image;
  }

  ArtworkHolder(Downloadable boxArt, BufferedImage image, BufferedImage thumb)
  {
    this(boxArt, image);
    this.thumbnail = thumb;
  }
}
