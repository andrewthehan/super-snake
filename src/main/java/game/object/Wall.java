
package game.object;

import game.graphic.CellBlockRenderer;
import game.object.attribute.Body;
import game.object.attribute.Skinnable;
import game.object.attribute.StaticBody;
import game.object.decoration.Skin;
import game.util.CellBlock;

import java.awt.Color;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class Wall implements StaticBody, Skinnable{
  private Set<CellBlock> body;
  private Skin skin;

  public Wall(int x0, int x1, int y0, int y1){
    body = new HashSet<>();
    for(int i = x0; i < x1; ++i){
      for(int j = y0; j < y1; ++j){
        body.add(new CellBlock(i, j));
      }
    }

    skin = Skin.WALL_DEFAULT;
  }

  @Override
  public Collection<CellBlock> getBody(){
    return body;
  }

  @Override
  public void collide(Body body){
  }

  @Override
  public void render(){
    CellBlockRenderer.render(body, skin.getColor());
  }

  @Override
  public void setSkin(Skin skin){
    this.skin = skin;
  }
}
