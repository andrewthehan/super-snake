
package supersnake.util;

public class Location{
  private final int x, y;

  public Location(int x, int y){
    this.x = x;
    this.y = y;
  }

  public int getX(){
    return x;
  }

  public int getY(){
    return y;
  }

  @Override
  public boolean equals(Object o){
    return o instanceof Location && o != null && ((Location) o).getX() == x && ((Location) o).getY() == y;
  }
}
