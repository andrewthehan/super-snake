
package supersnake.game.actor;

import supersnake.attribute.Renderable;
import supersnake.attribute.Updatable;
import supersnake.game.object.attribute.Body;
import supersnake.game.object.attribute.Killable;

public abstract class Actor implements Updatable, Renderable, Killable{
  protected Body object;

  public Actor(Body object){
    this.object = object;
  }

  public void setObject(Body object){
    this.object = object;
  }

  public Body getObject(){
    return object;
  }

  @Override
  public boolean isKilled(){
    return object.isKilled();
  }
}
