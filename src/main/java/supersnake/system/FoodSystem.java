
package supersnake.system;

import supersnake.attribute.Renderable;
import supersnake.attribute.Updatable;
import supersnake.Constants;
import supersnake.object.Food;
import supersnake.object.Map;
import supersnake.util.CellBlock;
import supersnake.util.RNG;

import static org.lwjgl.opengl.GL11.*;

import java.awt.Point;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class FoodSystem implements Updatable, Renderable{
  private int amount;
  private Set<Food> foods;

  private Map map;

  public FoodSystem(){
    amount = 0;
    foods = new HashSet<>();
  }

  public void setMap(Map map){
    this.map = map;
  }

  public Collection<Food> getFoods(){
    return foods;
  }

  public void setAmount(int amount){
    int difference = amount - this.amount;
    if(difference > 0){
      for(int i = 0; i < difference; ++i){
        foods.add(new Food(randomValidPosition()));
      }
    }
    else if(difference < 0){
      for(int i = 0; i > difference; --i){
        foods.remove(foods.iterator().next());
      }
    }
    this.amount = amount;
  }

  private Point randomValidPosition(){
    Map.Bounds bounds = map.getBounds();
    CellBlock cell = new CellBlock(bounds.getLeft(), bounds.getBottom());
    while(map.intersects(cell)){
      cell.setLocation(RNG.location(bounds.getLeft(), bounds.getRight(), bounds.getBottom(), bounds.getTop()));
    }
    return new Point(cell.getX(), cell.getY());
  }

  @Override
  public void update(double timeElapsed){
    foods.forEach(f -> {
      if(f.isConsumed()){
        f.move(randomValidPosition());
      }
    });
  }

  @Override
  public void render(){
    foods.forEach(Food::render);
  }
}
