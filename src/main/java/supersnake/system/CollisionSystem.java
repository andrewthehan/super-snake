
package supersnake.system;

import supersnake.attribute.Renderable;
import supersnake.attribute.Updatable;
import supersnake.object.attribute.Body;
import supersnake.object.attribute.DynamicBody;
import supersnake.object.attribute.StaticBody;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class CollisionSystem implements Updatable, Renderable{
  private Set<StaticBody> staticBodies;
  private Set<DynamicBody> dynamicBodies;

  public CollisionSystem(){
    staticBodies = new HashSet<>();
    dynamicBodies = new HashSet<>();
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

  public void clear(){
    staticBodies.clear();
    dynamicBodies.clear();
  }

  @Override
  public void update(long timeElapsed){
    dynamicBodies.forEach(b -> {
      staticBodies.forEach(b::checkCollision);
      dynamicBodies.forEach(b::checkCollision);
    });
  }

  @Override
  public void render(){
    // draw effects for eating food / getting hurt
  }
}
