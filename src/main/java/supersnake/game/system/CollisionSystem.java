
package supersnake.game.system;

import supersnake.attribute.Renderable;
import supersnake.attribute.Updatable;
import supersnake.game.map.Map;
import supersnake.game.object.attribute.Body;
import supersnake.game.object.attribute.DynamicBody;
import supersnake.game.object.attribute.StaticBody;

import java.util.stream.Collectors;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CollisionSystem implements Updatable, Renderable{
  private Set<StaticBody> staticBodies;
  private Set<DynamicBody> dynamicBodies;

  private Map map;

  public CollisionSystem(){
    staticBodies = new HashSet<>();
    dynamicBodies = new HashSet<>();
  }

  public void setMap(Map map){
    this.map = map;
  }

  public void refreshSets(){
    Collection<Body> newBodies = map.getBodies();

    // add new bodies
    newBodies.stream().filter(b -> !staticBodies.contains(b) && !dynamicBodies.contains(b)).forEach(this::addBody);

    // remove bodies that don't exist anymore
    staticBodies = staticBodies.stream().filter(b -> newBodies.contains(b)).collect(Collectors.toSet());
    dynamicBodies = dynamicBodies.stream().filter(b -> newBodies.contains(b)).collect(Collectors.toSet());
  }

  public void addBody(Body body){
    if(body instanceof StaticBody){
      staticBodies.add((StaticBody) body);
    }
    else if(body instanceof DynamicBody){
      dynamicBodies.add((DynamicBody) body);
    }
  }

  public <T extends Body> void addBodies(Collection<T> bodies){
    bodies.forEach(this::addBody);
  }

  @Override
  public void update(double timeElapsed){
    refreshSets();
    List<DynamicBody> orderedDynamicBodies = dynamicBodies.stream().collect(Collectors.toList());
    for(int i = 0; i < orderedDynamicBodies.size(); ++i){
      Body b = orderedDynamicBodies.get(i);
      staticBodies.forEach(b::checkCollision);
      for(int j = i; j < orderedDynamicBodies.size(); ++j){
        Body b2 = orderedDynamicBodies.get(j);
        b.checkCollision(b2);
      }
    }
  }

  @Override
  public void render(){
    // draw effects for eating food / getting hurt
  }
}
