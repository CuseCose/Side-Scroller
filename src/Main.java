import java.applet.Applet;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
//https://docs.oracle.com/javase/tutorial/uiswing/components/textfield.html

public class Main extends Applet implements Runnable, KeyListener, MouseListener {


  private final int WIDTH = 1280, HEIGHT = 900;
  private Thread thread;
  private int screenNum = 0;
  Graphics gfx;
  Image img;
  private boolean firstLaunch = true;
  private Map map;
  private Menu menu;


  public void init() {
    this.resize(WIDTH, HEIGHT);
    this.addKeyListener(this);
    this.addMouseListener(this);
    img = createImage(WIDTH, HEIGHT);
    gfx = img.getGraphics();
    menu = new Menu(0);
    System.out.println("creating thread");
    thread = new Thread(this);
    System.out.println("thread created, starting thread");
    thread.start();
    System.out.println("thread started");
  }


  public void paint(Graphics g) {
    if (screenNum == 0) {
      if (firstLaunch) {
        gfx.setFont(new Font("default", Font.BOLD, 12));
        firstLaunch = false;
      }
      gfx.setColor(Color.BLACK);//background
      gfx.fillRect(0, 0, WIDTH, HEIGHT);//background size
      gfx.setColor(Color.WHITE);
      gfx.drawString("Enter: New Map", (WIDTH / 2) - 200, (HEIGHT / 2));
      menu.draw(gfx);


    } else if (screenNum == 1) {
      //System.out.println("drawing main");
      gfx.setColor(Color.BLACK);//background
      gfx.fillRect(0, 0, WIDTH, HEIGHT);//background size
      map.draw(gfx);

    }
    g.drawImage(img, 0, 0, this);
  }


  public void update(Graphics g) {
    paint(g);
  }


  public void run() {
    for (; ; ) {
      if (screenNum == 0) {
      } else if (screenNum == 1) {
        map.move();
      }
      repaint();
      try {
        Thread.sleep(15);
      } catch (InterruptedException e) {
        e.printStackTrace();
        System.out.println("GAME FAILED TO RUN");
      }
    }
  }


  public void keyTyped(KeyEvent e) {
  }


  public void keyPressed(KeyEvent e) {
    if (screenNum == 1) {
      if (e.getKeyCode() == KeyEvent.VK_D) {
        map.setMovingRight(true);
      } else if (e.getKeyCode() == KeyEvent.VK_A) {
        map.setMovingLeft(true);
      } else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
        map.jump();
      } else if (e.getKeyCode() == KeyEvent.VK_W) {
        map.setClimbing(true);
      } else if (e.getKeyCode() > 47 && e.getKeyCode() < 58) {
        System.out.println(e.getKeyCode());
        if (e.getKeyCode() == '0') {
          System.out.println("selecting invnum 9");
          map.setSelectedItem(9);
        } else {
          System.out.println("selecting invnum " + (e.getKeyCode() - 49));
          map.setSelectedItem(e.getKeyCode() - 49);
        }
      } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
        map.openCloseInv();
      } else if (map.isInvOpen()) {
        if (e.getKeyCode() == KeyEvent.VK_UP) {
          map.changeSelected(false);
        } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
          map.changeSelected(true);
        } else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
          map.craft();
        }
      }
    }
  }


  public void keyReleased(KeyEvent e) {
    System.out.println("key released");
    if (screenNum == 1) {
      if (e.getKeyCode() == KeyEvent.VK_D) {
        map.setMovingRight(false);
      } else if (e.getKeyCode() == KeyEvent.VK_A) {
        map.setMovingLeft(false);
      } else if (e.getKeyCode() == KeyEvent.VK_W) {
        map.setClimbing(false);
      }
    } else if (screenNum == 0) {
      if (e.getKeyCode() == KeyEvent.VK_UP) {
        menu.changeSelectedButton(true);
      } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
        menu.changeSelectedButton(false);
      } else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
        if (menu.getSelectedButton() == 0) {
          map = new Map(1);
          screenNum = 1;
          System.out.println("pressed enter");
        } else if (menu.getSelectedButton() == 1) {

        } else if (menu.getSelectedButton() == 2) {
          System.exit(0);
        }
      }

    }
  }


  public void mouseClicked(MouseEvent e) {
  }


  public void mousePressed(MouseEvent e) {
  }


  public void mouseReleased(MouseEvent e) {
    System.out.println(
        "clicked " + e.getX() + ", " + e.getY() + " Block: " + map.getRealMouseX(e.getX()) + ", "
            + map.getRealMouseY(e.getY()));
    map.leftClick(e.getX(), e.getY());
  }


  public void mouseEntered(MouseEvent e) {
  }


  public void mouseExited(MouseEvent e) {
  }
}


