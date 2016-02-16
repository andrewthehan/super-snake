
package game.object;

import game.attribute.Body;
import game.attribute.StaticBody;
import game.Constants;
import game.util.CellBlock;
import game.util.RNG;

import static org.lwjgl.opengl.GL11.*;

import java.awt.Point;
import java.util.Collection;
import java.util.HashSet;

public class Food implements StaticBody{
  private CellBlock body;
  private boolean isConsumed;

  public Food(int x, int y){
    body = new CellBlock(x, y);
    isConsumed = false;
  }

  public Food(Point location){
    body = new CellBlock(location);
  }

  public int getX(){
    return body.getX();
  }

  public int getY(){
    return body.getY();
  }

  public Point getLocation(){
    return body.getLocation();
  }

  public boolean isConsumed(){
    return isConsumed;
  }

  public void consume(){
    isConsumed = true;
  }

  public void reset(int x, int y){
    body.setLocation(x, y);
    isConsumed = false;
  }

  public void reset(Point location){
    body.setLocation(location);
    isConsumed = false;
  }

  @Override
  public Collection<CellBlock> getBody(){
    Collection<CellBlock> toReturn = new HashSet<>();
    toReturn.add(body);
    return toReturn;
  }

  @Override
  public void collide(Body b){
    if(b instanceof Snake){
      reset(RNG.location());
    }
  }

  @Override
  public void render(){
    glColor3f(1f, 0, 0);
  	glBegin(GL_QUADS);
		glVertex2f(getX() * Constants.CELL_BLOCK_SIZE, getY() * Constants.CELL_BLOCK_SIZE);
		glVertex2f((getX() + 1) * Constants.CELL_BLOCK_SIZE, getY() * Constants.CELL_BLOCK_SIZE);
		glVertex2f((getX() + 1) * Constants.CELL_BLOCK_SIZE, (getY() + 1) * Constants.CELL_BLOCK_SIZE);
		glVertex2f(getX() * Constants.CELL_BLOCK_SIZE, (getY() + 1) * Constants.CELL_BLOCK_SIZE);
    glEnd();
  }
}
