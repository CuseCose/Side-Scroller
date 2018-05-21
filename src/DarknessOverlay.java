import java.awt.*;

public class DarknessOverlay implements Sprite{

    int x, y;
    double lightlvl;
    Image img;


    public DarknessOverlay(int x, int y){
        this.x=x;
        this.y=y;
    }



    public void update(Map m){
        lightlvl=0;

    }

    public void update(double dark){
        img=tk.getImage(DOCPATH+"shading\\"+(int)((dark/9)*29)+".png");
    }



    public void draw(Graphics g, int xloc, int yloc) {
        g.drawImage(img,xloc,yloc,BLOCKSIZE,BLOCKSIZE, null);
    }



    public void draw(Graphics g) { }
    public int getY() {
        return 0;
    }
    public int getX() {
        return 0;
    }
}
