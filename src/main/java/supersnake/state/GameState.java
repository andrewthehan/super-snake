
package supersnake.state;

import supersnake.Constants;
import supersnake.game.actor.Player;
import supersnake.game.actor.EnemySnake;
import supersnake.input.Key;
import supersnake.input.KeyManager;
import supersnake.game.map.Map;
import supersnake.game.object.decoration.Skin;
import supersnake.game.object.Wall;
import supersnake.game.system.CameraSystem;
import supersnake.game.system.CollisionSystem;
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
      .setItemAmount(5)
      .add(player)
      .add(new EnemySnake(player.getObject(), 15, 10, 20))
      .add(new Wall(20, 23, 0, 10))
      .build();

    collisionSystem = new CollisionSystem();
    collisionSystem.setMap(map);

    map.load(player);

    CameraSystem.addTargets(map.getActors().stream().map(supersnake.game.actor.Actor::getObject).collect(java.util.stream.Collectors.toSet()));
  }

  @Override
  public void pause(){
  }


  @Override
  public void resume(){
  }

  @Override
  public void update(double timeElapsed){
    if(KeyManager.isReleased(Key.P)){
      StateManager.push(new PauseState());
      return;
    }

    collisionSystem.update(timeElapsed);
    map.update(timeElapsed);

    CameraSystem.update(timeElapsed);
  }

  @Override
  public void render(){
    collisionSystem.render();
    map.render();

    CameraSystem.render();
  }
}
