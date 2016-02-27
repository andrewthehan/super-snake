
package supersnake.control;

import supersnake.attribute.Renderable;
import supersnake.attribute.Updatable;
import supersnake.object.attribute.Body;

public abstract class Enemy implements Updatable, Renderable{
  protected Body object;
  protected Body target;

  public Enemy(Body object, Body target){
    this.object = object;
    this.target = target;
  }

  public void setObject(Body object){
    this.object = object;
  }

  public Body getObject(){
    return object;
  }

  public void setTarget(Body target){
    this.target = target;
  }

  public Body getTarget(){
    return target;
  }
}
