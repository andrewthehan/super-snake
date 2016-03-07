
package supersnake.game.actor;

import supersnake.game.object.attribute.Body;
import supersnake.game.object.DynamicBody;

public abstract class Enemy extends Actor{
  protected Body target;

  public Enemy(DynamicBody object, Body target){
    super(object);
    this.target = target;
  }

  public void setTarget(Body target){
    this.target = target;
  }

  public Body getTarget(){
    return target;
  }
}
