import java.awt.*;

public class Background implements Sprite{

    Color background;
    double time;
    int playerLayer;
    Color dirtLayer=new Color(103,51,50);
    Color caveLayer=new Color(153, 153, 102);

    public Background(){
        time=12;
        playerLayer=0;
    }



    public void draw(Graphics g) {
        switch (playerLayer){
            case 1:
                g.setColor(Color.black);
                break;
            case 0:
                g.setColor(Color.cyan);
                break;
            case -1:
                g.setColor(dirtLayer);
                break;
            case -2:
                g.setColor(caveLayer);
                break;
        }
        g.fillRect(0,0,WIDTH,HEIGHT);
    }

    public void move(double p1y, double p1x, int[] groundmap, int mapheight){
        if (p1y<=groundmap[(int)p1x]+5){
            if (p1y<mapheight*.15){
                playerLayer=1;//sky layer
            }else {
                playerLayer=0;//ground level
            }
        }else if (p1y>groundmap[(int)p1x]+15){
            if (p1y>groundmap[(int)p1x]+(mapheight/10)*2){
                playerLayer=-2;//cavern layer
            }else {
                playerLayer=-1;//dirt layer
            }
        }
    }


    public int getY() {
        return 0;
    }
    public int getX() {
        return 0;
    }
}
