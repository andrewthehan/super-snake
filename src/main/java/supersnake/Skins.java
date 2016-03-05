
package supersnake;

import supersnake.game.object.decoration.Skin;
import supersnake.util.IterativeColor;

import java.awt.Color;

public class Skins{
  public static final Skin.SnakeSkin SNAKE_RAINBOW = new Skin.SnakeSkin(Color.BLACK, new IterativeColor(Color.RED, Color.ORANGE, Color.YELLOW, Color.GREEN, Color.BLUE), Color.BLACK);
  public static final Skin.SnakeSkin SNAKE_ENEMY = new Skin.SnakeSkin(Color.MAGENTA, Color.MAGENTA, Color.MAGENTA);
}
