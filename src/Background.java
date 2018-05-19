import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

public class Background implements Sprite {


  double time;
  int playerLayer;
  Color dirtLayer = new Color(103, 51, 50);
  Color caveLayer = new Color(153, 153, 102);
  Image moon;


  public Background() {
    time = 12;
    playerLayer = 0;
    moon = tk.getImage(DOCPATH + "moon.png");
  }


  public void draw(Graphics g) {
    switch (playerLayer) {
      case 1:
        g.setColor(Color.black);
        break;
      case 0:
        if (time <= 12) {
          g.setColor(Color.cyan);
        } else {
          g.setColor(Color.black);
        }
        break;
      case -1:
        g.setColor(dirtLayer);
        break;
      case -2:
        g.setColor(caveLayer);
        break;
    }
    g.fillRect(0, 0, WIDTH, HEIGHT);

    if (playerLayer == 1 || playerLayer == 0) {
      if (time <= 12) {
        g.setColor(Color.yellow);
        g.fillOval((int) (time * (WIDTH / 10) - 100), (int) (HEIGHT * .1), 100, 100);
      } else {
                /*
                g.setColor(Color.lightGray);
                g.fillOval((int)((time-12)*(WIDTH/10)-100), (int)(HEIGHT*.1), 100,100);
                */
        g.drawImage(moon, (int) ((time - 12) * (WIDTH / 10) - 100), (int) (HEIGHT * .1), 100, 100,
            null);
      }
    }
  }


  public void move(double p1y, double p1x, int[] groundmap, int mapheight, int[] biomemap) {
    time += .002;
    if (time >= 24) {
      time = 0;
    }
    if (p1y <= groundmap[(int) p1x] + 5) {
      if (p1y < mapheight * .1) {
        playerLayer = 1;//sky layer
      } else {
        playerLayer = 0;//ground level
      }
    } else if (p1y > groundmap[(int) p1x] + 15) {
      if (p1y > groundmap[(int) p1x] + (mapheight / 10) * 2) {
        playerLayer = -2;//cavern layer
      } else {
        playerLayer = -1;//dirt layer
      }
    }
  }


  public double getTime() {
    return time;
  }

  public boolean isDay() {
    return time <= 12;
  }

  public int getY() {
    return 0;
  }

  public int getX() {
    return 0;
  }
}
