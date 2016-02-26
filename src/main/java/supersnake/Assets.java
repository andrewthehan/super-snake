
package supersnake;

import org.lwjgl.BufferUtils;

import static org.lwjgl.stb.STBImage.*;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

public class Assets{
  public static final String RESOURCES = "res/";
  public static final Image BUTTON_PLAY = new Image(RESOURCES + "button_play.png");
  public static final Image BUTTON_BACK = new Image(RESOURCES + "button_back.png");

  public static class Image{
    private static final IntBuffer W = BufferUtils.createIntBuffer(1);
    private static final IntBuffer H = BufferUtils.createIntBuffer(1);
    private static final IntBuffer COMP = BufferUtils.createIntBuffer(1);
    private final ByteBuffer image;
    private final int width, height, comp;

    public Image(String imageLocation){
      image = stbi_load(imageLocation, W, H, COMP, 0);
      width = W.get(0);
      height = H.get(0);
      comp = COMP.get(0);
    }

    public ByteBuffer getImage(){
      return image;
    }

    public int getWidth(){
      return width;
    }

    public int getHeight(){
      return height;
    }

    public int getComp(){
      return comp;
    }
  }
}
