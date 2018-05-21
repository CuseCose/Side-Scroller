import java.awt.*;

public class Block extends Item implements Sprite {

    int x,y;

    public Block(int x, int y, int type){
        super(type);
        this.x=x;
        this.y=y;

    }


    public void draw(Graphics g) { }

    public void draw(Graphics g, int xloc, int yloc) {
        if (exists) {
            //g.drawImage(super.img, xloc, yloc, BLOCKSIZE, BLOCKSIZE, null);
            g.drawImage(super.img, xloc, yloc, BLOCKSIZE, BLOCKSIZE, null);
        }
    }






    public void move(Map map){ }

    public boolean isNotStandable(){return isNotStandable;}
    public boolean isPassable() { return isPassable; }
    public boolean isNotClimbable() { return isNotClimbable; }
    public int getitemID() {
        return itemID;
    }
    public int getY() {
        return x;
    }
    public int getX() {
        return y;
    }
}
