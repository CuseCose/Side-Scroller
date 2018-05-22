import java.awt.Color;
import java.awt.Graphics;
import java.awt.Toolkit;

public interface Sprite {

  String DOCPATH = (System.getenv("USERPROFILE"))
      + "\\Development\\IntelliJ\\IdeaProjects\\Side-Scroller\\textures\\";
  int WIDTH = 1280, HEIGHT = 900, BLOCKSIZE = 40;

  void draw(Graphics g);

  int getY();

  int getX();

  Toolkit tk = Toolkit.getDefaultToolkit();
  Color invColor = new Color(204, 204, 179);
  Color invTextColor = new Color(175, 175, 175);
}


