
package supersnake.system;

import supersnake.object.attribute.Body;
import supersnake.util.CellBlock;
import supersnake.Constants;

import static org.lwjgl.opengl.GL11.*;

public class CameraSystem{
  private static Body target;

  private static int x, y;

  private CameraSystem(){}

  public static void setTarget(Body target){
    CameraSystem.target = target;
  }

  public static void update(double timeElapsed){
    if(target != null){
      CellBlock location = target.getBody().iterator().next();
      int targetX = location.getX() * Constants.CELL_BLOCK_SIZE - Constants.SCREEN_WIDTH / 2;
      int targetY = location.getY() * Constants.CELL_BLOCK_SIZE - Constants.SCREEN_HEIGHT / 2;
      x += (targetX - x) * timeElapsed;
      y += (targetY - y) * timeElapsed;
    }
    glLoadIdentity();
    glOrtho(x, x + Constants.SCREEN_WIDTH, y, y + Constants.SCREEN_HEIGHT, -1, 1);
  }
}
