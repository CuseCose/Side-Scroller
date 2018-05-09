import java.awt.Graphics;

public interface Sprite {
    final int WIDTH=1280, HEIGHT=900, BLOCKSIZE=50;
    public void draw(Graphics g);
    //public void move(Map map);
    public int getY();
    public int getX();
}


