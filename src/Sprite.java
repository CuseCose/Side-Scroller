import java.awt.Graphics;

public interface Sprite {
    int WIDTH=1280, HEIGHT=900, BLOCKSIZE=50;
    void draw(Graphics g);
    void move();
    int getY();
    int getX();
}


