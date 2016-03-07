
package supersnake.game.object;

import supersnake.attribute.Updatable;
import supersnake.game.actor.Actor;
import supersnake.game.object.attribute.Body;
import supersnake.game.object.item.AbstractItem;
import supersnake.util.Direction;
import supersnake.util.Location;

public abstract class DynamicBody implements Body, Updatable{
  protected Actor actor;
  protected Direction direction;

  public DynamicBody(){
    this(Direction.UP);
  }

  public DynamicBody(Direction direction){
    this.direction = direction;
  }

  @Override
  public void collide(Body collided, Location location){
    if(collided instanceof AbstractItem){
      actor.addItem((AbstractItem) collided);
    }
  }

  public void setDirection(Direction direction){
    this.direction = direction;
  }

  public Direction getDirection(){
    return direction;
  }

  public void setActor(Actor actor){
    this.actor = actor;
  }

  public Actor getActor(){
    return actor;
  }
}
