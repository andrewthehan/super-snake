
package supersnake.control;

import supersnake.attribute.Renderable;
import supersnake.attribute.Updatable;
import supersnake.Constants;
import supersnake.object.attribute.Body;
import supersnake.object.decoration.Skin;
import supersnake.object.Snake;
import supersnake.util.CellBlock;
import supersnake.util.Direction;
import supersnake.util.RNG;

public class EnemySnake implements Updatable, Renderable{
  private Snake snake;
  private Body target;

  public EnemySnake(){
    snake = new Snake(20, 10, 7);
    snake.setSpeed(1000000000 / 7);
    snake.setSkin(Skin.SNAKE_ENEMY);
  }

  public void setTarget(Body target){
    this.target = target;
  }

  public void chase(){
    CellBlock targetLoc = target.getBody().iterator().next();
    CellBlock curLoc = snake.getHead();
    Direction nextDirection = snake.getDirection();
    if(snake.getDirection() == Direction.UP || snake.getDirection() == Direction.DOWN){
      if(targetLoc.getX() < curLoc.getX()){
        nextDirection = Direction.LEFT;
      }
      else if(targetLoc.getX() > curLoc.getX()){
        nextDirection = Direction.RIGHT;
      }
      else if((targetLoc.getY() > curLoc.getY() && snake.getDirection() == Direction.DOWN) ||
          (targetLoc.getY() < curLoc.getY() && snake.getDirection() == Direction.UP)){
        nextDirection = RNG.bool() ? Direction.LEFT : Direction.RIGHT;
      }
    }
    else if(snake.getDirection() == Direction.LEFT || snake.getDirection() == Direction.RIGHT){
      if(targetLoc.getY() < curLoc.getY()){
        nextDirection = Direction.DOWN;
      }
      else if(targetLoc.getY() > curLoc.getY()){
        nextDirection = Direction.UP;
      }
      else if((targetLoc.getX() > curLoc.getX() && snake.getDirection() == Direction.LEFT) ||
          (targetLoc.getX() < curLoc.getX() && snake.getDirection() == Direction.RIGHT)){
        nextDirection = RNG.bool() ? Direction.UP : Direction.DOWN;
      }
    }

    // chance of (not) changing direction
    if(RNG.integer(5) == 0){
      snake.setNextDirection(nextDirection);
    }
  }

  public Snake getSnake(){
    return snake;
  }

  public void clear(){
    snake.setLength(0);
  }

  public void reset(){
    snake.reset(Constants.GRID_WIDTH / 2, Constants.GRID_HEIGHT / 2, 5, Direction.UP);
    snake.setSkin(Skin.SNAKE_ENEMY);
  }

  @Override
  public void update(long timeElapsed){
    chase();
    snake.update(timeElapsed);
  }

  @Override
  public void render(){
    snake.render();
  }
}
