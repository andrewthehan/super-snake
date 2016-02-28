
package supersnake.object.decoration;

import supersnake.util.IterativeColor;

import java.awt.Color;

public class Skin{
  public static final SnakeSkin DEFAULT_SNAKE = new SnakeSkin(Color.BLACK, Color.BLACK, Color.BLACK);
  public static final Skin DEFAULT_WALL = new Skin(Color.BLACK);
  public static final Skin DEFAULT_FOOD = new Skin(Color.RED);

  private final Color color;

  public Skin(Color color){
    this.color = color;
  }

  public Color getColor(){
    return color;
  }

  public static class SnakeSkin extends Skin{
    private final Color head, tail;

    public SnakeSkin(Color head, Color body, Color tail){
      super(body);
      this.head = head;
      this.tail = tail;
    }

    public Color getHeadColor(){
      return head;
    }

    public Color getBodyColor(){
      Color body = getColor();
      if(body instanceof IterativeColor){
        ((IterativeColor) body).reset();
      }
      return body;
    }

    public Color getTailColor(){
      return tail;
    }
  }
}
