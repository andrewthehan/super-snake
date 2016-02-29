
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
      if(action == GLFW_PRESS || action == GLFW_RELEASE){
        int k = convertGLFWToKey(key);
        if(k == -1){
          System.err.println("Key (" + key + ") not supported.");
        }
        else{
          setPressed(k, action == GLFW_PRESS);
        }
      }
    });
  }

  private static int convertGLFWToKey(int glfwKey){
    switch(glfwKey){
      case GLFW_KEY_A: return Key.A;
      case GLFW_KEY_B: return Key.B;
      case GLFW_KEY_C: return Key.C;
      case GLFW_KEY_D: return Key.D;
      case GLFW_KEY_E: return Key.E;
      case GLFW_KEY_F: return Key.F;
      case GLFW_KEY_G: return Key.G;
      case GLFW_KEY_H: return Key.H;
      case GLFW_KEY_I: return Key.I;
      case GLFW_KEY_J: return Key.J;
      case GLFW_KEY_K: return Key.K;
      case GLFW_KEY_L: return Key.L;
      case GLFW_KEY_M: return Key.M;
      case GLFW_KEY_N: return Key.N;
      case GLFW_KEY_O: return Key.O;
      case GLFW_KEY_P: return Key.P;
      case GLFW_KEY_Q: return Key.Q;
      case GLFW_KEY_R: return Key.R;
      case GLFW_KEY_S: return Key.S;
      case GLFW_KEY_T: return Key.T;
      case GLFW_KEY_U: return Key.U;
      case GLFW_KEY_V: return Key.V;
      case GLFW_KEY_W: return Key.W;
      case GLFW_KEY_X: return Key.X;
      case GLFW_KEY_Y: return Key.Y;
      case GLFW_KEY_Z: return Key.Z;
      case GLFW_KEY_ESCAPE: return Key.ESCAPE;
      case GLFW_KEY_UP: return Key.UP;
      case GLFW_KEY_RIGHT: return Key.RIGHT;
      case GLFW_KEY_DOWN: return Key.DOWN;
      case GLFW_KEY_LEFT: return Key.LEFT;
      case GLFW_KEY_LEFT_ALT: return Key.LEFT_ALT;
      case GLFW_KEY_LEFT_CONTROL: return Key.LEFT_CTRL;
      case GLFW_KEY_LEFT_SHIFT: return Key.LEFT_SHIFT;
      case GLFW_KEY_RIGHT_ALT: return Key.RIGHT_ALT;
      case GLFW_KEY_RIGHT_CONTROL: return Key.RIGHT_CTRL;
      case GLFW_KEY_RIGHT_SHIFT: return Key.RIGHT_SHIFT;
      default: return -1;
    }
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
    return KEY_STATES[key] == KeyState.IDLE || KEY_STATES[key] == KeyState.RELEASED;
  }

  public static boolean isHeld(int key){
    return KEY_STATES[key] == KeyState.HELD || KEY_STATES[key] == KeyState.PRESSED;
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
