
package supersnake;

import supersnake.input.Key;
import supersnake.input.KeyManager;
import supersnake.input.MouseManager;
import supersnake.state.MenuState;
import supersnake.state.StateManager;

import org.lwjgl.glfw.GLFWCursorPosCallback;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.glfw.GLFWMouseButtonCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.NULL;

public class SuperSnake{
  private GLFWErrorCallback errorCallback;
  private GLFWKeyCallback keyCallback;
	private GLFWCursorPosCallback posCallback;
	private GLFWMouseButtonCallback mouseCallback;

  private long window;

  private double lastTime;

  public static void main(String[] args){
    new SuperSnake();
  }

  public SuperSnake(){
    try{
      init();
      loop();
    }
    finally{
      glfwTerminate();
      errorCallback.release();
    }
	}

  private void init() {
      glfwSetErrorCallback(errorCallback = GLFWErrorCallback.createPrint(System.err));

      if(glfwInit() != GLFW_TRUE){
        throw new IllegalStateException("Unable to initialize GLFW");
			}

      glfwDefaultWindowHints();
      glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
      glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);

      window = glfwCreateWindow(Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT, "Super Snake", NULL, NULL);
      if(window == NULL){
        throw new RuntimeException("Failed to create the GLFW window");
      }

      glfwSetKeyCallback(window, keyCallback = KeyManager.getCallback());

			glfwSetCursorPosCallback(window, posCallback = MouseManager.getPositionCallback());

			glfwSetMouseButtonCallback(window, mouseCallback = MouseManager.getButtonCallback());

      GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
      glfwSetWindowPos(window, (vidmode.width() - Constants.SCREEN_WIDTH) / 2, (vidmode.height() - Constants.SCREEN_HEIGHT) / 2);

      glfwMakeContextCurrent(window);
      glfwSwapInterval(1);

      glfwShowWindow(window);
  }

  public void loop(){
    GL.createCapabilities();
    glClearColor(1f, 1f, 1f, 0f);
    glOrtho(0, Constants.SCREEN_WIDTH, 0, Constants.SCREEN_HEIGHT, 0, 1);
		glViewport(0, 0, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);

    StateManager.push(new MenuState());

    lastTime = getTime();
    while(glfwWindowShouldClose(window) == GLFW_FALSE && !StateManager.isEmpty()){
      glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

      StateManager.update(getDelta());
      StateManager.render();

      glfwSwapBuffers(window);

      glfwPollEvents();

			if(KeyManager.isReleased(Key.ESCAPE)){
				StateManager.pop();
			}
    }

		glfwDestroyWindow(window);
		keyCallback.release();
  }

	public double getTime(){
		return glfwGetTime();
	}

	public double getDelta(){
		double time = getTime();
		double delta = time - lastTime;
		lastTime = time;
		return delta;
	}
}
