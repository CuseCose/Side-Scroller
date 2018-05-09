import java.awt.*;

public class NPC implements Sprite {
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
    private int invLength=11;
    private int invHeight=3;

    private int timer=0;
    //private int jumpTimer=0;
    private boolean inJump=false;

    private boolean isJumping=true;
    private boolean firstLaunch=true;
    public int mapLength,mapHeight;
    boolean invOpen=false;
    private RecipeList re;
    int selectedItem=0;
    int selectedItemID;
    Color invColor=new Color(204, 204, 179);


    public NPC(){
        re=new RecipeList();
        x=0;
        y=2000;
        firstLaunch=true;

    }



    public void draw(Graphics g) {
    }

    public void draw(Graphics g, double p1x, double p1y, int mapLength) {
        g.setColor(Color.RED);
        System.out.println("drawing npc, x="+x+"("+realx+") y="+y+"("+realy+")");
        double drawx=((mapLength/2)*BLOCKSIZE)+x+p1x;
        double drawy=(realy*BLOCKSIZE)-(p1y*BLOCKSIZE)+(HEIGHT/2);
        g.fillRect((int)drawx, (int)drawy, width, height);
        /*g.drawString(x+", "+y,WIDTH/2-5, HEIGHT/2-30);
        g.drawString(realx+", "+realy,WIDTH/2-5, HEIGHT/2-45);*/
    }

    public void move(Map map) {
        if (firstLaunch){
            mapHeight=map.height;
            mapLength=map.length;
            realx = (double) map.length / 2 + ((double) x / map.blockSize);
        }
        timer++;
        if (timer==50){timer=0;}
        if (timer==19){ System.out.println("npc is moving left: "+isMovingLeft+" is moving right: "+isMovingRight+" x velocity: "+xvel+" y velocity: "+yvel);}
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
                if(map.isNotClimbable((int)futureRealX,(int)futureRealY+1)&&map.isNotClimbable((int)(futureRealX+.4),(int)futureRealY+1)){
                    y += yvel;
                    realy = (double) map.height / 2 - ((double) y / map.blockSize);
                }else{
                    inJump=false;
                    //System.out.println("in jump set to false");
                }
            }
        } else if (yvel > 0) {
            if(futureRealY>0&&futureRealY<map.height-1) {
                if (map.isPassable((int)futureRealX, (int)futureRealY)) {
                    y += yvel;
                }
            }
        }else if (yvel<0){
            inJump=false;
            //System.out.println("in jump set to false");
        }
        if((xvel<0&&map.isPassable((int)(futureRealX),((int)futureRealY)))||(xvel>0&&map.isPassable((int)(futureRealX+.4),((int)futureRealY)))) {
            if (futureRealX>0&&futureRealX<map.length-1) {
                realx = (double) map.length / 2 + ((double) x / map.blockSize);
                x += xvel;
            }
        }
    }


    public void jump(){
        if (!inJump) {
            System.out.println("jump");
            yvel = 20;
            inJump=true;
        }
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







}
