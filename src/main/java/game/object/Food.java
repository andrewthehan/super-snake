
package game.object;

import game.graphic.CellBlockRenderer;
import game.object.attribute.Body;
import game.object.attribute.Skinnable;
import game.object.attribute.StaticBody;
import game.object.decoration.Skin;
import game.util.CellBlock;
import game.util.RNG;

import java.awt.Color;
import java.awt.Point;
import java.util.Collection;
import java.util.HashSet;

public class Food implements StaticBody, Skinnable{
  private CellBlock body;
  private boolean isConsumed;
  private Skin skin;

  public Food(int x, int y){
    body = new CellBlock(x, y);
    isConsumed = false;
    skin = Skin.FOOD_DEFAULT;
  }

  public Food(Point location){
    this((int) location.getX(), (int) location.getY());
  }

  public int getX(){
    return body.getX();
  }

  public int getY(){
    return body.getY();
  }

  public Point getLocation(){
    return body.getLocation();
  }

  public boolean isConsumed(){
    return isConsumed;
  }

  public void consume(){
    isConsumed = true;
  }

  public void reset(int x, int y){
    body.setLocation(x, y);
    isConsumed = false;
  }

  public void reset(Point location){
    body.setLocation(location);
    isConsumed = false;
  }

  @Override
  public Collection<CellBlock> getBody(){
    Collection<CellBlock> toReturn = new HashSet<>();
    toReturn.add(body);
    return toReturn;
  }

  @Override
  public void collide(Body b){
    if(b instanceof Snake){
      reset(RNG.location());
    }
  }

  @Override
  public void render(){
    CellBlockRenderer.render(body, skin.getColor());
  }

  @Override
  public void setSkin(Skin skin){
    this.skin = skin;
  }
}
