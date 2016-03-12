
package supersnake.game.object.item;

import supersnake.game.map.Map;
import supersnake.util.Location;
import supersnake.util.Time;
import supersnake.util.UpdateController;
import supersnake.Assets;

public class MagnetItem extends AbstractItem{
  private UpdateController uController;

  public MagnetItem(Location location){
    super(location, Assets.ITEM_MAGNET, 3 * Time.SECOND);
    uController = new UpdateController(Time.SECOND / 3);
  }

  @Override
  protected void onActivate(){
  }

  @Override
  protected void onStop(){
  }

  @Override
  public void update(double timeElapsed){
    if(isActivated() && uController.shouldUpdate(timeElapsed)){
      map.getFoodSystem().getFoods().forEach(f -> {
        Location loc = f.getLocation();
        Location targetLoc = obtainedBy.getBody().iterator().next().getLocation();
        int newX = loc.getX(), newY = loc.getY();
        if(loc.getX() != targetLoc.getX()){
          newX += loc.getX() < targetLoc.getX() ? 1 : -1;
        }
        if(loc.getY() != targetLoc.getY()){
          newY += loc.getY() < targetLoc.getY() ? 1 : -1;
        }
        f.move(newX, newY);
      });
    }
    super.update(timeElapsed);
  }
}
