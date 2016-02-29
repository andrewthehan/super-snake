
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
import supersnake.util.CellBlock;
import supersnake.util.Direction;

import static org.lwjgl.opengl.GL11.*;

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

    if(map.getPlayer() != null){
      CellBlock head = map.getPlayer().getSnake().getHead();
      int x0 = head.getX() * Constants.CELL_BLOCK_SIZE - Constants.SCREEN_WIDTH / 2;
      int x1 = x0 + Constants.SCREEN_WIDTH;
      int y0 = head.getY() * Constants.CELL_BLOCK_SIZE - Constants.SCREEN_HEIGHT / 2;
      int y1 = y0 + Constants.SCREEN_HEIGHT;

      glLoadIdentity();
      glOrtho(x0, x1, y0, y1, -1, 1);
    }

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
