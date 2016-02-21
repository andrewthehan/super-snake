
package game.state;

import game.Assets;
import game.Constants;
import game.graphic.ui.Button;
import game.state.GameState;
import game.state.StateManager;

public class MenuState extends AbstractState{
  private Button button;

  @Override
  public void exit(){
  }

  @Override
  public void load(){
    button = new Button((Constants.WORLD_WIDTH - 222) / 2, (Constants.WORLD_HEIGHT - 76) / 2, 222, 76, Assets.BUTTON, Assets.CLICKED_BUTTON);
    button.setAction(() -> StateManager.push(new GameState()));
  }

  @Override
  public void pause(){
  }

  @Override
  public void resume(){
  }

  @Override
  public void update(long timeElapsed){
    button.update(timeElapsed);
  }

  @Override
  public void render(){
    button.render();
  }
}
