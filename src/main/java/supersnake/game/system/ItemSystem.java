
package supersnake.game.system;

import supersnake.attribute.Renderable;
import supersnake.attribute.Updatable;
import supersnake.Constants;
import supersnake.game.map.Bounds;
import supersnake.game.map.Map;
import supersnake.game.object.item.AbstractItem;
import supersnake.game.object.item.FreezeItem;
import supersnake.game.object.item.MagnetItem;
import supersnake.util.CellBlock;
import supersnake.util.Location;
import supersnake.util.RNG;
import supersnake.util.Time;
import supersnake.util.UpdateController;

import static org.lwjgl.opengl.GL11.*;

import java.util.stream.Collectors;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

public class ItemSystem implements Updatable, Renderable{
  private UpdateController uController;
  private int amount;
  private Queue<AbstractItem> items;

  private Bounds bounds;

  private Map map;

  public ItemSystem(){
    uController = new UpdateController(Time.SECOND * 5);
    amount = 0;
    items = new LinkedList<>();
  }

  public void setMap(Map map){
    this.map = map;
  }

  public void setBounds(Bounds bounds){
    this.bounds = bounds;
  }

  public Collection<AbstractItem> getItems(){
    return items;
  }

  public void setAmount(int amount){
    int difference = amount - this.amount;
    if(difference < 0){
      for(int i = 0; i > difference; --i){
        items.remove(items.iterator().next());
      }
    }
    this.amount = amount;
  }

  private AbstractItem newItem(){
    Location spawn = RNG.location(bounds);
    switch(RNG.integer(2)){
      case 0: return new FreezeItem(new Location(spawn.getX(), spawn.getY()));
      case 1: return new MagnetItem(new Location(spawn.getX(), spawn.getY()));
      default: return null;
    }
  }

  private void addItem(){
    if(items.size() == amount){
      removeItem();
    }
    items.add(newItem());
  }

  private void removeItem(){
    if(!items.isEmpty()){
      items.remove();
    }
  }

  @Override
  public void update(double timeElapsed){
    if(uController.shouldUpdate(timeElapsed)){
      addItem();
    }
    items.forEach(i -> i.update(timeElapsed));
    items.stream().filter(AbstractItem::isObtained).forEach(i -> i.apply(map));
    Set<AbstractItem> toRemove = items.stream().filter(AbstractItem::isObtained).collect(Collectors.toSet());
    toRemove.forEach(items::remove);
  }

  @Override
  public void render(){
    items.forEach(AbstractItem::render);
  }
}
