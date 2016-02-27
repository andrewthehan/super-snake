
package supersnake.input;

import org.lwjgl.glfw.GLFWKeyCallback;
import static org.lwjgl.glfw.GLFW.*;

import java.util.Arrays;

public final class KeyManager{
  private static KeyState[] KEY_STATES = new KeyState[Key.NUMBER_OF_KEYS];
  private static final GLFWKeyCallback callback;

  static{
    Arrays.fill(KEY_STATES, KeyState.IDLE);
    callback = GLFWKeyCallback.create((window, key, scancode, action, mods) -> {
      switch(key){
        case GLFW_KEY_ESCAPE:
          setPressed(Key.ESCAPE, action == GLFW_PRESS);
          break;
        case GLFW_KEY_UP:
          setPressed(Key.UP, action == GLFW_PRESS);
          break;
        case GLFW_KEY_RIGHT:
          setPressed(Key.RIGHT, action == GLFW_PRESS);
          break;
        case GLFW_KEY_DOWN:
          setPressed(Key.DOWN, action == GLFW_PRESS);
          break;
        case GLFW_KEY_LEFT:
          setPressed(Key.LEFT, action == GLFW_PRESS);
          break;
        case GLFW_KEY_P:
          setPressed(Key.P, action == GLFW_PRESS);
          break;
        default:
          System.err.println("Key (" + key + ") not supported.");
          break;
      }
    });
  }

  public static GLFWKeyCallback getCallback(){
    return callback;
  }

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
