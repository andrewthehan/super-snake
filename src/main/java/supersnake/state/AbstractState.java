
package supersnake.state;

import supersnake.attribute.Renderable;
import supersnake.attribute.Updatable;

public abstract class AbstractState implements Updatable, Renderable{
  public abstract void exit();
  public abstract void load();
  public abstract void pause();
  public abstract void resume();
}
