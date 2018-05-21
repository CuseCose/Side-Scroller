import java.awt.*;

public class DarknessOverlay implements Sprite{

    int x, y;
    double lightlvl;
    Image img;
    boolean isSky;
    double dark;

    public DarknessOverlay(int x, int y){
        this.x=x;
        this.y=y;
        img=tk.getImage(DOCPATH+"10x2opacity.png");
    }



    public void update(Map m){
        lightlvl=0;

    }

    public void update(double dar, boolean isSky){
        dark=dar;
        this.isSky=isSky;
    }

    public void update(double[][] lightSrcMap, int length, int height){
        lightlvl=0;
        if (lightSrcMap[x][y]>0){
            lightlvl+=lightSrcMap[x][y];
        }

        for (double mapx=-10;mapx<10;mapx++){
            for (double mapy=-10; mapy<10;mapy++){
                if (x+mapx>0&&x+mapx<length&&y+mapy>0&&y+mapy<height) {
                    if (lightSrcMap[(int)(x + mapx)][(int)(y + mapy)] != 0) {
                        if (mapx != 0 && mapy != 0) {
                            lightlvl += Math.abs(lightSrcMap[(int)(x + mapx)][(int)(y + mapy)] / ((double) mapx + mapy));
                        } else {
                            if (mapx != 0) {
                                lightlvl += Math.abs(lightSrcMap[(int)(x + mapx)][(int)(y + mapy)]  / mapx);
                            } else {
                                lightlvl += Math.abs(lightSrcMap[(int)(x + mapx)][(int)(y + mapy)] / mapy);
                            }
                        }
                    }
                }
            }
        }


    }

    public void draw(Graphics g, int xloc, int yloc, double time) {
        if(isSky&&time>3&&time<9){
        }else {
            if (dark >= 12) {
                g.setColor(Color.BLACK);
                g.fillRect(xloc, yloc, BLOCKSIZE, BLOCKSIZE);
            } else {
                for (int i = 0; i < dark; i++) {
                    g.drawImage(img, xloc, yloc, BLOCKSIZE, BLOCKSIZE, null);
                }
            }
        }
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
