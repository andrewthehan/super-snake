
package supersnake.util;

import supersnake.object.exception.IndexOutOfSyncException;

import java.awt.Color;

public class IterativeColor extends Color{
  private final Color[] colors;
  private int i;
  private boolean r, g, b;

  public IterativeColor(Color... colors){
    super(0);
    this.colors = colors;
    i = 0;
    r = g = b = false;
  }

  public void reset(){
    i = 0;
  }

  private void update(){
    if(r && g && b){
      r = g = b = false;
      i = (i + 1) % colors.length;
    }
  }

  @Override
  public int getRed(){
    if(r){
      new IndexOutOfSyncException();
    }
    int red = colors[i].getRed();
    r = true;
    update();
    return red;
  }

  @Override
  public int getGreen(){
    if(g){
      new IndexOutOfSyncException();
    }
    int green = colors[i].getGreen();
    g = true;
    update();
    return green;
  }

  @Override
  public int getBlue(){
    if(b){
      new IndexOutOfSyncException();
    }
    int blue = colors[i].getBlue();
    b = true;
    update();
    return blue;
  }
}
