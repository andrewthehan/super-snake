
package supersnake.game.system;

import supersnake.attribute.Renderable;
import supersnake.attribute.Updatable;
import supersnake.Constants;
import supersnake.game.map.Bounds;
import supersnake.game.object.attribute.Killable;
import supersnake.game.object.Food;
import supersnake.game.system.CameraSystem;
import supersnake.util.CellBlock;
import supersnake.util.Location;
import supersnake.util.RNG;

import static org.lwjgl.opengl.GL11.*;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class FoodSystem implements Updatable, Renderable{
  private int amount;
  private Set<Food> foods;

  private Bounds bounds;

  public FoodSystem(){
    amount = 0;
    foods = new HashSet<>();
  }

  public void setBounds(Bounds bounds){
    this.bounds = bounds;
  }

  public Collection<Food> getFoods(){
    return foods;
  }

  public void setAmount(int amount){
    int difference = amount - this.amount;
    if(difference > 0){
      for(int i = 0; i < difference; ++i){
        foods.add(new Food(RNG.location(bounds)));
      }
    }
    else if(difference < 0){
      for(int i = 0; i > difference; --i){
        foods.remove(foods.iterator().next());
      }
    }
    this.amount = amount;
  }

  @Override
  public void update(double timeElapsed){
    foods.stream().filter(Food::isConsumed).forEach(f -> f.move(RNG.location(bounds)));
  }

  @Override
  public void render(){
    foods.forEach(Food::render);
  }
}
