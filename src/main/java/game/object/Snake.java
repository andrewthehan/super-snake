
package game.object;

import game.attribute.Updatable;
import game.attribute.Renderable;
import game.Constants;
import game.util.CellBlock;
import game.util.Direction;
import game.util.UpdateController;

import static org.lwjgl.opengl.GL11.*;

import java.awt.Point;
import java.util.Collection;
import java.util.ArrayDeque;
import java.util.Deque;

public class Snake implements Updatable, Renderable{
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
    for(int i = 0; i < length; ++i){
      body.addLast(new CellBlock(initialLocation));
    }

    this.direction = direction;
  }

  public Collection<CellBlock> getBody(){
    return body;
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

  public void step(){
    CellBlock head = body.getFirst();
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
  public void update(long timeElapsed){
    if(KeyManager.isPressed(Key.UP) && getDirection() != Direction.DOWN){
      setDirection(Direction.UP);
    }
    else if(KeyManager.isPressed(Key.RIGHT) && getDirection() != Direction.LEFT){
      setDirection(Direction.RIGHT);
    }
    else if(KeyManager.isPressed(Key.DOWN) && getDirection() != Direction.UP){
      setDirection(Direction.DOWN);
    }
    else if(KeyManager.isPressed(Key.LEFT) && getDirection() != Direction.RIGHT){
      setDirection(Direction.LEFT);
    }
    if(uController.shouldUpdate(timeElapsed)){
      step();
    }
  }

  @Override
  public void render(){
  	glBegin(GL_QUADS);
    body.descendingIterator().forEachRemaining(cb -> {
      if(cb == body.getFirst()){
        glColor3f(0, 1, 0);
      }
      else{
        glColor3f(0, 0, 0);
      }
			glVertex2f(cb.getX() * Constants.CELL_BLOCK_SIZE, cb.getY() * Constants.CELL_BLOCK_SIZE);
			glVertex2f((cb.getX() + 1) * Constants.CELL_BLOCK_SIZE, cb.getY() * Constants.CELL_BLOCK_SIZE);
			glVertex2f((cb.getX() + 1) * Constants.CELL_BLOCK_SIZE, (cb.getY() + 1) * Constants.CELL_BLOCK_SIZE);
			glVertex2f(cb.getX() * Constants.CELL_BLOCK_SIZE, (cb.getY() + 1) * Constants.CELL_BLOCK_SIZE);
    });
    glEnd();
  }
}
