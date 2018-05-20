import java.awt.*;
import java.util.ArrayList;

public class Map implements Sprite {

    int[] biomemap;//biome map
    Block[][] blocks;//block block map
    int[] groundlvlmap;//ground level map
    private Character p1;
    private Background bak;
    int length, height;
    int blockSize;
    ArrayList<NPC> en=new ArrayList<NPC>();
    MapGen mg;
    DarknessOverlay[][] shading;
    int[][] lightSources;


    public Map(int size){
        mg=new MapGen(size);
        System.out.println("mapgen done");
        length=mg.getLength();
        height=mg.getHeight();
        System.out.println("map dimensions:"+length+" x "+height);
        biomemap=new int[length];
        groundlvlmap=new int[length];
        blocks=new Block[length][height];
        lightSources=new int[length][height];
        for (int x=0; x<length;x++){
            groundlvlmap[x]=mg.getGroundlvlmap()[x];
            biomemap[x]=mg.getBiomemap()[x];
            for (int y=0; y<height; y++){
                lightSources[x][y]=0;
                shading[x][y]=new DarknessOverlay(x, y);
                blocks[x][y]=new Block(x, y, mg.getMap()[x][y]);
                //System.out.println("Making block "+x+". "+y);
            }
        }

        blockSize=BLOCKSIZE;
        p1=new Character(this);
        bak=new Background();
    }

    public Map(String fileloc, int type){//type indicates if your using a old map and new char, or new map and old char

    }


    public Map(String fileloc, String charfile){

    }


    public void draw(Graphics g) {
        bak.draw(g);
        for(int loadx=p1.getLoadXMin();loadx<p1.getLoadXMax();loadx++){
            for(int loady=p1.getLoadYMin(); loady<p1.getLoadYMax(); loady++){
                blocks[loadx][loady].draw(g,WIDTH/2+((loadx-(length/2))*blockSize)-p1.getX(), HEIGHT/2+((loady-(height/2))*blockSize)+p1.getY());
            }
        }
        for (int i=0; i<en.size(); i++){
            en.get(i).draw(g, p1.getX(), p1.getY(), length);
        }
        p1.draw(g);

    }


    public void leftClick(int x, int y){
        System.out.println("block clicked");
        int clickx=getRealMouseX(x);
        int clicky=getRealMouseY(y);
        if (p1.selectedItemID==12){
            System.out.println("breaking block");
            if (blocks[clickx][clicky].exists) {
                p1.addToInv(blocks[clickx][clicky].getitemID());
                blocks[clickx][clicky] = new Block(getRealMouseX(x), getRealMouseY(y), 0);
            }
        }else if (p1.selectedItemIsBlock()){
            if (blocks[clickx][clicky].getitemID()==0) {
                blocks[clickx][clicky] = new Block(getRealMouseX(x), getRealMouseY(y), p1.selectedItemID);
                p1.useSelectedItem();
            }
        }
    }


    public boolean isNotStandable(int x, int y){
        if ( blocks[x][y].isNotStandable()){ return true;
        }else { return false; }
    }


    public boolean isPassable(int x, int y){
        if ( blocks[x][y].isPassable()){ return true;
        }else { return false; }
    }


    public boolean isNotClimbable(int x, int y){
        if ( blocks[x][y].isNotClimbable()){ return true;
        }else { return false; }
    }


    public int getRealMouseX(int x){
        int realx=(int)(p1.getRealx()-((WIDTH/2)/blockSize)+(x/blockSize));
        return realx;
    }


    public int getRealMouseY(int y){
        int realy=(int)(p1.getRealy()-((HEIGHT/2)/blockSize)+(y/blockSize)+1);
        return realy;
    }

    int timer=0;

    public void move() {
        updateSunLight();
        p1.move(this);
        for (int i=0; i<en.size();i++){
            en.get(i).move(this);
        }
        bak.move(p1.getRealy(), p1.getRealx(), groundlvlmap, height, biomemap);
        if (timer<10){
            timer++;
        }else {
            timer=0;
        }
        if (timer==5) {
            if (!bak.isDay()) {
                int dice = (int) (Math.random() * 100);
                int dice2 = (int) (Math.random() * 100);
                if (dice < 10 && dice2 < 20) {
                    en.add(new NPC(this));
                    System.out.println("spawned enemy");
                }
            }else {
                if (en.size()>0) {
                    int dice = (int) (Math.random() * 100);
                    if (dice < 10) {
                        int dice3 = (int) (Math.random() * en.size());
                        System.out.println("trying to remove enemy");
                        if (en.get(dice3).isOffScreen(this)){
                            en.remove(dice3);
                            System.out.println("removed");
                        }
                    }
                }
            }
        }
    }

    public void updateSunLight(){
        double time=bak.getTime();
        int sunlightlvl;
        if (time>12){
            sunlightlvl=0;
        }else {
            if (time<6){
                sunlightlvl=(int)time;
            }else {
                sunlightlvl=(int)(12-time);
            }
        }
        for (int x=p1.getLoadXMin(); x<p1.getLoadXMax();x++){
            for (int y=p1.getLoadYMin(); y<p1.getLoadYMax();y++){
                if (y<groundlvlmap[x]) {
                    lightSources[x][y]=sunlightlvl;
                }
            }
        }

    }

    public void setSelectedItem(int itemnum){p1.setSelectedItem(itemnum);}
    public void openCloseInv(){p1.openCloseInv();}
    public void changeSelected(boolean isInc){ p1.changeSelected(isInc); }
    public boolean isInvOpen(){return p1.isInvOpen();}
    public void craft(){p1.craftSelected();}
    public void setMovingRight(boolean input){p1.setMovingRight(input);}
    public void setMovingLeft(boolean input){p1.setMovingLeft(input);}
    public void setClimbing(boolean input){p1.setClimbing(input);}
    public void jump(){p1.jump();}
    public int getY() { return (int)p1.getRealy(); }
    public int getX() {
        return (int)p1.getRealx();
    }
    public int[] getGroundlvlmap() { return groundlvlmap; }
}