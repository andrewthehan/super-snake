
package supersnake.object;

import supersnake.attribute.Renderable;
import supersnake.attribute.Updatable;
import supersnake.control.Enemy;
import supersnake.object.attribute.Body;
import supersnake.object.exception.AlreadyInitializedException;
import supersnake.object.Wall;
import supersnake.util.CellBlock;

import java.util.HashSet;
import java.util.Set;

public class Map implements Renderable, Updatable{
  private Set<Wall> walls;
  private Set<Enemy> enemies;
  private Bounds bounds;

  public Map(Bounds bounds, Set<Wall> walls, Set<Enemy> enemies){
    this.bounds = bounds;
    this.walls = walls;
    this.enemies = enemies;
  }

  public Bounds getBounds(){
    return bounds;
  }

  public boolean intersects(CellBlock cell){
    return walls.stream().anyMatch(w -> w.getBody().stream().anyMatch(c -> c.intersects(cell))) ||
      enemies.stream().anyMatch(e -> e.getObject().getBody().stream().anyMatch(c -> c.intersects(cell)));
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

  public static class Bounds{
    private int left, right, bottom, top;

    public Bounds(int left, int right, int bottom, int top){
      this.left = left;
      this.right = right;
      this.bottom = bottom;
      this.top = top;
    }

    public int getLeft(){
      return left;
    }

    public int getRight(){
      return right;
    }

    public int getBottom(){
      return bottom;
    }

    public int getTop(){
      return top;
    }

    public boolean contains(CellBlock cell){
      int x = cell.getX();
      int y = cell.getY();
      return left <= x && x <= right && bottom <= y && y <= top;
    }
  }

  public static MapBuilder builder(){
    return new MapBuilder();
  }

  public static class MapBuilder{
    private Set<Wall> walls;
    private Set<Enemy> enemies;
    private Bounds bounds;

    public MapBuilder(){
      this.walls = new HashSet<>();
      this.enemies = new HashSet<>();
    }

    public MapBuilder bounds(int x0, int x1, int y0, int y1){
      if(bounds != null){
        new AlreadyInitializedException();
      }
      bounds = new Bounds(x0, x1, y0, y1);
      return add(new Wall(x0, x1, y0, y0 + 1))
        .add(new Wall(x0, x1, y1 - 1, y1))
        .add(new Wall(x0, x0 + 1, y0, y1))
        .add(new Wall(x1 - 1, x1, y0, y1));
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
      return new Map(bounds, walls, enemies);
    }
  }
}
