
package game.input;

public final class KeyManager{
  private static KeyState[] KEY_STATES = new KeyState[Key.NUMBER_OF_KEYS];

  public static void setPressed(int key, boolean isPressed){
    KEY_STATES[key] = isPressed ? KeyState.PRESSED : KeyState.RELEASED;
  }

  public static KeyState getState(int key){
    return KEY_STATES[key];
  }

  public static boolean isIdle(int key){
    return KEY_STATES[key] == KeyState.IDLE;
  }

  public static boolean isHeld(int key){
    return KEY_STATES[key] == KeyState.HELD;
  }

  public static boolean isPressed(int key){
    KeyState keyState = KEY_STATES[key];
    if(keyState == KeyState.PRESSED){
      KEY_STATES[key] = KeyState.HELD;
    }
    return keyState == KeyState.PRESSED;
  }

  public static boolean isReleased(int key){
    KeyState keyState = KEY_STATES[key];
    if(keyState == KeyState.RELEASED){
      KEY_STATES[key] = KeyState.IDLE;
    }
    return keyState == KeyState.RELEASED;
  }
}
