
package game.state;

import game.Constants;
import game.input.Key;
import game.input.KeyManager;
import game.object.decoration.Skin;
import game.object.Wall;
import game.Player;
import game.system.CollisionSystem;
import game.system.FoodSystem;
import game.util.CellBlock;

import java.util.HashSet;
import java.util.Set;

public class GameState extends AbstractState{
  private Player player;
  private Set<Wall> walls;

  private FoodSystem foodSystem;
  private CollisionSystem collisionSystem;

  public GameState(){
    player = new Player();
    walls = new HashSet<>();

    foodSystem = new FoodSystem();
    collisionSystem = new CollisionSystem();
  }

  public void exit(){
    player.clear();
    walls.clear();

    foodSystem.clear();
    collisionSystem.clear();
  }

  public void load(){
    player.reset();
    walls.add(new Wall(0, Constants.GRID_WIDTH, 0, 1));
    walls.add(new Wall(0, Constants.GRID_WIDTH, Constants.GRID_HEIGHT - 1, Constants.GRID_HEIGHT));
    walls.add(new Wall(0, 1, 0, Constants.GRID_HEIGHT));
    walls.add(new Wall(Constants.GRID_WIDTH - 1, Constants.GRID_WIDTH, 0, Constants.GRID_HEIGHT));

    foodSystem.setAmount(3);
    collisionSystem.addBody(player.getSnake());
    walls.forEach(collisionSystem::addBody);
    collisionSystem.addBodies(foodSystem.getFoods());
  }

  public void pause(){

  }

  public void resume(){

  }

  @Override
  public void update(long timeElapsed){
    player.update(timeElapsed);
    foodSystem.update(timeElapsed);
    collisionSystem.update(timeElapsed);
    if(KeyManager.isReleased(Key.P)){
      StateManager.push(new PauseState());
    }
  }

  @Override
  public void render(){
    player.render();
    walls.forEach(Wall::render);
    foodSystem.render();
    collisionSystem.render();
  }
}
