
package game;

import game.attribute.Renderable;
import game.attribute.Updatable;
import game.input.Key;
import game.input.KeyManager;
import game.object.Snake;
import game.util.Direction;

public class Player implements Updatable, Renderable{
  private Snake snake;

  public Player(){
    snake = new Snake(10, 10, 5);
  }

  public Snake getSnake(){
    return snake;
  }

  public void clear(){
    snake.setLength(0);
  }

  public void reset(){
    snake.reset(Constants.GRID_WIDTH / 2, Constants.GRID_HEIGHT / 2, 5, Direction.UP);
  }

  @Override
  public void update(long timeElapsed){
    if(KeyManager.isPressed(Key.UP) && snake.getDirection() != Direction.DOWN){
      snake.setDirection(Direction.UP);
    }
    else if(KeyManager.isPressed(Key.RIGHT) && snake.getDirection() != Direction.LEFT){
      snake.setDirection(Direction.RIGHT);
    }
    else if(KeyManager.isPressed(Key.DOWN) && snake.getDirection() != Direction.UP){
      snake.setDirection(Direction.DOWN);
    }
    else if(KeyManager.isPressed(Key.LEFT) && snake.getDirection() != Direction.RIGHT){
      snake.setDirection(Direction.LEFT);
    }
    snake.update(timeElapsed);
  }

  @Override
  public void render(){
    snake.render();
  }
}
