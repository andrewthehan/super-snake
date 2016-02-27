
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
import supersnake.util.Direction;

import static org.lwjgl.opengl.GL11.*;

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
    player = new Player(0, 0, 5, Direction.UP);

    CellBlock head = player.getSnake().getHead();
    int x0 = head.getX() - Constants.GRID_WIDTH / 2;
    int x1 = x0 + Constants.GRID_WIDTH;
    int y0 = head.getY() - Constants.GRID_HEIGHT / 2;
    int y1 = y0 + Constants.GRID_HEIGHT;

    map = Map.builder()
      .bounds(x0, x1, y0, y1)
      .add(new EnemySnake(player.getSnake(), 15, 10, 7))
      .build();

    foodSystem = new FoodSystem();
    foodSystem.setMap(map);
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

    CellBlock head = player.getSnake().getHead();
    int x0 = (head.getX() - Constants.GRID_WIDTH / 2) * Constants.CELL_BLOCK_SIZE;
    int x1 = x0 + Constants.SCREEN_WIDTH;
    int y0 = (head.getY() - Constants.GRID_HEIGHT / 2) * Constants.CELL_BLOCK_SIZE;
    int y1 = y0 + Constants.SCREEN_HEIGHT;

    glLoadIdentity();
    glOrtho(x0, x1, y0, y1, -1, 1);

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
