
package supersnake.game.object;

import supersnake.graphic.CellBlockRenderer;
import supersnake.game.object.attribute.Body;
import supersnake.game.object.attribute.Skinnable;
import supersnake.game.object.StaticBody;
import supersnake.game.object.decoration.Skin;
import supersnake.util.CellBlock;
import supersnake.util.Location;

import java.awt.Color;
import java.util.Collection;
import java.util.HashSet;

public class Food extends StaticBody implements Skinnable{
  private CellBlock body;
  private boolean isConsumed;
  private Skin skin;

  public Food(int x, int y){
    body = new CellBlock(x, y);
    isConsumed = false;
    skin = Skin.DEFAULT_FOOD;
  }

  public Food(Location location){
    this(location.getX(), location.getY());
  }

  public int getX(){
    return body.getX();
  }

  public int getY(){
    return body.getY();
  }

  public Location getLocation(){
    return body.getLocation();
  }

  public boolean isConsumed(){
    return isConsumed;
  }

  public void consume(){
    isConsumed = true;
  }

  public void move(int x, int y){
    body.setLocation(x, y);
    isConsumed = false;
  }

  public void move(Location location){
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
  public void collide(Body collided, Location location){
    if(collided instanceof Snake){
      isConsumed = true;
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
