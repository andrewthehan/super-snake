
package game.input;

public final class MouseManager{
  private static MouseButtonState[] MOUSE_BUTTON_STATES = new MouseButtonState[MouseButton.NUMBER_OF_MOUSE_BUTTONS];
  private static int x, y;

  public static void setPressed(int mouseButton, boolean isPressed){
    MOUSE_BUTTON_STATES[mouseButton] = isPressed ? MouseButtonState.PRESSED : MouseButtonState.RELEASED;
  }

  public static void setLocation(int x, int y){
    MouseManager.x = x;
    MouseManager.y = y;
  }

  public static MouseButtonState getState(int mouseButton){
    return MOUSE_BUTTON_STATES[mouseButton];
  }

  public static int getMouseX(){
    return x;
  }

  public static int getMouseY(){
    return y;
  }

  public static boolean isIdle(int mouseButton){
    return MOUSE_BUTTON_STATES[mouseButton] == MouseButtonState.IDLE;
  }

  public static boolean isHeld(int mouseButton){
    return MOUSE_BUTTON_STATES[mouseButton] == MouseButtonState.HELD;
  }

  public static boolean isPressed(int mouseButton){
    MouseButtonState mouseButtonState = MOUSE_BUTTON_STATES[mouseButton];
    if(mouseButtonState == MouseButtonState.PRESSED){
      MOUSE_BUTTON_STATES[mouseButton] = MouseButtonState.HELD;
    }
    return mouseButtonState == MouseButtonState.PRESSED;
  }

  public static boolean isReleased(int mouseButton){
    MouseButtonState mouseButtonState = MOUSE_BUTTON_STATES[mouseButton];
    if(mouseButtonState == MouseButtonState.RELEASED){
      MOUSE_BUTTON_STATES[mouseButton] = MouseButtonState.IDLE;
    }
    return mouseButtonState == MouseButtonState.RELEASED;
  }
}
