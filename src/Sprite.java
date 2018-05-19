import java.awt.*;

public interface Sprite {
    String DOCPATH=(System.getenv("USERPROFILE"))+"\\Documents\\GitHub\\Side-Scroller\\textures\\";
    int WIDTH=1280, HEIGHT=900, BLOCKSIZE=10;
    void draw(Graphics g);
    int getY();
    int getX();
    Toolkit tk = Toolkit.getDefaultToolkit();
    Color invColor=new Color(204, 204, 179);
}


