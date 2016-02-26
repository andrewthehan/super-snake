
package supersnake.state;

import supersnake.Assets;
import supersnake.Constants;
import supersnake.graphic.ui.Button;
import supersnake.input.Key;
import supersnake.input.KeyManager;

import java.util.HashSet;
import java.util.Set;

public class PauseState extends AbstractState{
  private Set<Button> buttons;
  private Button back;

  public PauseState(){
    buttons = new HashSet<>();
    back = new Button((Constants.WORLD_WIDTH - 72) / 2, (Constants.WORLD_HEIGHT - 45) / 2, 72, 45, Assets.BUTTON_BACK);
    back.setAction(() -> StateManager.pop());
    buttons.add(back);
  }

  @Override
  public void exit(){
  }

  @Override
  public void load(){
  }

  @Override
  public void pause(){
  }

  @Override
  public void resume(){
  }

  @Override
  public void update(long timeElapsed){
    buttons.forEach(b -> b.update(timeElapsed));
    if(KeyManager.isReleased(Key.P)){
      StateManager.pop();
    }
  }

  @Override
  public void render(){
    buttons.forEach(b -> b.render());
  }
}
