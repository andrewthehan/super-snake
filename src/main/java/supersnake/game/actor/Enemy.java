
package supersnake.game.actor;

import supersnake.game.object.attribute.Body;

public abstract class Enemy extends Actor{
  protected Body target;

  public Enemy(Body object, Body target){
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
