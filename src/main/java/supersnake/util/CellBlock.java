
package supersnake.util;

public class CellBlock{
  private int x, y;

  public CellBlock(int x, int y){
    this.x = x;
    this.y = y;
  }

  public CellBlock(Location location){
    this(location.getX(), location.getY());
  }

  public boolean intersects(CellBlock cb){
    return x == cb.getX() && y == cb.getY();
  }

  public int getX(){
    return x;
  }

  public int getY(){
    return y;
  }

  public Location getLocation(){
    return new Location(x, y);
  }

  public void setX(int x){
    this.x = x;
  }

  public void setY(int y){
    this.y = y;
  }

  public void setLocation(int x, int y){
    this.x = x;
    this.y = y;
  }

  public void setLocation(Location location){
    this.x = location.getX();
    this.y = location.getY();
  }

  public void translate(int dx, int dy){
    x += dx;
    y += dy;
  }
}
