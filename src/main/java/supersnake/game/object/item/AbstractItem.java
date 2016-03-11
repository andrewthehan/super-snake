
package supersnake.game.object.item;

import supersnake.attribute.Updatable;
import supersnake.game.map.Map;
import supersnake.game.object.attribute.Body;
import supersnake.game.object.StaticBody;
import supersnake.graphic.ui.Component;
import supersnake.util.CellBlock;
import supersnake.util.Location;
import supersnake.util.UpdateController;
import supersnake.Assets;
import supersnake.Constants;

import java.util.Collection;
import java.util.HashSet;

public abstract class AbstractItem extends StaticBody implements Updatable{
  private Component image;
  private CellBlock body;
  private UpdateController uController;

  private boolean isStarted;
  private boolean isDone;

  protected Map map;
  protected Body obtainedBy;

  public AbstractItem(Location location, Assets.Image image, double duration){
    this.image = new Component(location.getX() * Constants.CELL_BLOCK_SIZE, location.getY() * Constants.CELL_BLOCK_SIZE, Constants.CELL_BLOCK_SIZE, Constants.CELL_BLOCK_SIZE, image);
    body = new CellBlock(location);
    uController = new UpdateController(duration);

    obtainedBy = null;
    isStarted = false;
    isDone = false;
  }

  protected abstract void onActivate();

  protected abstract void onStop();

  public void apply(Map map){
    this.map = map;
  }

  public void activate(){
    onActivate();
    isStarted = true;
  }

  public void stop(){
    onStop();
    isDone = true;
  }

  public boolean isActivated(){
    return isStarted && !isDone;
  }

  public boolean isObtained(){
    return obtainedBy != null;
  }

  @Override
  public boolean isDead(){
    return isDone;
  }

  @Override
  public Collection<CellBlock> getBody(){
    Collection<CellBlock> toReturn = new HashSet<>();
    toReturn.add(body);
    return toReturn;
  }

  @Override
  public void collide(Body collided, Location location){
    obtainedBy = collided;
  }

  @Override
  public void update(double timeElapsed){
    if(isStarted){
      if(uController.shouldUpdate(timeElapsed)){
        stop();
      }
    }
  }

  @Override
  public void render(){
    if(!isObtained()){
      image.render();
    }
  }
}
