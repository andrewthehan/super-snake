
package supersnake.util;

import java.awt.Point;
import java.util.Random;

public final class RNG{
  private static final Random R = new Random();

  public static boolean bool(){
    return R.nextBoolean();
  }

  public static int integer(int i){
    return R.nextInt(i);
  }

  public static Point location(int x0, int x1, int y0, int y1){
    return new Point(integer(x1 - x0) + x0, integer(y1 - y0) + y0);
  }
}
