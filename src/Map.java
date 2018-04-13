import java.awt.*;

public class Map implements Sprite {

    int[][] map;//block map
    int[][] biomemap;//biome map

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
                length=50;
                height=30;
                blockSize=50;
                break;
            case 2:
                length=100;
                height=60;
                blockSize=40;
                break;
            case 3:
                length=200;
                height=80;
                blockSize=30;
                break;
        }
        System.out.println("generating map");
        map=new int[length][height];
        int cground=height/2;
        for (int x=0; x<length;x++){
            for (int y=0; y<height; y++){
                if (y>=cground){
                    if (y<cground+(height/10)){
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
        int currentBiome=1;
        if (currentBiome==1){
            for (int x=3; x<length-3; x++){
                if (Math.random()*100<20){
                    System.out.println("generating tree at"+x);
                    boolean hasGrass=false;
                    int groundLevel=0;
                    for (int y=0; y<height; y++){
                        if (map[x][y]==1||map[x][y]==4){
                            hasGrass=true;
                            groundLevel=y;
                            System.out.println("groundlevel: "+groundLevel);
                            break;
                        }
                    }
                    if(hasGrass) {
                        int treeHeight = (int) (10 * Math.random())+1;
                        for (int y = groundLevel-1; y >= groundLevel - treeHeight; y--) {
                            System.out.println("putting bark at: ("+x+", "+y+")");
                            map[x][y] = 5;
                        }
                        for (int treex = x - 2; treex < x + 3; treex++) {
                            for (int treey = groundLevel - treeHeight - 2; treey < groundLevel - treeHeight + 2; treey++) {
                                if (!(treex == x && treey >= groundLevel - treeHeight)) {
                                    map[treex][treey] = 6;
                                }
                            }
                        }
                    }
                    x++;


                }
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
        for(int loadx=0;loadx<length;loadx++){
            for(int loady=0; loady<height; loady++){
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

                }

                g.fillRect(WIDTH/2+((loadx-(length/2))*blockSize)-p1.getX(), HEIGHT/2+((loady-(height/2))*blockSize)+p1.getY(), blockSize, blockSize);
                //g.setColor(Color.black);
                //g.drawString("("+loadx+", "+loady+")",WIDTH/2+((loadx-(length/2))*blockSize)-p1.getX()+7, HEIGHT/2+((loady-(height/2))*blockSize)+p1.getY()+30);
            }
        }
        p1.draw(g);


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
                return false;
            case 6:
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
    public void jump(){p1.jump();}

    public int getY() {
        return 0;
    }
    public int getX() {
        return 0;
    }
}
