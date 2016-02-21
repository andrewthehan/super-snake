
package game.state;

import game.graphic.ui.Button;
import game.input.Key;
import game.input.KeyManager;

public class PauseState extends AbstractState{

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
    if(KeyManager.isReleased(Key.P)){
      StateManager.pop();
    }
  }

  @Override
  public void render(){
  }
}
