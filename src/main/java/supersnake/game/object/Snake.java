
package supersnake.game.object;

import supersnake.graphic.CellBlockRenderer;
import supersnake.game.exception.InvalidSkinException;
import supersnake.game.object.attribute.Body;
import supersnake.game.object.DynamicBody;
import supersnake.game.object.attribute.Skinnable;
import supersnake.game.object.attribute.Killable;
import supersnake.game.object.decoration.Skin;
import supersnake.util.CellBlock;
import supersnake.util.Direction;
import supersnake.util.Location;
import supersnake.util.UpdateController;

import java.awt.Color;
import java.util.Collection;
import java.util.ArrayDeque;
import java.util.Deque;

public class Snake extends DynamicBody implements Skinnable{
  private UpdateController uController;
  private Deque<CellBlock> body;
  private Direction nextDirection;
  private Skin.SnakeSkin skin;

  public Snake(int x, int y, int length){
    this(new Location(x, y), length, Direction.UP);
  }

  public Snake(int x, int y, int length, Direction direction){
    this(new Location(x, y), length, direction);
  }

  public Snake(Location initialLocation, int length, Direction direction){
    super(direction);
    uController = new UpdateController();
    body = new ArrayDeque<>();
    init(initialLocation, length, direction);

    skin = Skin.DEFAULT_SNAKE;
  }

  private void init(Location initialLocation, int length, Direction direction){
    for(int i = 0; i < length; ++i){
      body.addLast(new CellBlock(initialLocation));
    }

    this.direction = direction;
    this.nextDirection = direction;

    setLocation(initialLocation);
  }

  public void setUpdateDelay(double updateDelay){
    uController.setUpdateDelay(updateDelay);
  }

  @Override
  public void setDirection(Direction direction){
    nextDirection = direction;
  }

  public int getLength(){
    return body.size();
  }

  public void setLength(int length){
    int difference = length - getLength();
    if(difference > 0){
      for(int i = 0; i < difference; ++i){
        increment();
      }
    }
    else{
      for(int i = 0; i > difference; --i){
        decrement();
      }
    }
  }

  public void increment(){
    CellBlock tail = body.getLast();
    body.addLast(new CellBlock(tail.getLocation()));
  }

  public void decrement(){
    body.removeLast();
  }

  public void decrementFromHead(){
    body.removeFirst();
  }

  public void setLocation(int x, int y){
    setLocation(new Location(x, y));
  }

  public void setLocation(Location location){
    body.forEach(cb -> cb.setLocation(location));
    for(CellBlock cb : body){
      if(cb != getHead()){
        switch(direction){
          case UP:
            cb.translate(0, -1);
            break;
          case RIGHT:
            cb.translate(-1, 0);
            break;
          case DOWN:
            cb.translate(0, 1);
            break;
          case LEFT:
            cb.translate(1, 0);
            break;
        }
      }
    }
  }

  public CellBlock getHead(){
    return body.getFirst();
  }

  public CellBlock getTail(){
    return body.getLast();
  }

  public void step(){
    CellBlock head = getHead();
    CellBlock tail = body.removeLast();

    tail.setLocation(head.getLocation());
    switch(direction){
      case UP:
        tail.translate(0, 1);
        break;
      case RIGHT:
        tail.translate(1, 0);
        break;
      case DOWN:
        tail.translate(0, -1);
        break;
      case LEFT:
        tail.translate(-1, 0);
        break;
    }
    body.addFirst(tail);
  }

  @Override
  public Collection<CellBlock> getBody(){
    return body;
  }

  @Override
  public void collide(Body collided, Location location){
    super.collide(collided, location);
    if(collided instanceof Food){
      increment();
    }
    else if(collided instanceof Wall){
      decrementFromHead();
    }
    else if(collided instanceof Snake){
      Snake collidedSnake = (Snake) collided;
      // collided with itself
      if(collidedSnake == this){
        CellBlock newTail = body.stream().filter(cb -> cb.intersects(getHead()) && cb != getHead()).findFirst().get();
        while(newTail != getTail()){
          decrement();
        }
      }
      else{
        // head collided with something else (head or body)
        if(getHead().getLocation().equals(location)){
          decrementFromHead();
        }
        // body collided with another snake's head
        else if(body.stream().anyMatch(cb -> cb.getLocation().equals(location))){
          CellBlock newTail = body.stream().filter(cb -> cb.getLocation().equals(location)).findFirst().get();
          while(newTail != getTail()){
            decrement();
          }
        }
      }
    }
  }

  @Override
  public void checkCollision(Body collided){
    if(isDead()){
      return;
    }
    else if(collided instanceof Snake && (Snake) collided == this){
      CellBlock head = getHead();
      for(CellBlock cb : getBody()){
        if(head.intersects(cb) && head != cb){
          collide(collided, cb.getLocation());
          break;
        }
      }
    }
    else{
      super.checkCollision(collided);
    }
  }

  @Override
  public void update(double timeElapsed){
    if(uController.shouldUpdate(timeElapsed)){
      if(nextDirection != direction){
        direction = nextDirection;
      }
      step();
    }
  }

  @Override
  public void render(){
    CellBlock head = getHead();
    CellBlock tail = getTail();

    // if any color is an iterative color, calling #getXColor resets color index to 0
    Color headColor = skin.getHeadColor();
    Color tailColor = skin.getTailColor();
    Color bodyColor = skin.getBodyColor();

    // descendingIterator so blocks closer to head are painted on top of blocks farther from head
    body.forEach(cb ->
        CellBlockRenderer.render(cb, (cb == head) ? headColor : (cb == tail) ? tailColor : bodyColor)
    );
  }

  @Override
  public void setSkin(Skin skin){
    if(!(skin instanceof Skin.SnakeSkin)){
      new InvalidSkinException();
    }
    this.skin = (Skin.SnakeSkin) skin;
  }
}
