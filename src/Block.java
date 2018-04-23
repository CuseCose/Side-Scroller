import java.awt.*;

public class Block implements Sprite {

    int x,y;
    int type;
    boolean isNotStandable, isNotClimbable, isPassable;
    Color color;
    boolean exists;
    boolean complex;
    int blockSize;

    public Block(){
        x=0;
        y=0;
        exists=false;
        complex=false;
        isNotStandable=true;
        isNotClimbable=true;
        isPassable=true;
        blockSize=50;
    }


    public Block(int x, int y, int type, int blockSize){
        this.x=x;
        this.y=y;
        this.type=type;
        exists=true;
        complex=false;
        this.blockSize=blockSize;
        switch (type){
            case 0://nothing
                exists=false;
                isNotStandable=true;
                isNotClimbable=true;
                isPassable=true;
                break;
            case 1://grass
                isNotStandable=false;
                isNotClimbable=false;
                isPassable=false;
                color=Color.green;
                break;
            case 2://grey rock
                isNotStandable=false;
                isNotClimbable=false;
                isPassable=false;
                color=Color.GRAY;
                break;
            case 3://black rock
                isNotStandable=false;
                isNotClimbable=false;
                isPassable=false;
                color=Color.BLACK;
                break;
            case 4://dirt
                isNotStandable=false;
                isNotClimbable=false;
                isPassable=false;
                color=new Color(102,51,0);
                break;
            case 5://wood
                isNotStandable=true;
                isNotClimbable=false;
                isPassable=true;
                color=new Color(153,102,51);
                break;
            case 6://leaves
                isNotStandable=true;
                isNotClimbable=false;
                isPassable=true;
                color=new Color(0, 153, 51);
                break;
            case 7://clouds
                isNotStandable=false;
                isNotClimbable=false;
                isPassable=true;
                color=Color.white;
                break;
            case 8://sand
                isNotStandable=false;
                isNotClimbable=false;
                isPassable=false;
                color=Color.yellow;
                break;
            case 9://snow
                isNotStandable=false;
                isNotClimbable=false;
                isPassable=false;
                color=Color.white;
                break;
            case 10://snowy leaves
                isNotStandable=true;
                isNotClimbable=false;
                isPassable=true;
                color=Color.white;
                break;
            case 11://cactus
                isNotStandable=true;
                isNotClimbable=true;
                isPassable=true;
                color=new Color(0,120,3);
                break;
        }


    }

    public void draw(Graphics g) {

    }

    public void draw(Graphics g, int xloc, int yloc) {
        if (!complex&&exists){
            g.setColor(color);
            g.fillRect(xloc,yloc,blockSize,blockSize);
        }
    }

    public boolean isNotStandable(){return isNotStandable;}
    public boolean isPassable() { return isPassable; }
    public boolean isNotClimbable() { return isNotClimbable; }

    public int getType() {
        return type;
    }

    public void move(Map map){

    }


    public int getY() {
        return x;
    }


    public int getX() {
        return y;
    }
}
