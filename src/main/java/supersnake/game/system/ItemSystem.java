
package supersnake.game.system;

import supersnake.attribute.Renderable;
import supersnake.attribute.Updatable;
import supersnake.Constants;
import supersnake.game.map.Bounds;
import supersnake.game.map.Map;
import supersnake.game.object.item.AbstractItem;
import supersnake.game.object.item.FreezeItem;
import supersnake.util.CellBlock;
import supersnake.util.Location;
import supersnake.util.RNG;

import static org.lwjgl.opengl.GL11.*;

import java.util.stream.Collectors;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class ItemSystem implements Updatable, Renderable{
  private int amount;
  private Set<AbstractItem> items;

  private Bounds bounds;

  private Map map;

  public ItemSystem(){
    amount = 0;
    items = new HashSet<>();
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
    if(difference > 0){
      for(int i = 0; i < difference; ++i){
        Location spawn = RNG.location(bounds);
        items.add(newItem());
      }
    }
    else if(difference < 0){
      for(int i = 0; i > difference; --i){
        items.remove(items.iterator().next());
      }
    }
    this.amount = amount;
  }

  private AbstractItem newItem(){
    Location spawn = RNG.location(bounds);
    return new FreezeItem(new Location(spawn.getX() * Constants.CELL_BLOCK_SIZE, spawn.getY() * Constants.CELL_BLOCK_SIZE));
  }

  @Override
  public void update(double timeElapsed){
    items.stream().filter(AbstractItem::isObtained).forEach(i -> i.apply(map));
    items.forEach(i -> i.update(timeElapsed));
    Set<AbstractItem> toRemove = items.stream().filter(AbstractItem::isDone).collect(Collectors.toSet());
    toRemove.forEach(i -> {
      items.remove(i);
      items.add(newItem());
    });

  }

  @Override
  public void render(){
    items.forEach(AbstractItem::render);
  }
}
