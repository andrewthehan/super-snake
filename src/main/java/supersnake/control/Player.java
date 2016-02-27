
package supersnake.control;

import supersnake.attribute.Renderable;
import supersnake.attribute.Updatable;
import supersnake.Constants;
import supersnake.input.Key;
import supersnake.input.KeyManager;
import supersnake.object.decoration.Skin;
import supersnake.object.Snake;
import supersnake.util.Direction;
import supersnake.util.Time;

public class Player implements Updatable, Renderable{
  private Snake snake;

  public Player(){
    snake = new Snake(Constants.GRID_WIDTH / 2, Constants.GRID_HEIGHT / 2, 5, Direction.UP);
    snake.setSkin(Skin.SNAKE_RAINBOW);
    snake.setUpdateDelay(Time.SECOND / 10.0);
  }

  public Snake getSnake(){
    return snake;
  }

  @Override
  public void update(double timeElapsed){
    if(KeyManager.isPressed(Key.UP) && snake.getDirection() != Direction.DOWN){
      snake.setNextDirection(Direction.UP);
    }
    else if(KeyManager.isPressed(Key.RIGHT) && snake.getDirection() != Direction.LEFT){
      snake.setNextDirection(Direction.RIGHT);
    }
    else if(KeyManager.isPressed(Key.DOWN) && snake.getDirection() != Direction.UP){
      snake.setNextDirection(Direction.DOWN);
    }
    else if(KeyManager.isPressed(Key.LEFT) && snake.getDirection() != Direction.RIGHT){
      snake.setNextDirection(Direction.LEFT);
    }
    snake.update(timeElapsed);
  }

  @Override
  public void render(){
    snake.render();
  }
}
