
package game.system;

import game.attribute.Renderable;
import game.attribute.Updatable;
import game.Constants;
import game.object.Food;
import game.util.RNG;

import static org.lwjgl.opengl.GL11.*;

import java.util.Iterator;
import java.util.HashSet;
import java.util.Set;

public class FoodSystem implements Updatable, Renderable{
  private int amount;
  private Set<Food> foods;

  public FoodSystem(int amount){
    this.amount = amount;
    foods = new HashSet<>();
    for(int i = 0; i < amount; ++i){
      foods.add(new Food(RNG.location()));
    }
  }

  public void clear(){
    setAmount(0);
  }

  public void setAmount(int amount){
    int difference = amount - this.amount;
    if(difference > 0){
      for(int i = 0; i < difference; ++i){
        foods.add(new Food(RNG.location()));
      }
    }
    else{
      Iterator<Food> it = foods.iterator();
      for(int i = 0; i > difference; --i){
        foods.remove(it.next());
      }
    }
    this.amount = amount;
  }

  @Override
  public void update(long timeElapsed){
    foods.forEach(f -> {
      if(f.isConsumed()){
        f.reset(RNG.location());
      }
    });
  }

  @Override
  public void render(){
    glColor3f(1f, 0, 0);
  	glBegin(GL_QUADS);
    foods.forEach(f -> {
  		glVertex2f(f.getX() * Constants.CELL_BLOCK_SIZE, f.getY() * Constants.CELL_BLOCK_SIZE);
  		glVertex2f((f.getX() + 1) * Constants.CELL_BLOCK_SIZE, f.getY() * Constants.CELL_BLOCK_SIZE);
  		glVertex2f((f.getX() + 1) * Constants.CELL_BLOCK_SIZE, (f.getY() + 1) * Constants.CELL_BLOCK_SIZE);
  		glVertex2f(f.getX() * Constants.CELL_BLOCK_SIZE, (f.getY() + 1) * Constants.CELL_BLOCK_SIZE);
    });
    glEnd();
  }
}
