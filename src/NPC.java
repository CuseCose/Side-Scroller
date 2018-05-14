import java.awt.*;

public class NPC implements Sprite {
    private int x, y;
    private double realx;
    private double realy;

    private int hp;
    private int maxhp;

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
    int selectedItem=0;
    int selectedItemID;
    Color invColor=new Color(204, 204, 179);
    private boolean aggro=false;


    public NPC(Map m){
        int relativespawnloc=(int)(Math.random()*4000-2000);
        if (relativespawnloc>0){
            relativespawnloc+=1000;
        }else {
            relativespawnloc-=1000;
        }
        x=((m.getX()-(m.length/2))*BLOCKSIZE)+relativespawnloc;
        y=2000;
        firstLaunch=true;
        realx = (double) m.length / 2 + ((double) x / m.blockSize);
        realy=m.groundlvlmap[(int)realx]-5;
        y=-(int)(realy-((double)m.height/2))*m.blockSize;
        hp=100;
        maxhp=100;

    }

    public boolean isOffScreen(Map m){
        if ((m.getX()-realx)*BLOCKSIZE>1000||(m.getX()-realx)*BLOCKSIZE<-1000){
            return true;
        }else {
            return false;
        }
    }


    public void draw(Graphics g) {
    }

    public void draw(Graphics g, double p1x, double p1y, int mapLength) {
        if (aggro) {
            g.setColor(Color.red);
        }else {
            g.setColor(Color.BLUE);
        }
        //System.out.println("drawing npc, x="+x+"("+realx+") y="+y+"("+realy+")");
        double drawx=(x-p1x)+WIDTH/2;
        double drawy=(p1y-y)+HEIGHT/2-25;
        g.fillRect((int)drawx, (int)drawy, width, height);
        g.drawString(x+", "+y,(int)drawx-5, (int)drawy-35);
        g.drawString(realx+", "+realy,(int)drawx-5, (int)drawy-50);
        g.setColor(Color.WHITE);
        g.fillRect((int)drawx-(maxhp/2)+(width/2), (int)drawy-30, maxhp, 20);
        g.setColor(Color.red);
        g.fillRect((int)drawx-(maxhp/2)+(width/2), (int)drawy-30, hp, 20);
    }

    public void move(Map map) {
        if (firstLaunch){
            mapHeight=map.height;
            mapLength=map.length;
            realx = (double) map.length / 2 + ((double) x / map.blockSize);
            aggro=false;
            firstLaunch=false;
        }
        if(!aggro&&realx-map.getX()<6&&realx-map.getX()>-6){
            aggro=true;
        }
        if(aggro){
            if(map.getX()>realx){
                isMovingRight=true;
                isMovingLeft=false;
            }else{
                isMovingRight=false;
                isMovingLeft=true;
            }
            if((int)(Math.random()*100)<3){
                jump();
            }
            if (realx-map.getX()>20||realx-map.getX()<-20){
                aggro=false;
            }
        }else{
            int dice=(int)(Math.random()*100);
            if(dice<3){
                jump();
            }else if(dice<6) {
                isMovingRight = false;
                isMovingLeft = true;
            }else if(dice<9){
                isMovingRight=true;
                isMovingLeft=false;
            }else if(dice<12){
                isMovingRight=false;
                isMovingLeft=false;
            }
        }
        timer++;
        if (timer==50){timer=0;}
        if (timer==49){
            int dice=(int)(Math.random()*100);
            System.out.println("npc is moving left: "+isMovingLeft+" is moving right: "+isMovingRight+" x velocity: "+xvel+" y velocity: "+yvel);
        }
        if (isMovingLeft){
            if(xvel>-5){
                xvel--;
            }
        }
        if (isMovingRight){
            if(xvel<5){
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
                //realy = (double) map.height / 2 - ((double) y / map.blockSize);
            }else {
                if(map.isNotClimbable((int)futureRealX,(int)futureRealY+1)&&map.isNotClimbable((int)(futureRealX+.4),(int)futureRealY+1)){
                    y += yvel;
                    //realy = (double) map.height / 2 - ((double) y / map.blockSize);
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
                //realx = (double) map.length / 2 + ((double) x / map.blockSize);
                x += xvel;
            }
        }
        realy = (double) map.height / 2 - ((double) y / map.blockSize);
        realx = (double) map.length / 2 + ((double) x / map.blockSize);
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
