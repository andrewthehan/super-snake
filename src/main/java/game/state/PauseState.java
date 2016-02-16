
package game.state;

import game.input.Key;
import game.input.KeyManager;

public class PauseState extends AbstractState{

  public void exit(){
  }

  public void load(){
  }

  public void pause(){
  }

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
