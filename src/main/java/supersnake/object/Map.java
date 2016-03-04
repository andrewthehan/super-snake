
package supersnake.object;

import supersnake.attribute.Renderable;
import supersnake.attribute.Updatable;
import supersnake.actor.Actor;
import supersnake.actor.Player;
import supersnake.object.attribute.Body;
import supersnake.object.exception.AlreadyInitializedException;
import supersnake.object.Wall;
import supersnake.system.FoodSystem;
import supersnake.util.CellBlock;
import supersnake.util.Location;

import java.util.stream.Collectors;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class Map implements Renderable, Updatable{
  private Bounds bounds;
  private Location spawnLocation;

  private FoodSystem foodSystem;
  private Set<Wall> walls;
  private Set<Actor> actors;

  public Map(Bounds bounds, Location spawnLocation, FoodSystem foodSystem, Set<Wall> walls, Set<Actor> actors){
    this.bounds = bounds;
    this.spawnLocation = spawnLocation;
    this.foodSystem = foodSystem;
    this.walls = walls;
    this.actors = actors;
  }

  public void load(Player player){
    ((Snake) player.getObject()).setLocation(spawnLocation);
  }

  public Collection<Body> getBodies(){
    Set<Body> bodies = new HashSet<>();
    bodies.addAll(foodSystem.getFoods());
    bodies.addAll(actors.stream().map(Actor::getObject).collect(Collectors.toSet()));
    bodies.addAll(walls);
    return bodies;
  }

  public boolean intersects(CellBlock cell){
    return getBodies().stream().flatMap(b -> b.getBody().stream()).anyMatch(cb -> cb.intersects(cell));
  }

  public Bounds getBounds(){
    return bounds;
  }

  public Location getSpawnLocation(){
    return spawnLocation;
  }

  public FoodSystem getFoodSystem(){
    return foodSystem;
  }

  public Set<Wall> getWalls(){
    return walls;
  }

  public Set<Actor> getActors(){
    return actors;
  }

  @Override
  public void update(double timeElapsed){
    // TODO: testing purposes; remove later
    if(supersnake.input.KeyManager.isHeld(supersnake.input.Key.LEFT_CTRL)) return;

    foodSystem.update(timeElapsed);
    actors.forEach(e -> e.update(timeElapsed));
  }

  @Override
  public void render(){
    foodSystem.render();
    walls.forEach(Renderable::render);
    actors.forEach(Renderable::render);
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
    private Bounds bounds;
    private Location spawnLocation;
    private FoodSystem foodSystem;
    private Set<Wall> walls;
    private Set<Actor> actors;

    public MapBuilder(){
      foodSystem = new FoodSystem();
      this.walls = new HashSet<>();
      this.actors = new HashSet<>();
    }

    public MapBuilder setBounds(int x0, int x1, int y0, int y1){
      if(bounds != null){
        new AlreadyInitializedException();
      }
      bounds = new Bounds(x0, x1, y0, y1);
      foodSystem.setBounds(bounds);
      return add(new Wall(x0, x1, y0, y0 + 1))
        .add(new Wall(x0, x1, y1 - 1, y1))
        .add(new Wall(x0, x0 + 1, y0, y1))
        .add(new Wall(x1 - 1, x1, y0, y1));
    }

    public MapBuilder setSpawnLocation(int x, int y){
      spawnLocation = new Location(x, y);
      return this;
    }

    public MapBuilder setFoodAmount(int amount){
      foodSystem.setAmount(amount);
      return this;
    }

    public MapBuilder add(Wall wall){
      walls.add(wall);
      return this;
    }

    public MapBuilder add(Actor enemy){
      actors.add(enemy);
      return this;
    }

    public Map build(){
      return new Map(bounds, spawnLocation, foodSystem, walls, actors);
    }
  }
}
