
package supersnake.game.system;

import supersnake.attribute.Renderable;
import supersnake.attribute.Updatable;
import supersnake.Constants;
import supersnake.game.actor.Actor;
import supersnake.game.actor.Enemy;
import supersnake.game.actor.EnemySnake;
import supersnake.game.map.Bounds;
import supersnake.game.map.Map;
import supersnake.game.object.attribute.Killable;
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

public class EnemySystem implements Updatable, Renderable{
  private UpdateController uController;
  private int amount;
  private Queue<Enemy> enemies;

  private Bounds bounds;

  private Map map;

  public EnemySystem(){
    uController = new UpdateController(Time.SECOND * 5);
    amount = 0;
    enemies = new LinkedList<>();
  }

  public void setMap(Map map){
    this.map = map;
  }

  public void setBounds(Bounds bounds){
    this.bounds = bounds;
  }

  public Collection<Enemy> getEnemies(){
    return enemies;
  }

  public void setAmount(int amount){
    int difference = amount - this.amount;
    if(difference < 0){
      for(int i = 0; i > difference; --i){
        enemies.remove(enemies.iterator().next());
      }
    }
    this.amount = amount;
  }

  private Enemy newEnemy(){
    Location spawn = RNG.location(bounds);
    switch(RNG.integer(1)){
      case 0: return new EnemySnake(map.getPlayers().iterator().next().getObject(), new Location(spawn.getX(), spawn.getY()), 5);
      default: return null;
    }
  }

  private void addEnemy(){
    if(enemies.size() != amount){
			Enemy enemy = newEnemy();
    	enemies.add(enemy);
	    CameraSystem.addTarget(enemy.getObject());
    }
  }

  @Override
  public void update(double timeElapsed){
    if(uController.shouldUpdate(timeElapsed)){
      addEnemy();
    }
		enemies.forEach(e -> e.update(timeElapsed));
    Set<Enemy> toRemove = enemies.stream().filter(Killable::isDead).collect(Collectors.toSet());
    if(!toRemove.isEmpty()){
      toRemove.forEach(enemies::remove);
      CameraSystem.removeTargets(toRemove.stream().map(Actor::getObject).collect(Collectors.toSet()));
    }
  }

  @Override
  public void render(){
    enemies.forEach(Renderable::render);
  }
}
