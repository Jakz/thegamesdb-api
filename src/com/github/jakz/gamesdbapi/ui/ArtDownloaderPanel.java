package com.github.jakz.gamesdbapi.ui;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import org.imgscalr.Scalr;
import org.imgscalr.Scalr.Method;

import com.github.jakz.gamesdbapi.requests.RequestFactory;
import com.github.jakz.gamesdbapi.types.BoxArt;
import com.github.jakz.gamesdbapi.types.Images;

public class ArtDownloaderPanel extends JPanel
{
  JPanel imagesPanel;
  
  public ArtDownloaderPanel()
  {
    imagesPanel = new JPanel();
    this.add(imagesPanel);
  }
  
  public void onResponse(Images images)
  {
    ExecutorService executor = Executors.newFixedThreadPool(4);
    
    List<BoxArt> boxarts = images.boxarts();
    
    imagesPanel.removeAll();
    if (boxarts != null)
    {
      //boxarts.forEach(art -> imagesPanel.add(new JLabel()));
      
      //TODO: obtain from XML result
      String base = "http://thegamesdb.net/banners/";
      
      Function<BoxArt, ArtworkHolder> downloadTask = boxArt -> {
        try
        {
          URL url = new URL(base + boxArt.url());
          return new ArtworkHolder(boxArt, ImageIO.read(url));
        }
        catch (IOException e)
        {
          e.printStackTrace();
          return new ArtworkHolder();
        }
      };
      
      Function<ArtworkHolder, ArtworkHolder> resizeTask = h -> {
        if (h.image != null)
          h.thumbnail = Scalr.resize(h.image, Method.QUALITY, 200);      
        return h;
      };
      
      Consumer<ArtworkHolder> addToPanelTask = holder -> {
        if (holder.image != null)
        {
          System.out.printf("Adding %dx%d image\n", holder.image.getWidth(), holder.image.getHeight());
          
          JButton button = new JButton(new ImageIcon(holder.thumbnail));
          button.setHorizontalTextPosition(SwingConstants.CENTER);
          button.setVerticalTextPosition(SwingConstants.BOTTOM);
          button.setText(holder.boxArt+" "+holder.image.getWidth()+"x"+holder.image.getHeight());
          
          imagesPanel.add(button);
          SwingUtilities.invokeLater(() -> imagesPanel.revalidate());
        }             
      };
      
      List<CompletableFuture<Void>> futures = boxarts.stream()
        .map(boxArt -> CompletableFuture.supplyAsync(() -> downloadTask.apply(boxArt), executor))
        .map(future -> future.thenApply(resizeTask))
        .map(future -> future.thenAccept(addToPanelTask))
        .collect(Collectors.toList());
    }
  }
  
}
