import java.awt.*;
import java.util.ArrayList;

public class Map implements Sprite {

    int[][] map;//block map
    int[] biomemap;//biome map

    private Character p1;
    int length, height;
    int blockSize;
    int loadsizex=10;
    int loadsizey=5;
    Color dirt=new Color(102,51,0);
    Color bark=new Color(153,102,51);
    Color leaves=new Color(0, 153, 51);


    public Map(int size){
        switch (size){
            case 1:
                length=1000;
                height=100;
                blockSize=50;
                break;
            case 2:
                length=2000;
                height=200;
                blockSize=40;
                break;
            case 3:
                length=3000;
                height=300;
                blockSize=30;
                break;
        }
        System.out.println("generating map");
        biomemap=new int[length];
        map=new int[length][height];
        int cground=height/2;
        for (int x=0; x<length;x++){
            for (int y=0; y<height; y++){
                if (y>=cground){
                    if (y<cground+2){
                        map[x][y]=1;
                    }else {
                        if (y<cground+(height/10)*2){
                            map[x][y]=4;
                        }else {
                            if (Math.random() * 100 < 20) {
                                map[x][y] = 3;
                            } else {
                                map[x][y] = 2;
                            }
                        }
                    }
                }
            }
            int randChangeSize=(int)(Math.random()*100);
            if (randChangeSize<30){
                cground++;
            }else if (randChangeSize<60){
                cground--;
            }


        }
        System.out.println("generating biomes");
        int cbiome=0;

        for (int x=0;x<length-1;x++){
            biomemap[x]=cbiome;
            if (Math.random()*100<2){
                System.out.println("change biome");
                int newbiome=(int)(Math.random()*100);
                if (newbiome<40){
                    cbiome=1;
                }else if (newbiome<80){
                    cbiome=2;
                }else {
                    cbiome=0;
                }

            }
        }

        boolean adjacentHasTree=false;
        for (int x=0; x<length-1; x++) {

            if (biomemap[x] == 0) {
                for (int y=0; y<height-1; y++){
                    if (map[x][y]==1){
                        map[x][y]=8;
                    }
                }
            }else if (biomemap[x] == 1) {
                if (Math.random() * 100 < 20&&!adjacentHasTree) {
                    adjacentHasTree=true;
                    System.out.println("generating tree at" + x);
                    boolean hasGrass = false;
                    int groundLevel = 0;
                    for (int y = 0; y < height; y++) {
                        if (map[x][y] == 1 || map[x][y] == 4) {
                            hasGrass = true;
                            groundLevel = y;
                            System.out.println("groundlevel: " + groundLevel);
                            break;
                        }
                    }
                    if (hasGrass) {
                        int treeHeight = (int) (15 * Math.random()) + 3;
                        for (int y = groundLevel - 1; y >= groundLevel - treeHeight; y--) {
                            System.out.println("putting bark at: (" + x + ", " + y + ")");
                            if (y >= 0)
                                map[x][y] = 5;
                        }
                        for (int treex = x - 2; treex < x + 3; treex++) {
                            for (int treey = groundLevel - treeHeight - 2; treey < groundLevel - treeHeight + 2; treey++) {
                                if (!(treex == x && treey >= groundLevel - treeHeight)) {
                                    if (map[treex][treey] == 0) {
                                        map[treex][treey] = 6;
                                    }
                                }
                            }
                        }
                    }
                    x++;
                }else {
                    adjacentHasTree=false;
                }
            }else if(biomemap[x]==2){
                if (Math.random() * 100 < 20&&!adjacentHasTree) {
                    adjacentHasTree=true;
                    System.out.println("generating tree at" + x);
                    boolean hasGrass = false;
                    int groundLevel = 0;
                    for (int y = 0; y < height; y++) {
                        if (map[x][y] == 1 || map[x][y] == 4) {
                            hasGrass = true;
                            groundLevel = y;
                            System.out.println("groundlevel: " + groundLevel);
                            break;
                        }
                    }
                    if (hasGrass) {
                        int treeHeight = (int) (15 * Math.random()) + 3;
                        for (int y = groundLevel - 1; y >= groundLevel - treeHeight; y--) {
                            System.out.println("putting bark at: (" + x + ", " + y + ")");
                            if (y >= 0)
                                map[x][y] = 5;
                        }
                        for (int treex = x - 2; treex < x + 3; treex++) {
                            for (int treey = groundLevel - treeHeight - 2; treey < groundLevel - treeHeight + 2; treey++) {
                                if (!(treex == x && treey >= groundLevel - treeHeight)) {
                                    if (map[treex][treey] == 0) {
                                        map[treex][treey] = 10;
                                    }
                                }
                            }
                        }
                    }

                    //x++;
                }else {
                    adjacentHasTree=false;
                }
                for (int y=0; y<height-1; y++){
                    if (map[x][y]==1){
                        map[x][y]=9;
                    }
                }
            }
        }

        System.out.println("generating clouds");
        for (int x=3; x<length-10; x++){
            if (Math.random()*100<20){
                int cloudheight=2+(int)(Math.random()*10);
                int cloudsize=5+(int)(Math.random()*10);
                for(int cloudx=x; cloudx<x+cloudsize;cloudx++){
                    for (int cloudy=cloudheight;cloudy<cloudheight+(cloudsize/2); cloudy++){
                        if (cloudx>0&&cloudx<length-1&&cloudy>0&&cloudy<height-1){
                            if (map[cloudx][cloudy]==0) {
                                map[cloudx][cloudy] = 7;
                            }
                        }
                    }
                }
                x+=20;
            }
        }

        p1=new Character();
        for (int y=0; y<height; y++){
            for (int x=0; x<length; x++){
                System.out.print(map[x][y]+" ");
            }
            System.out.println("");
        }
    }

    public Map(String fileloc, int type){//type indicates if your using a old map and new char, or new map and old char

    }

    public Map(String fileloc, String charfile){

    }

    public void draw(Graphics g) {

        for(int loadx=p1.getLoadXMin();loadx<p1.getLoadXMax();loadx++){
            for(int loady=p1.getLoadYMin(); loady<p1.getLoadYMax(); loady++){
                switch (map[loadx][loady]){
                    case 0://empty
                        g.setColor(Color.cyan);
                        break;
                    case 1://grass
                        g.setColor(Color.green);
                        break;
                    case 2://grey rock
                        g.setColor(Color.GRAY);
                        break;
                    case 3://black rock
                        g.setColor(Color.black);
                        break;
                    case 4://dirt
                        g.setColor(dirt);
                        break;
                    case 5://wood
                        g.setColor(bark);
                        break;
                    case 6://leaves
                        g.setColor(leaves);
                        break;
                    case 7://clouds
                        g.setColor(Color.white);
                        break;
                    case 8://sand
                        g.setColor(Color.yellow);
                        break;
                    case 9://snow
                        g.setColor(Color.WHITE);
                        break;
                    case 10://snowy leaves
                        g.setColor(Color.white);
                }

                g.fillRect(WIDTH/2+((loadx-(length/2))*blockSize)-p1.getX(), HEIGHT/2+((loady-(height/2))*blockSize)+p1.getY(), blockSize, blockSize);
                //g.setColor(Color.black);
                //g.drawString("("+loadx+", "+loady+")",WIDTH/2+((loadx-(length/2))*blockSize)-p1.getX()+7, HEIGHT/2+((loady-(height/2))*blockSize)+p1.getY()+30);
            }
        }
        p1.draw(g);


    }

    public boolean isNotStandable(int x, int y){
        //System.out.println("ispassable?");
        switch (map[x][y]){
            case 0:
                return true;
            case 1:
                return false;
            case 2:
                return false;
            case 3:
                return false;
            case 4:
                return false;
            case 5:
                return true;
            case 6:
                return true;
            case 7:
                return false;
            case 8:
                return false;
            case 9:
                return false;
            case 10:
                return true;
            default:
                return false;
        }
    }
    public boolean isPassable(int x, int y){
        //System.out.println("ispassable?");
        switch (map[x][y]){
            case 0:
                return true;
            case 1:
                return false;
            case 2:
                return false;
            case 3:
                return false;
            case 4:
                return false;
            case 5:
                return true;
            case 6:
                return true;
            case 7:
                return true;
            case 8:
                return false;
            case 9:
                return false;
            case 10:
                return true;
            default:
                return false;
        }
    }
    public boolean isNotClimbable(int x, int y){
        //System.out.println("ispassable?");
        switch (map[x][y]){
            case 0:
                return true;
            case 1:
                return false;
            case 2:
                return false;
            case 3:
                return false;
            case 4:
                return false;
            case 5:
                return false;
            case 6:
                return false;
            case 7:
                return false;
            case 8:
                return false;
            case 9:
                return false;
            case 10:
                return false;
            default:
                return false;
        }
    }


    public void move() {
        p1.move(this);
    }

    public void setMovingRight(boolean input){p1.setMovingRight(input);}
    public void setMovingLeft(boolean input){p1.setMovingLeft(input);}
    public void setClimbing(boolean input){p1.setClimbing(input);}
    public void jump(){p1.jump();}

    public int getY() {
        return 0;
    }
    public int getX() {
        return 0;
    }
}