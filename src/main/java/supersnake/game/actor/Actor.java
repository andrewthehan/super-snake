
package supersnake.game.actor;

import supersnake.attribute.Renderable;
import supersnake.attribute.Updatable;
import supersnake.game.object.attribute.Killable;
import supersnake.game.object.item.AbstractItem;
import supersnake.game.object.DynamicBody;

import java.util.stream.Collectors;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

public abstract class Actor implements Updatable, Renderable, Killable{
  protected DynamicBody object;
  protected Queue<AbstractItem> items;

  public Actor(DynamicBody object){
    this.object = object;
    items = new LinkedList<>();
  }

  public void activateItem(){
    AbstractItem top = items.peek();
    if(top != null){
      if(top.isActivated()){
        if(items.size() == 1){
          return;
        }
        top.stop();
        items.remove();
        top = items.peek();
      }
      top.activate();
    }
  }

  public void addItem(AbstractItem item){
    items.add(item);
  }

  public void setObject(DynamicBody object){
    this.object = object;
  }

  public DynamicBody getObject(){
    return object;
  }

  @Override
  public boolean isDead(){
    return object.isDead();
  }

  @Override
  public void update(double timeElapsed){
    object.update(timeElapsed);
    if(!items.isEmpty()){
      AbstractItem item = items.element();
      if(item.isActivated()){
        item.update(timeElapsed);
      }
      else if(item.isDead()){
        items.remove();
      }
    }
  }

  @Override
  public void render(){
    object.render();
    if(!items.isEmpty() && items.element().isActivated()){
      items.element().render();
    }
  }
}
