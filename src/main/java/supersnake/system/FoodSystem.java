
package supersnake.system;

import supersnake.attribute.Renderable;
import supersnake.attribute.Updatable;
import supersnake.Constants;
import supersnake.object.Food;
import supersnake.util.RNG;

import static org.lwjgl.opengl.GL11.*;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class FoodSystem implements Updatable, Renderable{
  private int amount;
  private Set<Food> foods;

  public FoodSystem(){
    amount = 0;
    foods = new HashSet<>();
  }

  public Collection<Food> getFoods(){
    return foods;
  }

  public void setAmount(int amount){
    int difference = amount - this.amount;
    if(difference > 0){
      for(int i = 0; i < difference; ++i){
        foods.add(new Food(RNG.location()));
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
    foods.forEach(f -> {
      if(f.isConsumed()){
        f.move(RNG.location());
      }
    });
  }

  @Override
  public void render(){
    foods.forEach(Food::render);
  }
}
