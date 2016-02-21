
package game.graphic;

import game.Constants;
import game.util.CellBlock;

import static org.lwjgl.opengl.GL11.*;

import java.awt.Color;
import java.util.Collection;

public class CellBlockRenderer{
  public static void render(CellBlock cb, Color color){
    glColor3f(color.getRed() / 255f, color.getGreen() / 255f, color.getBlue() / 255f);
    glBegin(GL_QUADS);
    glVertex2f(cb.getX() * Constants.CELL_BLOCK_SIZE, cb.getY() * Constants.CELL_BLOCK_SIZE);
    glVertex2f((cb.getX() + 1) * Constants.CELL_BLOCK_SIZE, cb.getY() * Constants.CELL_BLOCK_SIZE);
    glVertex2f((cb.getX() + 1) * Constants.CELL_BLOCK_SIZE, (cb.getY() + 1) * Constants.CELL_BLOCK_SIZE);
    glVertex2f(cb.getX() * Constants.CELL_BLOCK_SIZE, (cb.getY() + 1) * Constants.CELL_BLOCK_SIZE);
    glEnd();
  }

  public static void render(Collection<CellBlock> cbs, Color color){
    glColor3f(color.getRed() / 255f, color.getGreen() / 255f, color.getBlue() / 255f);
    glBegin(GL_QUADS);
      cbs.forEach(cb -> {
      glVertex2f(cb.getX() * Constants.CELL_BLOCK_SIZE, cb.getY() * Constants.CELL_BLOCK_SIZE);
      glVertex2f((cb.getX() + 1) * Constants.CELL_BLOCK_SIZE, cb.getY() * Constants.CELL_BLOCK_SIZE);
      glVertex2f((cb.getX() + 1) * Constants.CELL_BLOCK_SIZE, (cb.getY() + 1) * Constants.CELL_BLOCK_SIZE);
      glVertex2f(cb.getX() * Constants.CELL_BLOCK_SIZE, (cb.getY() + 1) * Constants.CELL_BLOCK_SIZE);
    });
    glEnd();
  }
}
