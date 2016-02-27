
package supersnake.input;

import supersnake.Constants;

import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFWCursorPosCallback;
import org.lwjgl.glfw.GLFWMouseButtonCallback;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

import java.nio.IntBuffer;
import java.util.Arrays;

public final class MouseManager{
  private static MouseButtonState[] MOUSE_BUTTON_STATES = new MouseButtonState[MouseButton.NUMBER_OF_MOUSE_BUTTONS];
  private static int x, y;

  private static final IntBuffer VIEWPORT_PARAMS = BufferUtils.createIntBuffer(4);
  private static final GLFWCursorPosCallback positionCallback;
  private static final GLFWMouseButtonCallback buttonCallback;

  static{
    Arrays.fill(MOUSE_BUTTON_STATES, MouseButtonState.IDLE);
    positionCallback = GLFWCursorPosCallback.create((window, x, y) -> {
      glGetIntegerv(GL_VIEWPORT, VIEWPORT_PARAMS);
      setLocation((int) x - VIEWPORT_PARAMS.get(0), (int) (Constants.SCREEN_HEIGHT - y) - VIEWPORT_PARAMS.get(1));
    });
    buttonCallback = GLFWMouseButtonCallback.create((window, button, action, mods) -> {
      switch(button){
        case GLFW_MOUSE_BUTTON_1:
          setPressed(MouseButton.LEFT, action == GLFW_PRESS);
          break;
        case GLFW_MOUSE_BUTTON_2:
          setPressed(MouseButton.RIGHT, action == GLFW_PRESS);
          break;
        case GLFW_MOUSE_BUTTON_3:
          setPressed(MouseButton.MIDDLE, action == GLFW_PRESS);
          break;
        default:
          System.err.println("Mouse button (" + button + ") not supported.");
          break;
      }
    });
  }

  public static GLFWCursorPosCallback getPositionCallback(){
    return positionCallback;
  }

  public static GLFWMouseButtonCallback getButtonCallback(){
    return buttonCallback;
  }

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
