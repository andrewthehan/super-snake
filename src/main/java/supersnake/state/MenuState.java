
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
  private Button play;

  public MenuState(){
    buttons = new HashSet<>();
    play = new Button((Constants.WORLD_WIDTH - 222) / 2, (Constants.WORLD_HEIGHT - 76) / 2, 222, 76, Assets.BUTTON_PLAY);
    play.setAction(() -> StateManager.push(new GameState()));
    buttons.add(play);
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
  }

  @Override
  public void render(){
    buttons.forEach(b -> b.render());
  }
}
