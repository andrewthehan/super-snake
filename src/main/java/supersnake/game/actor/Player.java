
package supersnake.game.actor;

import supersnake.input.Key;
import supersnake.input.KeyManager;
import supersnake.game.object.Snake;
import supersnake.util.Direction;
import supersnake.util.Time;
import supersnake.Skins;

public class Player extends Actor{
  public Player(){
    this(0, 0, 1, Direction.UP);
  }

  public Player(int x, int y, int length, Direction direction){
    super(new Snake(x, y, length, direction));
    Snake snake = (Snake) object;
    snake.setActor(this);
    snake.setSkin(Skins.SNAKE_RAINBOW);
    snake.setUpdateDelay(Time.SECOND / 10.0);
  }

  @Override
  public void update(double timeElapsed){
    Snake snake = (Snake) object;
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
    super.update(timeElapsed);
  }
}
