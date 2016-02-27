
package supersnake.state;

import supersnake.Constants;
import supersnake.control.Player;
import supersnake.control.Enemy;
import supersnake.control.EnemySnake;
import supersnake.input.Key;
import supersnake.input.KeyManager;
import supersnake.object.decoration.Skin;
import supersnake.object.Wall;
import supersnake.system.CollisionSystem;
import supersnake.system.FoodSystem;
import supersnake.util.CellBlock;

import java.util.HashSet;
import java.util.Set;

public class GameState extends AbstractState{
  private Player player;
  private Set<Enemy> enemies;
  private Set<Wall> walls;

  private FoodSystem foodSystem;
  private CollisionSystem collisionSystem;

  public GameState(){
    player = new Player();

    enemies = new HashSet<>();
    walls = new HashSet<>();

    foodSystem = new FoodSystem();
    collisionSystem = new CollisionSystem();
  }

  @Override
  public void exit(){
    player.clear();

    enemies.clear();
    walls.clear();

    foodSystem.clear();
    collisionSystem.clear();
  }

  @Override
  public void load(){
    player.reset();

    EnemySnake enemySnake = new EnemySnake();
    enemySnake.setTarget(player.getSnake());
    enemies.add(enemySnake);

    walls.add(new Wall(0, Constants.GRID_WIDTH, 0, 1));
    walls.add(new Wall(0, Constants.GRID_WIDTH, Constants.GRID_HEIGHT - 1, Constants.GRID_HEIGHT));
    walls.add(new Wall(0, 1, 0, Constants.GRID_HEIGHT));
    walls.add(new Wall(Constants.GRID_WIDTH - 1, Constants.GRID_WIDTH, 0, Constants.GRID_HEIGHT));

    foodSystem.setAmount(3);
    collisionSystem.addBody(player.getSnake());
    enemies.forEach(e -> collisionSystem.addBody(e.getBody()));
    walls.forEach(collisionSystem::addBody);
    collisionSystem.addBodies(foodSystem.getFoods());
  }

  @Override
  public void pause(){

  }


  @Override
  public void resume(){

  }

  @Override
  public void update(double timeElapsed){
    player.update(timeElapsed);
    enemies.forEach(e -> e.update(timeElapsed));
    foodSystem.update(timeElapsed);
    collisionSystem.update(timeElapsed);
    if(KeyManager.isReleased(Key.P)){
      StateManager.push(new PauseState());
    }
  }

  @Override
  public void render(){
    player.render();
    enemies.forEach(Enemy::render);
    walls.forEach(Wall::render);
    foodSystem.render();
    collisionSystem.render();
  }
}
