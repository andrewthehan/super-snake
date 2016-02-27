
package supersnake.control;

import supersnake.attribute.Renderable;
import supersnake.attribute.Updatable;
import supersnake.object.attribute.Body;

public abstract class Enemy implements Updatable, Renderable{
  private Body body;

  public void setBody(Body body){
    this.body = body;
  }

  public Body getBody(){
    return body;
  }
}
