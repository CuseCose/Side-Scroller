import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

public class DarknessOverlay implements Sprite {

  int x, y;
  double lightlvl;
  Image dimg;


  public DarknessOverlay(int x, int y) {
    this.x = x;
    this.y = y;
    dimg = tk.getImage(DOCPATH + "shading\\0.png");
  }


  public void update(double light) {
    lightlvl = light;
    dimg = tk.getImage(DOCPATH + "shading\\" + (int) (lightlvl * (10 / 3)) + ".png");
  }


  public void draw(Graphics g, int xloc, int yloc) {
    g.setColor(Color.BLUE);
    //g.drawString(lightlvl+"",xloc+5, yloc+10);
    g.drawImage(dimg, xloc, yloc, BLOCKSIZE, BLOCKSIZE, null);
  }


  public double getLightlvl() {
    return lightlvl;
  }

  public void draw(Graphics g) {
  }

  public int getY() {
    return 0;
  }

  public int getX() {
    return 0;
  }
}
