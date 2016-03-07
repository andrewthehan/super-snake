
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

  protected Body obtainedBy;
  protected boolean isStarted;
  protected boolean isDone;

  public AbstractItem(Location location, Assets.Image image, double duration){
    this.image = new Component(location.getX() * Constants.CELL_BLOCK_SIZE, location.getY() * Constants.CELL_BLOCK_SIZE, Constants.CELL_BLOCK_SIZE, Constants.CELL_BLOCK_SIZE, image);
    body = new CellBlock(location);
    uController = new UpdateController(duration);

    obtainedBy = null;
    isStarted = false;
    isDone = false;
  }

  public abstract void apply(Map map);

  public abstract void stop();

  public boolean isObtained(){
    return obtainedBy != null;
  }

  @Override
  public boolean isKilled(){
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
        isDone = true;
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
