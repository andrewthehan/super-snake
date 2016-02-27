
package supersnake.graphic.ui;

import supersnake.Assets;
import supersnake.attribute.Updatable;
import supersnake.input.MouseButton;
import supersnake.input.MouseManager;
import supersnake.util.function.Actionable;

import static org.lwjgl.opengl.GL11.*;

public class Button extends Component implements Updatable{
  private ButtonState state;
  private Actionable action;
  private boolean pressedOutside;

  public Button(int x, int y, int width, int height, Assets.Image image){
    super(x, y, width, height, image);
    state = ButtonState.IDLE;
    pressedOutside = false;
  }

  public void setAction(Actionable action){
    this.action = action;
  }

  @Override
  public void render(){
    float imageYStart, imageYEnd;
    switch(state){
      case IDLE:
        imageYStart = 0f / 3f;
        imageYEnd = 1f / 3f;
        break;
      case CLICKED:
        imageYStart = 1f / 3f;
        imageYEnd = 2f / 3f;
        break;
      case HOVERED:
        imageYStart = 2f / 3f;
        imageYEnd = 3f / 3f;
        break;
      default:
        imageYStart = imageYEnd = 0;
        break;
    }

    Assets.Image image = getImage();

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

		glTexCoord2f(0, imageYEnd);
		glVertex2f(getX(), getY());

		glTexCoord2f(1, imageYEnd);
		glVertex2f(getX() + getWidth(), getY());

		glTexCoord2f(1, imageYStart);
		glVertex2f(getX() + getWidth(), getY() + getHeight());

		glTexCoord2f(0, imageYStart);
		glVertex2f(getX(), getY() + getHeight());

    glEnd();
    glDisable(GL_TEXTURE_2D);
  }


  @Override
  public void update(double timeElapsed){
    if(MouseManager.isReleased(MouseButton.LEFT)){
      if(state == ButtonState.CLICKED){
        state = ButtonState.HOVERED;
        action.act();
      }
      else{
        pressedOutside = false;
      }
    }
    else if(MouseManager.isPressed(MouseButton.LEFT)){
      if(state == ButtonState.HOVERED){
        state = ButtonState.CLICKED;
      }
      else{
        pressedOutside = true;
      }
    }
    else if(contains(MouseManager.getMouseX(), MouseManager.getMouseY())){
      if(!pressedOutside){
        if(state == ButtonState.IDLE){
          state = ButtonState.HOVERED;
        }
        else if(state != ButtonState.CLICKED && MouseManager.isHeld(MouseButton.LEFT)){
          state = ButtonState.CLICKED;
        }
      }
    }
    else if(state != ButtonState.IDLE){
      state = ButtonState.IDLE;
    }
  }
}
