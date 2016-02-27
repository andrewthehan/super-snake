
package supersnake.object;

import supersnake.attribute.Renderable;
import supersnake.attribute.Updatable;
import supersnake.control.Enemy;
import supersnake.object.attribute.Body;
import supersnake.object.Wall;

import java.util.HashSet;
import java.util.Set;

public class Map implements Renderable, Updatable{
  private Set<Wall> walls;
  private Set<Enemy> enemies;

  public Map(Set<Wall> walls, Set<Enemy> enemies){
    this.walls = walls;
    this.enemies = enemies;
  }

  public Set<Wall> getWalls(){
    return walls;
  }

  public Set<Enemy> getEnemies(){
    return enemies;
  }

  @Override
  public void update(double timeElapsed){
    enemies.forEach(e -> e.update(timeElapsed));
  }

  @Override
  public void render(){
    walls.forEach(Renderable::render);
    enemies.forEach(Renderable::render);
  }

  public static MapBuilder builder(){
    return new MapBuilder();
  }

  public static class MapBuilder{
    private Set<Wall> walls;
    private Set<Enemy> enemies;

    public MapBuilder(){
      this.walls = new HashSet<>();
      this.enemies = new HashSet<>();
    }

    public MapBuilder add(Wall wall){
      walls.add(wall);
      return this;
    }

    public MapBuilder add(Enemy enemy){
      enemies.add(enemy);
      return this;
    }

    public Map build(){
      return new Map(walls, enemies);
    }
  }
}
