
package game.object;

import game.attribute.Body;
import game.attribute.DynamicBody;
import game.Constants;
import game.util.CellBlock;
import game.util.Direction;
import game.util.UpdateController;

import static org.lwjgl.opengl.GL11.*;

import java.awt.Point;
import java.util.Collection;
import java.util.ArrayDeque;
import java.util.Deque;

public class Snake implements DynamicBody{
  private UpdateController uController;
  private Deque<CellBlock> body;
  private Direction direction;

  public Snake(int x, int y, int length){
    this(new Point(x, y), length, Direction.UP);
  }

  public Snake(Point initialLocation, int length){
    this(initialLocation, length, Direction.UP);
  }

  public Snake(Point initialLocation, int length, Direction direction){
    uController = new UpdateController(1000000000 / 10);
    body = new ArrayDeque<>();
    reset(initialLocation, length, direction);
  }

  public void reset(int x, int y, int length, Direction direction){
    reset(new Point(x, y), length, direction);
  }

  public void reset(Point initialLocation, int length, Direction direction){
    body.clear();
    for(int i = 0; i < length; ++i){
      body.addLast(new CellBlock(initialLocation));
    }

    this.direction = direction;

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

  public Direction getDirection(){
    return direction;
  }

  public void setDirection(Direction direction){
    this.direction = direction;
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
    else if(body instanceof Snake){
      System.out.println("DEAD");
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
  public void update(long timeElapsed){
    if(uController.shouldUpdate(timeElapsed)){
      step();
    }
  }

  @Override
  public void render(){
    glColor3f(0, 0, 0);
  	glBegin(GL_QUADS);
    body.descendingIterator().forEachRemaining(cb -> {
			glVertex2f(cb.getX() * Constants.CELL_BLOCK_SIZE, cb.getY() * Constants.CELL_BLOCK_SIZE);
			glVertex2f((cb.getX() + 1) * Constants.CELL_BLOCK_SIZE, cb.getY() * Constants.CELL_BLOCK_SIZE);
			glVertex2f((cb.getX() + 1) * Constants.CELL_BLOCK_SIZE, (cb.getY() + 1) * Constants.CELL_BLOCK_SIZE);
			glVertex2f(cb.getX() * Constants.CELL_BLOCK_SIZE, (cb.getY() + 1) * Constants.CELL_BLOCK_SIZE);
    });
    glEnd();
  }
}
