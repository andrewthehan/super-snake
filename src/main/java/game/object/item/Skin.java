
package game.object.item;

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
  public static final Skin.Snake SNAKE_DEFAULT = new Skin.Snake(Color.BLACK, Color.BLACK, Color.BLACK);
  public static final Skin.Snake SNAKE_SKIN_1 = new Skin.Snake(Color.RED, Color.BLACK, Color.BLACK);
  public static final Skin.Snake SNAKE_RAINBOW = new Skin.Snake(Color.BLACK, RANDOM_COLOR, Color.BLACK);

  public static class Snake{
    private final Color[] s;

    public Snake(Color head, Color body, Color tail){
      s = new Color[3];
      s[0] = head;
      s[1] = body;
      s[2] = tail;
    }

    public Color getHeadColor(){
      return s[0];
    }

    public Color getBodyColor(){
      return s[1];
    }

    public Color getTailColor(){
      return s[2];
    }
  }
}
