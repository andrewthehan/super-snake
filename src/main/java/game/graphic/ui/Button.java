
package game.graphic.ui;

import game.Assets;
import game.attribute.Updatable;
import game.input.MouseButton;
import game.input.MouseManager;
import game.util.function.Actionable;

public class Button extends Component implements Updatable{
  private Assets.Image image, clickedImage;
  private Actionable action;
  private boolean isPressed;

  public Button(int x, int y, int width, int height, Assets.Image image, Assets.Image clickedImage){
    super(x, y, width, height, image);
    this.image = image;
    this.clickedImage = clickedImage;
    isPressed = false;
  }

  public void setAction(Actionable action){
    this.action = action;
  }

  @Override
  public void update(long timeElapsed){
    if(MouseManager.isPressed(MouseButton.LEFT) && contains(MouseManager.getMouseX(), MouseManager.getMouseY())){
      setImage(clickedImage);
      isPressed = true;
    }
    else if(isPressed && MouseManager.isReleased(MouseButton.LEFT)){
      setImage(image);
      isPressed = false;
      if(contains(MouseManager.getMouseX(), MouseManager.getMouseY())){
        action.act();
      }
    }
  }
}
