
package supersnake.state;

import supersnake.Constants;
import supersnake.control.Player;
import supersnake.control.EnemySnake;
import supersnake.input.Key;
import supersnake.input.KeyManager;
import supersnake.object.decoration.Skin;
import supersnake.object.Map;
import supersnake.object.Wall;
import supersnake.system.CollisionSystem;
import supersnake.system.FoodSystem;
import supersnake.util.CellBlock;

public class GameState extends AbstractState{
  private Player player;
  private Map map;

  private FoodSystem foodSystem;
  private CollisionSystem collisionSystem;

  @Override
  public void exit(){
  }

  @Override
  public void load(){
    player = new Player();

    map = Map.builder()
      .add(new EnemySnake(player.getSnake(), 15, 10, 7))
      .add(new Wall(0, Constants.GRID_WIDTH, 0, 1))
      .add(new Wall(0, Constants.GRID_WIDTH, Constants.GRID_HEIGHT - 1, Constants.GRID_HEIGHT))
      .add(new Wall(0, 1, 0, Constants.GRID_HEIGHT))
      .add(new Wall(Constants.GRID_WIDTH - 1, Constants.GRID_WIDTH, 0, Constants.GRID_HEIGHT))
      .build();

    foodSystem = new FoodSystem();
    foodSystem.setAmount(3);

    collisionSystem = new CollisionSystem();
    collisionSystem.addBody(player.getSnake());
    collisionSystem.addBodies(map.getWalls());
    map.getEnemies().forEach(e -> collisionSystem.addBody(e.getObject()));
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
    map.update(timeElapsed);
    foodSystem.update(timeElapsed);
    collisionSystem.update(timeElapsed);
    if(KeyManager.isReleased(Key.P)){
      StateManager.push(new PauseState());
    }
  }

  @Override
  public void render(){
    player.render();
    map.render();
    foodSystem.render();
    collisionSystem.render();
  }
}
