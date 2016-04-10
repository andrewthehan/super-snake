
package supersnake.game.map;

import supersnake.attribute.Renderable;
import supersnake.attribute.Updatable;
import supersnake.game.actor.Actor;
import supersnake.game.actor.Player;
import supersnake.game.exception.AlreadyInitializedException;
import supersnake.game.object.attribute.Body;
import supersnake.game.object.attribute.Killable;
import supersnake.game.object.Snake;
import supersnake.game.object.Wall;
import supersnake.game.system.CameraSystem;
import supersnake.game.system.EnemySystem;
import supersnake.game.system.FoodSystem;
import supersnake.game.system.ItemSystem;
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
  private ItemSystem itemSystem;
	private EnemySystem enemySystem;
  private Set<Wall> walls;
  private Set<Player> players;

  public Map(Bounds bounds, Location spawnLocation, FoodSystem foodSystem, ItemSystem itemSystem, EnemySystem enemySystem, Set<Wall> walls, Set<Player> players){
    this.bounds = bounds;
    this.spawnLocation = spawnLocation;
    this.foodSystem = foodSystem;
    this.itemSystem = itemSystem;
		this.enemySystem = enemySystem;
    this.walls = walls;
    this.players = players;

    itemSystem.setMap(this);
		enemySystem.setMap(this);

		players.stream().map(Actor::getObject).forEach(CameraSystem::addTarget);
  }

  public void load(Player player){
    ((Snake) player.getObject()).setLocation(spawnLocation);
  }

  public Collection<Body> getBodies(){
    Set<Body> bodies = new HashSet<>();
    bodies.addAll(foodSystem.getFoods());
    bodies.addAll(itemSystem.getItems());
		bodies.addAll(enemySystem.getEnemies().stream().map(Actor::getObject).collect(Collectors.toSet()));
    bodies.addAll(players.stream().map(Actor::getObject).collect(Collectors.toSet()));
    bodies.addAll(walls);
    return bodies;
  }

  public void add(Wall wall){
    walls.add(wall);
  }

  public void add(Player player){
    players.add(player);

		CameraSystem.addTarget(player.getObject());
  }

  public void remove(Wall wall){
    if(walls.contains(wall)){
      walls.remove(wall);
    }
  }

  public void remove(Player player){
    if(players.contains(player)){
      players.remove(player);
    }
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

  public ItemSystem getItemSystem(){
    return itemSystem;
  }

	public EnemySystem getEnemySystem(){
		return enemySystem;
	}

  public Set<Wall> getWalls(){
    return walls;
  }

  public Set<Player> getPlayers(){
    return players;
  }

  @Override
  public void update(double timeElapsed){
    // TODO: testing purposes; remove later
    if(supersnake.input.KeyManager.isHeld(supersnake.input.Key.LEFT_CTRL)) return;

    foodSystem.update(timeElapsed);
    itemSystem.update(timeElapsed);
		enemySystem.update(timeElapsed);

    Set<Actor> toRemove = players.stream().filter(Killable::isDead).collect(Collectors.toSet());
    if(!toRemove.isEmpty()){
      toRemove.forEach(players::remove);
      CameraSystem.removeTargets(toRemove.stream().map(Actor::getObject).collect(Collectors.toSet()));
    }

    Set<Actor> updated = new HashSet<>();
    Actor cur = players.stream().filter(a -> !updated.contains(a)).findFirst().orElse(null);
    while(cur != null){
      cur.update(timeElapsed);
      updated.add(cur);
      cur = players.stream().filter(p -> !updated.contains(p)).findFirst().orElse(null);
    }
  }

  @Override
  public void render(){
    foodSystem.render();
    itemSystem.render();
		enemySystem.render();
    walls.forEach(Renderable::render);
    players.forEach(Renderable::render);
  }

  public static MapBuilder builder(){
    return new MapBuilder();
  }

  public static class MapBuilder{
    private Bounds bounds;
    private Location spawnLocation;
    private FoodSystem foodSystem;
    private ItemSystem itemSystem;
		private EnemySystem enemySystem;
    private Set<Wall> walls;
    private Set<Player> players;

    public MapBuilder(){
      foodSystem = new FoodSystem();
      itemSystem = new ItemSystem();
      enemySystem = new EnemySystem();
      this.walls = new HashSet<>();
      this.players = new HashSet<>();
    }

    public MapBuilder setBounds(int x0, int x1, int y0, int y1){
      if(bounds != null){
        new AlreadyInitializedException();
      }
      bounds = new Bounds(x0, x1, y0, y1);
      foodSystem.setBounds(bounds);
      itemSystem.setBounds(bounds);
      enemySystem.setBounds(bounds);
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

    public MapBuilder setItemAmount(int amount){
      itemSystem.setAmount(amount);
      return this;
    }

    public MapBuilder setEnemyAmount(int amount){
      enemySystem.setAmount(amount);
      return this;
    }

    public MapBuilder add(Wall wall){
      walls.add(wall);
      return this;
    }

    public MapBuilder add(Player player){
      players.add(player);
      return this;
    }

    public Map build(){
      return new Map(bounds, spawnLocation, foodSystem, itemSystem, enemySystem, walls, players);
    }
  }
}
