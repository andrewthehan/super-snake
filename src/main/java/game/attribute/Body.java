
package game.attribute;

import game.attribute.Renderable;
import game.util.CellBlock;

import java.util.Collection;

public interface Body extends Renderable{
  public Collection<CellBlock> getBody();
  public void collide(Body body);
  public default void checkCollision(Body body){
    for(CellBlock cb1 : getBody()){
      for(CellBlock cb2 : body.getBody()){
        if(cb1.intersects(cb2) && !cb1.equals(cb2)){
          collide(body);
          body.collide(this);
          return;
        }
      }
    }
  }
}
