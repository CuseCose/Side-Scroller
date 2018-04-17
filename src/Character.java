import java.awt.*;
import java.util.concurrent.Future;

public class Character implements Sprite {

    private Item[][] inv;
    private int x, y;
    private double realx;
    private double realy;

    private int height=80, width=20;
    private boolean isMovingRight;
    private boolean isMovingLeft;
    private int xvel=0, yvel=0;
    private double friction=.9;
    private double gravity=.9;
    private boolean isClimbing=false;
    private int invLength=10;
    private int invHeight=2;

    private int timer=0;

    private boolean isJumping=true;
    private boolean firstLaunch=true;
    public int mapLength,mapHeight;

    int selectedItem=0;
    int selectedItemID;

    public Character(){
        x=0;
        y=0;
        inv=new Item[invHeight][invLength];
        firstLaunch=true;
        for (int invy=0;invy<invHeight-1;invy++){
            for (int invx=0;invx<invLength-1;invx++){
                inv[y][x]=new Item(0,x,y);
            }
        }
        inv[0][0]=new Item(12,0,0);




    }

    public Character(String fileloc){

    }

    public void draw(Graphics g) {
        g.setColor(Color.RED);
        g.fillRect(WIDTH/2, HEIGHT/2-25, width, height);
        g.drawString(x+", "+y,WIDTH/2-5, HEIGHT/2-30);
        g.drawString(realx+", "+realy,WIDTH/2-5, HEIGHT/2-45);
        //g.drawString((realx+5)+", "+realy,WIDTH/2-5, HEIGHT/2-60);

    }


    public void move(Map map) {
        selectedItemID=inv[0][selectedItem].getItemID();
        if (firstLaunch){
            mapHeight=map.height;
            mapLength=map.length;
        }
        timer++;
        if (timer==50){timer=0;}
        if (timer==19){ System.out.println("is moving left: "+isMovingLeft+" is moving right: "+isMovingRight+" x velocity: "+xvel+" y velocity: "+yvel);}
        if (isMovingLeft){
            if(xvel>-10){
                xvel--;
            }
        }
        if (isMovingRight){
            if(xvel<10){
                xvel++;
            }
        }
        if (!isMovingLeft&&!isMovingRight){
            xvel=(int)(xvel*friction);
        }
        if (yvel>-10){
            yvel--;
        }
        int futureX=x+xvel;
        double futureRealX=(double)map.length/2+((double)futureX/map.blockSize);
        int futureY=y+yvel;
        double futureRealY=(double)map.height/2-((double)futureY/map.blockSize);
        if(map.isNotStandable((int)futureRealX,(int)futureRealY+1)&&map.isNotStandable((int)(futureRealX+.4),(int)futureRealY+1)&&yvel<0){
            if (!isClimbing) {
                y += yvel;
                realy = (double) map.height / 2 - ((double) y / map.blockSize);
            }else {
                if(map.isNotClimbable((int)futureRealX,(int)futureRealY+1)&&map.isNotClimbable((int)(futureRealX+.4),(int)futureRealY+1)&&yvel<0){
                    y += yvel;
                    realy = (double) map.height / 2 - ((double) y / map.blockSize);
                }
            }
        } else if (yvel > 0) {
            if(futureRealY>0&&futureRealY<map.height-1) {
                y += yvel;
            }
        }
        if((xvel<0&&map.isPassable((int)(futureRealX),((int)futureRealY)))||(xvel>0&&map.isPassable((int)(futureRealX+.4),((int)futureRealY)))) {
            if (futureRealX>0&&futureRealX<map.length-1) {
                realx = (double) map.length / 2 + ((double) x / map.blockSize);
                x += xvel;
            }
        }






    }

    public void jump(){
        System.out.println("jump");
        yvel=20;
    }

    public void setMovingLeft(boolean input){isMovingLeft=input;}
    public void setMovingRight(boolean input){isMovingRight=input;}
    public void setClimbing(boolean input){isClimbing=input;}
    public int getY() {
        return y;
    }
    public int getX() {
        return x;
    }

    public double getRealx() { return realx; }
    public double getRealy() { return realy; }

    public int getLoadXMin(){
        int loadx=(int)realx-50;
        if (loadx<0){
            loadx=0;
        }
        return loadx;
    }
    public int getLoadXMax(){
        int loadx=(int) realx+50;
        if (loadx>mapLength-1){
            loadx=mapLength-1;
        }
        return loadx;
    }
    public int getLoadYMin(){
        int loady=(int)realy-50;
        if (loady<0){
            loady=0;
        }
        return loady;
    }
    public int getLoadYMax(){
        int loady=(int)realy+50;
        if (loady>mapHeight-1){
            loady=mapHeight-1;
        }
        return loady;
    }

}