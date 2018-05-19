import java.awt.Color;
import java.awt.Graphics;
import java.awt.Toolkit;

public interface Sprite {

  String DOCPATH = (System.getenv("user.dir")) + "\\textures\\";
  int WIDTH = 1280, HEIGHT = 900, BLOCKSIZE = 50;

  void draw(Graphics g);

  int getY();

  int getX();

  Toolkit tk = Toolkit.getDefaultToolkit();
  Color invColor = new Color(204, 204, 179);
}


