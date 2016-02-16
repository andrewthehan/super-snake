
package game.object;

import game.attribute.Body;
import game.attribute.StaticBody;
import game.graphic.CellBlockRenderer;
import game.util.CellBlock;

import java.awt.Color;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class Wall implements StaticBody{
  private Color color = new Color(0f, .5f, .5f);
  private Set<CellBlock> body;

  public Wall(int x0, int x1, int y0, int y1){
    body = new HashSet<>();
    for(int i = x0; i < x1; ++i){
      for(int j = y0; j < y1; ++j){
        body.add(new CellBlock(i, j));
      }
    }
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
    CellBlockRenderer.render(body, color);
  }
}
