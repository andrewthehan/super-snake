
package supersnake.game.system;

import supersnake.game.object.attribute.Body;
import supersnake.util.CellBlock;
import supersnake.Constants;

import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import static org.lwjgl.opengl.GL11.*;

public class CameraSystem{
  private static final double WIDTH_TO_HEIGHT_RATIO = (double) Constants.SCREEN_WIDTH / Constants.SCREEN_HEIGHT;
  private static final int MINIMUM_HEIGHT = Constants.SCREEN_HEIGHT / 3;
  private static final int MINIMUM_WIDTH = (int) (MINIMUM_HEIGHT * WIDTH_TO_HEIGHT_RATIO);
  private static final int MINIMUM_GAP = Constants.CELL_BLOCK_SIZE * 10;
  private static Set<Body> targets = new HashSet<>();

  private static int centerX = Constants.SCREEN_WIDTH / 2, centerY = Constants.SCREEN_HEIGHT / 2;
  private static int width = Constants.SCREEN_WIDTH, height = Constants.SCREEN_HEIGHT;

  private CameraSystem(){}

  public static void addTarget(Body target){
    targets.add(target);
  }

  public static void addTargets(Collection<Body> bodies){
    bodies.forEach(CameraSystem::addTarget);
  }

  public static void removeTarget(Body target){
    if(targets.contains(target)){
      targets.remove(target);
    }
  }

  public static void removeTargets(Collection<Body> bodies){
    bodies.forEach(CameraSystem::removeTarget);
  }

  public static void update(double timeElapsed){
    if(targets.isEmpty()){
      return;
    }

    // center position
    Set<Integer> xSet = targets.stream().mapToInt(b -> b.getBody().iterator().next().getX() * Constants.CELL_BLOCK_SIZE).boxed().collect(Collectors.toSet());
    Set<Integer> ySet = targets.stream().mapToInt(b -> b.getBody().iterator().next().getY() * Constants.CELL_BLOCK_SIZE).boxed().collect(Collectors.toSet());
    int averageX = xSet.stream().mapToInt(Integer::intValue).sum() / targets.size();
    int averageY = ySet.stream().mapToInt(Integer::intValue).sum() / targets.size();

    centerX += (averageX - centerX) * timeElapsed;
    centerY += (averageY - centerY) * timeElapsed;

    // zoom to include all targets
    int minX = xSet.stream().mapToInt(Integer::intValue).min().getAsInt();
    int maxX = xSet.stream().mapToInt(Integer::intValue).max().getAsInt();
    int minY = ySet.stream().mapToInt(Integer::intValue).min().getAsInt();
    int maxY = ySet.stream().mapToInt(Integer::intValue).max().getAsInt();
    int desiredWidth = Integer.max(2 * (Integer.max(centerX - minX, maxX - centerX) + MINIMUM_GAP), MINIMUM_WIDTH);
    int desiredHeight = Integer.max(2 * (Integer.max(centerY - minY, maxY - centerY) + MINIMUM_GAP), MINIMUM_HEIGHT);

    // width dominates
    if((double) desiredWidth / desiredHeight > WIDTH_TO_HEIGHT_RATIO){
      width += (desiredWidth - width) * timeElapsed;
      height = (int) (width / WIDTH_TO_HEIGHT_RATIO);
    }
    // height dominates
    else{
      height += (desiredHeight - height) * timeElapsed;
      width = (int) (height * WIDTH_TO_HEIGHT_RATIO);
    }
  }

  public static void render(){
    glLoadIdentity();
    glOrtho(centerX - width / 2, centerX + width / 2, centerY - height / 2, centerY + height / 2, -1, 1);
  }
}
