
package supersnake.actor;

import supersnake.attribute.Renderable;
import supersnake.attribute.Updatable;
import supersnake.object.attribute.Body;

public abstract class Actor implements Updatable, Renderable{
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
}
