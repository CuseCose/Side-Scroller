import java.awt.*;

public class Character implements Sprite {

    private int[] inventory;
    private int x, y;
    private double realx;
    private double realy;

    private int height=50, width=20;
    private boolean isMovingRight;
    private boolean isMovingLeft;
    private int xvel=0, yvel=0;
    private double friction=.9;
    private double gravity=.9;

    private int timer=0;

    private boolean isJumping=true;

    public Character(){
        x=0;
        y=0;
        inventory=new int[10];

    }

    public Character(String fileloc){

    }

    public void draw(Graphics g) {
        g.setColor(Color.RED);
        g.fillRect(WIDTH/2, HEIGHT/2-25, width, height);
        g.drawString(x+", "+y,WIDTH/2-5, HEIGHT/2-30);
        g.drawString(realx+", "+realy,WIDTH/2-5, HEIGHT/2-45);
    }


    public void move(Map map) {
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
        if (yvel>-5){
            yvel--;
        }
        int futureX=x+xvel;
        double futureRealX=(double)map.length/2+((double)futureX/map.blockSize);
        int futureY=y+yvel;
        double futureRealY=(double)map.height/2-((double)futureY/map.blockSize);
        if(map.isPassable((int)futureRealX,(int)futureRealY+1)&&yvel<0){
            y+=yvel;
            realy=(double)map.height/2-((double)y/map.blockSize);
        } else if (yvel > 0) {
            y+=yvel;
        }
        realx=(double)map.length/2+((double)x/map.blockSize);
        x+=xvel;






    }

    public void jump(){
        System.out.println("jump");
        yvel=10;
    }

    public void setMovingLeft(boolean input){isMovingLeft=input;}
    public void setMovingRight(boolean input){isMovingRight=input;}
    public int getY() {
        return y;
    }
    public int getX() {
        return x;
    }



}
