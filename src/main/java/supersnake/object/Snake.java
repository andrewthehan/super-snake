
package supersnake.object;

import supersnake.graphic.CellBlockRenderer;
import supersnake.object.attribute.Body;
import supersnake.object.attribute.DynamicBody;
import supersnake.object.attribute.Skinnable;
import supersnake.object.decoration.Skin;
import supersnake.object.exception.InvalidSkinException;
import supersnake.util.CellBlock;
import supersnake.util.Direction;
import supersnake.util.UpdateController;

import java.awt.Color;
import java.awt.Point;
import java.util.Collection;
import java.util.ArrayDeque;
import java.util.Deque;

public class Snake implements DynamicBody, Skinnable{
  private UpdateController uController;
  private Deque<CellBlock> body;
  private Direction direction, nextDirection;
  private Skin.SnakeSkin skin;

  public Snake(int x, int y, int length){
    this(new Point(x, y), length, Direction.UP);
  }

  public Snake(int x, int y, int length, Direction direction){
    this(new Point(x, y), length, direction);
  }

  public Snake(Point initialLocation, int length){
    this(initialLocation, length, Direction.UP);
  }

  public Snake(Point initialLocation, int length, Direction direction){
    uController = new UpdateController();
    body = new ArrayDeque<>();
    init(initialLocation, length, direction);

    skin = Skin.DEFAULT_SNAKE;
  }

  private void init(int x, int y, int length, Direction direction){
    init(new Point(x, y), length, direction);
  }

  private void init(Point initialLocation, int length, Direction direction){
    for(int i = 0; i < length; ++i){
      body.addLast(new CellBlock(initialLocation));
    }

    this.direction = direction;
    this.nextDirection = direction;

    int i = 0;
    for(CellBlock cb : body){
      switch(direction){
        case UP:
          cb.translate(0, -i);
          break;
        case RIGHT:
          cb.translate(-i, 0);
          break;
        case DOWN:
          cb.translate(0, i);
          break;
        case LEFT:
          cb.translate(i, 0);
          break;
      }
      ++i;
    }
  }

  public void setUpdateDelay(double updateDelay){
    uController.setUpdateDelay(updateDelay);
  }

  public Direction getDirection(){
    return direction;
  }

  public void setNextDirection(Direction direction){
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

  public void setLocation(int x, int y){
    body.forEach(cb -> cb.setLocation(x, y));
  }

  public void setLocation(Point location){
    body.forEach(cb -> cb.setLocation(location));
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
  public void collide(Body body){
    if(body instanceof Food){
      increment();
    }
    else if(body instanceof Wall){
      System.out.println("WALL DEAD");
    }
    else if(body instanceof Snake){
      System.out.println("SNAKE DEAD");
    }
  }

  @Override
  public void checkCollision(Body body){
    if(body instanceof Snake && this == (Snake) body){
      CellBlock head = getHead();
      for(CellBlock cb : getBody()){
        if(head.intersects(cb) && !head.equals(cb)){
          collide(body);
        }
      }
    }
    else{
      DynamicBody.super.checkCollision(body);
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
