
package supersnake.state;

import supersnake.Assets;
import supersnake.Constants;
import supersnake.graphic.ui.Button;
import supersnake.state.GameState;
import supersnake.state.StateManager;

import java.util.HashSet;
import java.util.Set;

public class MenuState extends AbstractState{
  private Set<Button> buttons;

  @Override
  public void exit(){
  }

  @Override
  public void load(){
    buttons = new HashSet<>();
    
    Button play = new Button((Constants.SCREEN_WIDTH - 222) / 2, (Constants.SCREEN_HEIGHT - 76) / 2, 222, 76, Assets.BUTTON_PLAY);
    play.setAction(() -> StateManager.push(new GameState()));
    buttons.add(play);
  }

  @Override
  public void pause(){
  }

  @Override
  public void resume(){
  }

  @Override
  public void update(double timeElapsed){
    buttons.forEach(b -> b.update(timeElapsed));
  }

  @Override
  public void render(){
    buttons.forEach(b -> b.render());
  }
}
