
package game.state;

import game.input.Key;
import game.input.KeyManager;
import game.Player;
import game.system.CollisionSystem;
import game.system.FoodSystem;
import game.util.CellBlock;

public class GameState extends AbstractState{
  private Player player;
  private FoodSystem foodSystem;
  private CollisionSystem collisionSystem;

  public GameState(){
    player = new Player();
    foodSystem = new FoodSystem(3);
    collisionSystem = new CollisionSystem();
  }

  public void exit(){
    player.clear();
    foodSystem.clear();
    collisionSystem.clear();
  }

  public void load(){
    player.reset();
    foodSystem.setAmount(3);

    collisionSystem.addBody(player.getSnake());
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
    foodSystem.render();
    collisionSystem.render();
  }
}
