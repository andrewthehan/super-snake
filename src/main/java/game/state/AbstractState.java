
package game.state;

import game.attribute.Renderable;
import game.attribute.Updatable;

public abstract class AbstractState implements Updatable, Renderable{
  public abstract void exit();
  public abstract void load();
  public abstract void pause();
  public abstract void resume();
}
