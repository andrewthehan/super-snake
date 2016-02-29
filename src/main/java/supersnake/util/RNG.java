
package supersnake.util;

import supersnake.object.Map;

import java.util.Random;

public final class RNG{
  private static final Random R = new Random();

  public static boolean bool(){
    return R.nextBoolean();
  }

  public static int integer(int i){
    return R.nextInt(i);
  }

  public static Location location(int x0, int x1, int y0, int y1){
    return new Location(integer(x1 - x0) + x0, integer(y1 - y0) + y0);
  }

  public static Location location(Map.Bounds bounds){
    return location(bounds.getLeft(), bounds.getRight(), bounds.getBottom(), bounds.getTop());
  }
}
