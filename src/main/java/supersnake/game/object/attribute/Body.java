
package supersnake.game.object.attribute;

import supersnake.attribute.Renderable;
import supersnake.util.CellBlock;
import supersnake.util.Location;

import java.util.Collection;

public interface Body extends Renderable{
  public Collection<CellBlock> getBody();
  public void collide(Body collided, Location location);
  public default void checkCollision(Body collided){
    for(CellBlock cb1 : getBody()){
      for(CellBlock cb2 : collided.getBody()){
        if(cb1.intersects(cb2) && cb1 != cb2){
          collide(collided, cb1.getLocation());
          collided.collide(this, cb1.getLocation());
          return;
        }
      }
    }
  }
}
