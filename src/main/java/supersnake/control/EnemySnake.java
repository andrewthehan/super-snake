
package supersnake.control;

import supersnake.object.attribute.Body;
import supersnake.object.Snake;
import supersnake.util.CellBlock;
import supersnake.util.Direction;
import supersnake.util.RNG;
import supersnake.util.Time;
import supersnake.Skins;

public class EnemySnake extends Enemy{
  private Snake snake;

  public EnemySnake(Body target, int x, int y, int length){
    super(new Snake(x, y, length), target);
    snake = (Snake) object;
    snake.setSkin(Skins.SNAKE_ENEMY);
    snake.setUpdateDelay(Time.SECOND / 7.0);
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

    snake.setNextDirection(nextDirection);
  }

  @Override
  public void update(double timeElapsed){
    // chance of (not) changing direction
    if(RNG.integer(10) == 0){
      chase();
    }
    snake.update(timeElapsed);
  }

  @Override
  public void render(){
    snake.render();
  }
}
