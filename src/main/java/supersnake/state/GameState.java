
package supersnake.state;

import supersnake.Constants;
import supersnake.control.Player;
import supersnake.control.EnemySnake;
import supersnake.input.Key;
import supersnake.input.KeyManager;
import supersnake.object.decoration.Skin;
import supersnake.object.Map;
import supersnake.object.Wall;
import supersnake.system.CameraSystem;
import supersnake.system.CollisionSystem;
import supersnake.util.CellBlock;
import supersnake.util.Direction;

public class GameState extends AbstractState{
  private Map map;
  private CollisionSystem collisionSystem;

  @Override
  public void exit(){
  }

  @Override
  public void load(){
    Player player = new Player(0, 0, 5, Direction.UP);

    map = Map.builder()
      .setBounds(0, 40, 0, 40)
      .setSpawnLocation(25, 25)
      .setFoodAmount(3)
      .add(new EnemySnake(player.getSnake(), 15, 10, 20))
      .add(new Wall(20, 23, 0, 10))
      .build();

    collisionSystem = new CollisionSystem();
    collisionSystem.setMap(map);

    map.load(player);

    CameraSystem.setTarget(player.getSnake());
  }

  @Override
  public void pause(){
  }


  @Override
  public void resume(){
  }

  @Override
  public void update(double timeElapsed){
    map.update(timeElapsed);
    collisionSystem.update(timeElapsed);

    CameraSystem.update(timeElapsed);

    if(KeyManager.isReleased(Key.P)){
      StateManager.push(new PauseState());
    }
  }

  @Override
  public void render(){
    map.render();
    collisionSystem.render();
  }
}
