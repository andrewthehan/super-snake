
package supersnake.graphic.ui;

import supersnake.attribute.Renderable;
import supersnake.util.Location;
import supersnake.Assets;

import static org.lwjgl.opengl.GL11.*;

public abstract class Component implements Renderable{
  private int x, y;
  private int width, height;
  private Assets.Image image;

  public Component(int x, int y, int width, int height, Assets.Image image){
    setLocation(x, y);
    setSize(width, height);
    setImage(image);
  }

  public Component(Location location, int width, int height, Assets.Image image){
    setLocation(location);
    setSize(width, height);
    setImage(image);
  }

  public boolean contains(int x, int y){
    return this.x <= x && x < this.x + width && this.y <= y && y < this.y + height;
  }

  @Override
  public void render(){
    glColor3f(1f, 1f, 1f);
    if(image.getComp() == 3){
      if((image.getWidth() & 3) != 0){
        glPixelStorei(GL_UNPACK_ALIGNMENT, 2 - (image.getWidth() & 1));
      }
      glTexImage2D(GL_TEXTURE_2D, 0, GL_RGB, image.getWidth(), image.getHeight(), 0, GL_RGB, GL_UNSIGNED_BYTE, image.getImage());
    }
    else{
      glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, image.getWidth(), image.getHeight(), 0, GL_RGBA, GL_UNSIGNED_BYTE, image.getImage());

      glEnable(GL_BLEND);
      glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
    }
    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);

    glEnable(GL_TEXTURE_2D);
		glBegin(GL_QUADS);

		glTexCoord2f(0, 1);
		glVertex2f(x, y);

		glTexCoord2f(1, 1);
		glVertex2f(x + width, y);

		glTexCoord2f(1, 0);
		glVertex2f(x + width, y + height);

		glTexCoord2f(0, 0);
		glVertex2f(x, y + height);

    glEnd();
    glDisable(GL_TEXTURE_2D);
  }

  public int getX(){
    return x;
  }

  public int getY(){
    return y;
  }

  public int getWidth(){
    return width;
  }

  public int getHeight(){
    return height;
  }

  public Assets.Image getImage(){
    return image;
  }

  public void setLocation(int x, int y){
    this.x = x;
    this.y = y;
  }

  public void setLocation(Location location){
    setLocation(location.getX(), location.getY());
  }

  public void setSize(int width, int height){
    this.width = width;
    this.height = height;
  }

  public void setImage(Assets.Image image){
    this.image = image;
  }
}
