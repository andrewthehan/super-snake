
package game.input;

import game.input.Key;

public final class KeyManager{
  private static boolean[] KEYS = new boolean[Key.NUMBER_OF_KEYS];

  public static void setPressed(int key, boolean isPressed){
    KEYS[key] = isPressed;
  }

  public static boolean isPressed(int key){
    return KEYS[key];
  }
}
