import java.awt.Color;
import java.awt.Graphics;

public class Character implements Sprite {

  private InvItem[][] inv;
  private int x, y;
  private double realx;
  private double realy;

  private int hp;
  private int maxhp;

  private int height = 80, width = 20;
  private boolean isMovingRight;
  private boolean isMovingLeft;
  private int xvel = 0, yvel = 0;
  private double friction = .9;
  private double gravity = .9;
  private boolean isClimbing = false;
  private int invLength = 11;
  private int invHeight = 3;

  private int timer = 0;
  //private int jumpTimer=0;
  private boolean inJump = false;

  private boolean isJumping = true;
  private boolean firstLaunch = true;
  public int mapLength, mapHeight;
  boolean invOpen = false;
  private RecipeList re;
  int selectedItem = 0;
  int selectedItemID;


  public Character(Map m) {
    re = new RecipeList();
    x = 0;
    y = 2000;
    inv = new InvItem[invHeight][invLength];
    firstLaunch = true;
    for (int invy = 0; invy < invHeight - 1; invy++) {
      for (int invx = 0; invx < invLength - 1; invx++) {
        System.out.println("creating item at inv slot " + invx + ", " + invy);
        inv[invy][invx] = new InvItem(0, invx, invy);
      }
    }
    inv[0][0] = new InvItem(12, 0, 0);
    realx = (double) m.length / 2 + ((double) x / m.blockSize);
    realy = m.groundlvlmap[(int) realx] - 5;
    y = -(int) (realy - ((double) m.height / 2)) * m.blockSize;
    hp = 100;
    maxhp = 100;


  }

  public Character(String fileloc) {

  }

  public void draw(Graphics g) {
    g.setColor(Color.RED);
    g.fillRect(WIDTH / 2, HEIGHT / 2 - 25, width, height);
    g.drawString(x + ", " + y, 20, HEIGHT - 30);
    g.drawString(realx + ", " + realy, 20, HEIGHT - 45);
    for (int invnum = 0; invnum < invLength - 1; invnum++) {
      if (!invOpen) {
        if (invnum == selectedItem) {
          //System.out.println("drawing "+invnum+"as selectedItem");
          g.setColor(invColor);
          g.fillRect(20 + (60 * invnum), 50, 40, 40);
        } else {
          g.setColor(invColor);
          g.drawRect(20 + (60 * invnum), 50, 40, 40);
        }
        inv[0][invnum].draw(g);
      } else {
        for (int invy = 0; invy < invHeight - 1; invy++) {
          if (invnum == selectedItem && invy == 0) {
            //System.out.println("drawing "+invnum+"as selectedItem");
            g.setColor(invColor);
            g.fillRect(20 + (60 * invnum), 50, 40, 40);
          } else {
            g.setColor(invColor);
            g.drawRect(20 + (60 * invnum), 50 + (60 * invy), 40, 40);
          }
          inv[invy][invnum].draw(g);
        }
        re.draw(g);

      }
    }
    g.setColor(Color.WHITE);
    g.fillRect(20, 15, maxhp, 20);
    g.setColor(Color.red);
    g.fillRect(20, 15, hp, 20);
  }


  public void move(Map map) {
    re.updateAvailable(inv, invLength, invHeight);
    selectedItemID = inv[0][selectedItem].getItemID();
    if (firstLaunch) {
      mapHeight = map.height;
      mapLength = map.length;
    }
    timer++;
    if (timer == 50) {
      timer = 0;
    }
    if (timer == 19) {
      if (hp < maxhp) {
        hp++;
      }
      System.out.println(
          "is moving left: " + isMovingLeft + " is moving right: " + isMovingRight + " x velocity: "
              + xvel + " y velocity: " + yvel);
    }
    if (isMovingLeft) {
      if (xvel > -10) {
        xvel--;
      }
    }
    if (isMovingRight) {
      if (xvel < 10) {
        xvel++;
      }
    }
    if (!isMovingLeft && !isMovingRight) {
      xvel = (int) (xvel * friction);
    }
    if (yvel > -10) {
      yvel--;
    }
    int futureX = x + xvel;
    double futureRealX = (double) map.length / 2 + ((double) futureX / map.blockSize);
    int futureY = y + yvel;
    double futureRealY = (double) map.height / 2 - ((double) futureY / map.blockSize);
    if (map.isNotStandable((int) futureRealX, (int) futureRealY + 1) && map
        .isNotStandable((int) (futureRealX + .4), (int) futureRealY + 1) && yvel < 0) {
      if (!isClimbing) {
        y += yvel;
        //realy = (double) map.height / 2 - ((double) y / map.blockSize);
      } else {
        if (map.isNotClimbable((int) futureRealX, (int) futureRealY + 1) && map
            .isNotClimbable((int) (futureRealX + .4), (int) futureRealY + 1)) {
          y += yvel;
          //realy = (double) map.height / 2 - ((double) y / map.blockSize);
        } else {
          inJump = false;
          //System.out.println("in jump set to false");
        }
      }
    } else if (yvel > 0) {
      if (futureRealY > 0 && futureRealY < map.height - 1) {
        if (map.isPassable((int) futureRealX, (int) futureRealY)) {
          y += yvel;
        }
      }
    } else if (yvel < 0) {
      inJump = false;
      //System.out.println("in jump set to false");
    }
    if ((xvel < 0 && map.isPassable((int) (futureRealX), ((int) futureRealY))) || (xvel > 0 && map
        .isPassable((int) (futureRealX + .4), ((int) futureRealY)))) {
      if (futureRealX > 0 && futureRealX < map.length - 1) {
        x += xvel;
      }
    }
    realy = (double) map.height / 2 - ((double) y / map.blockSize);
    realx = (double) map.length / 2 + ((double) x / map.blockSize);
  }


  public void jump() {
    if (!inJump) {
      System.out.println("jump");
      yvel = 20;
      inJump = true;
    }
  }


  public void addToInv(int itemID) {
    boolean hasItem = false;
    for (int invy = 0; invy < invHeight - 1; invy++) {
      for (int invx = 0; invx < invLength - 1; invx++) {
        if (inv[invy][invx].itemID == itemID) {
          inv[invy][invx].changeAmt(true);
          hasItem = true;
          invx = invLength;
          invy = invHeight;
        }
      }
    }
    if (!hasItem) {
      for (int invy = 0; invy < invHeight - 1; invy++) {
        for (int invx = 0; invx < invLength - 1; invx++) {
          if (!inv[invy][invx].exists) {
            inv[invy][invx] = new InvItem(itemID, invx, invy);
            invx = invLength;
            invy = invHeight;
          }
        }
      }
    }
  }


  public void setMovingLeft(boolean input) {
    isMovingLeft = input;
  }

  public void setMovingRight(boolean input) {
    isMovingRight = input;
  }

  public void setClimbing(boolean input) {
    isClimbing = input;
  }

  public int getY() {
    return y;
  }

  public int getX() {
    return x;
  }

  public double getRealx() {
    return realx;
  }

  public double getRealy() {
    return realy;
  }


  public int getLoadXMin() {
    int loadx = (int) realx - 15;
    if (loadx < 0) {
      loadx = 0;
    }
    return loadx;
  }


  public int getLoadXMax() {
    int loadx = (int) realx + 15;
    if (loadx > mapLength - 1) {
      loadx = mapLength - 1;
    }
    return loadx;
  }


  public int getLoadYMin() {
    int loady = (int) realy - 10;
    if (loady < 0) {
      loady = 0;
    }
    return loady;
  }


  public int getLoadYMax() {
    int loady = (int) realy + 10;
    if (loady > mapHeight - 1) {
      loady = mapHeight - 1;
    }
    return loady;
  }


  public void setSelectedItem(int newnum) {
    System.out.println("character class selected item= " + newnum);
    selectedItem = newnum;
  }


  public void useSelectedItem() {
    if (inv[0][selectedItem].isBlock) {
      inv[0][selectedItem].changeAmt(false);
    }
  }


  public boolean selectedItemIsBlock() {
    if (inv[0][selectedItem].isBlock) {
      return true;
    } else {
      return false;
    }
  }


  public void openCloseInv() {
    if (invOpen) {
      System.out.println("setting inv closed");
      invOpen = false;
    } else {
      System.out.println("setting inv open");
      invOpen = true;
    }
  }


  public boolean isInvOpen() {
    return invOpen;
  }

  public void craftSelected() {
    re.craftSelected(inv, invLength, invHeight);
  }

  public void changeSelected(boolean isInc) {
    re.changeSelected(isInc);
  }
}