
package game;

import game.input.*;
import game.state.*;

import org.lwjgl.*;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.*;

public class SuperSnake{
	private static final int FRAMERATE = 60;

  private GLFWErrorCallback errorCallback;
  private GLFWKeyCallback keyCallback;

  private long window;

  private long lastTime;

  public static void main(String[] args){
    new SuperSnake();
  }

  public SuperSnake(){
    try{
      init();
      loop();

      glfwDestroyWindow(window);
      keyCallback.release();
    }
    finally{
      glfwTerminate();
      errorCallback.release();
    }
	}

  private void init() {
      glfwSetErrorCallback(errorCallback = GLFWErrorCallback.createPrint(System.err));

      if ( glfwInit() != GLFW_TRUE )
          throw new IllegalStateException("Unable to initialize GLFW");

      glfwDefaultWindowHints();
      glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
      glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);

      window = glfwCreateWindow(Constants.WORLD_WIDTH, Constants.WORLD_HEIGHT, "Super Snake", NULL, NULL);
      if(window == NULL){
        throw new RuntimeException("Failed to create the GLFW window");
      }

      glfwSetKeyCallback(window, keyCallback = new GLFWKeyCallback(){
        @Override
        public void invoke(long window, int key, int scancode, int action, int mods){
          switch(key){
            case GLFW_KEY_ESCAPE:
              StateManager.pop();
            case GLFW_KEY_UP:
              KeyManager.setPressed(Key.UP, action == GLFW_PRESS);
              break;
            case GLFW_KEY_RIGHT:
              KeyManager.setPressed(Key.RIGHT, action == GLFW_PRESS);
              break;
            case GLFW_KEY_DOWN:
              KeyManager.setPressed(Key.DOWN, action == GLFW_PRESS);
              break;
            case GLFW_KEY_LEFT:
              KeyManager.setPressed(Key.LEFT, action == GLFW_PRESS);
              break;
            default:
              System.err.println("Key (" + key + ") not supported.");
              break;
          }
        }
      });

      GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
      glfwSetWindowPos(window, (vidmode.width() - Constants.WORLD_WIDTH) / 2, (vidmode.height() - Constants.WORLD_HEIGHT) / 2);

      glfwMakeContextCurrent(window);
      glfwSwapInterval(1);

      glfwShowWindow(window);
  }

  public void loop(){
    GL.createCapabilities();
    glClearColor(1f, 1f, 1f, 0f);
    glOrtho(0, Constants.WORLD_WIDTH, 0, Constants.WORLD_HEIGHT, 0, 1);
    glViewport(0, 0, Constants.WORLD_WIDTH, Constants.WORLD_HEIGHT);

    StateManager.push(new GameState());

    lastTime = getTime();
    while(glfwWindowShouldClose(window) == GLFW_FALSE && !StateManager.isEmpty()){
      glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

      StateManager.update(getDelta());
      StateManager.render();

      glfwSwapBuffers(window);

      glfwPollEvents();
    }
  }

	public long getTime(){
		return System.nanoTime();//GLFW.glfwGetTime();
	}

	public long getDelta(){
		long time = getTime();
		long delta = time - lastTime;
		lastTime = time;
		return delta;
	}
}
