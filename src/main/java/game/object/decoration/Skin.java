
package game.object.decoration;

import game.util.RNG;

import java.awt.Color;

public class Skin{
  private static final Color RANDOM_COLOR = new Color(0, 0, 0){
    @Override
    public int getRed(){
      return RNG.integer(255);
    }

    @Override
    public int getGreen(){
      return RNG.integer(255);
    }

    @Override
    public int getBlue(){
      return RNG.integer(255);
    }
  };
  public static final SnakeSkin SNAKE_DEFAULT = new SnakeSkin(Color.BLACK, Color.BLACK, Color.BLACK);
  public static final SnakeSkin SNAKE_1 = new SnakeSkin(Color.RED, Color.BLACK, Color.BLACK);
  public static final SnakeSkin SNAKE_RAINBOW = new SnakeSkin(Color.BLACK, new IterativeColor(Color.RED, Color.ORANGE, Color.YELLOW, Color.GREEN, Color.BLUE), Color.BLACK);
  public static final SnakeSkin SNAKE_RANDOM = new SnakeSkin(Color.BLACK, RANDOM_COLOR, Color.BLACK);
  public static final Skin WALL_DEFAULT = new Skin(Color.BLACK);
  public static final Skin WALL_1 = new Skin(Color.GRAY);
  public static final Skin FOOD_DEFAULT = new Skin(Color.RED);

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
