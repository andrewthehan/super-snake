
package supersnake.game.actor;

import supersnake.attribute.Renderable;
import supersnake.attribute.Updatable;
import supersnake.game.object.attribute.Killable;
import supersnake.game.object.item.AbstractItem;
import supersnake.game.object.DynamicBody;

import java.util.stream.Collectors;
import java.util.HashSet;
import java.util.Set;

public abstract class Actor implements Updatable, Renderable, Killable{
  protected DynamicBody object;
  protected Set<AbstractItem> items;

  public Actor(DynamicBody object){
    this.object = object;
    items = new HashSet<>();
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
  public boolean isKilled(){
    return object.isKilled();
  }

  @Override
  public void update(double timeElapsed){
    object.update(timeElapsed);
    items.forEach(i -> i.update(timeElapsed));
    Set<AbstractItem> toRemove = items.stream().filter(AbstractItem::isKilled).collect(Collectors.toSet());
    toRemove.forEach(items::remove);
  }

  @Override
  public void render(){
    object.render();
    items.forEach(AbstractItem::render);
  }
}
