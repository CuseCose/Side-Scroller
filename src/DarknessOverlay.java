import java.awt.*;

public class DarknessOverlay implements Sprite{

    int x, y;
    int lightlvl;
    int sunLight;
    int overallLight;
    Image img;

    public DarknessOverlay(int x, int y){
        this.x=x;
        this.y=y;
        sunLight=0;
    }

    public void setSunLight(int sunlightlvl){
        sunLight=sunlightlvl;
    }

    public void update(Map m){
        lightlvl=0;

    }

    public void update(int[][] lightSrcMap){
        lightlvl=0;


        overallLight=lightlvl+sunLight;

    }

    public void draw(Graphics g, int xloc, int yloc) {
        g.drawImage(img, xloc, yloc, BLOCKSIZE, BLOCKSIZE, null);

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
