
package game.system;

import game.attribute.Renderable;
import game.attribute.Updatable;
import game.Constants;
import game.object.Food;
import game.util.RNG;

import static org.lwjgl.opengl.GL11.*;

import java.util.Collection;
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

  public Collection<Food> getFoods(){
    return foods;
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
      for(int i = 0; i > difference; --i){
        foods.remove(foods.iterator().next());
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
    foods.forEach(Food::render);
  }
}
