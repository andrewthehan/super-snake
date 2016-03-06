
package supersnake.game.object.item;

import supersnake.game.actor.Actor;
import supersnake.game.map.Map;
import supersnake.util.Location;
import supersnake.util.Time;
import supersnake.Assets;

import java.util.stream.Collectors;
import java.util.HashSet;
import java.util.Set;

public class FreezeItem extends AbstractItem{
  private Set<Actor> toFreeze;
  private Map map;

  public FreezeItem(Location location){
    super(location, Assets.ITEM_FREEZE, 5 * Time.SECOND);
    toFreeze = new HashSet<>();
  }

  @Override
  public void stop(){
    toFreeze.forEach(map::add);
    toFreeze.clear();
  }

  @Override
  public void apply(Map map){
    this.map = map;
    toFreeze.addAll(map.getActors().stream().filter(a -> a.getObject() != obtainedBy).collect(Collectors.toSet()));
    toFreeze.forEach(map::remove);
  }

  @Override
  public void render(){
    super.render();
    if(!toFreeze.isEmpty()){
      toFreeze.forEach(a -> a.render());
    }
  }
}
