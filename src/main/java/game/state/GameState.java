
package game.state;

import game.Constants;
import game.object.Snake;
import game.system.CollisionSystem;
import game.system.FoodSystem;
import game.util.CellBlock;
import game.util.Direction;

public class GameState extends AbstractState{
  private Snake player;
  private FoodSystem foodSystem;
  private CollisionSystem collisionSystem;

  public GameState(){
    player = new Snake(10, 10, 5);
    foodSystem = new FoodSystem(3);
    collisionSystem = new CollisionSystem();
  }

  public void exit(){
    player.setLength(0);
    foodSystem.clear();
  }

  public void load(){
    player.setLength(5);
    player.setLocation(Constants.GRID_WIDTH / 2, Constants.GRID_HEIGHT / 2);
    player.setDirection(Direction.UP);
    foodSystem.setAmount(3);
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
  }

  @Override
  public void render(){
    player.render();
    foodSystem.render();
    collisionSystem.render();
  }
}
