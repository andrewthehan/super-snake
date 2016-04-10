
package supersnake.game.actor;

import supersnake.game.object.attribute.Body;
import supersnake.game.object.Snake;
import supersnake.util.CellBlock;
import supersnake.util.Direction;
import supersnake.util.Location;
import supersnake.util.RNG;
import supersnake.util.Time;
import supersnake.Skins;

public class EnemySnake extends Enemy{
  public EnemySnake(Body target, int x, int y, int length){
    super(new Snake(x, y, length), target);
    Snake snake = (Snake) object;
    snake.setActor(this);
    snake.setSkin(Skins.SNAKE_ENEMY);
    snake.setUpdateDelay(Time.SECOND / 7.0);
  }

  public EnemySnake(Body target, Location location, int length){
    super(new Snake(location, length), target);
    Snake snake = (Snake) object;
    snake.setActor(this);
    snake.setSkin(Skins.SNAKE_ENEMY);
    snake.setUpdateDelay(Time.SECOND / 7.0);
  }

  private void chase(){
    if(target.isDead()){
      return;
    }
    Snake snake = (Snake) object;
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

    snake.setDirection(nextDirection);
  }

  @Override
  public void update(double timeElapsed){
    // chance of changing direction
    if(RNG.integer(10) == 0){
      chase();
    }
    super.update(timeElapsed);
  }
}
