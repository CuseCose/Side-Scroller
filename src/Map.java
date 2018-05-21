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
    float[][] lightSources;
    double[][] light;
    float[][] dark;
    boolean[][] sky;


    /*
    public void applyLightRec(int currentx, int currenty, float lastLight) {
   if (!isValidPosition(currentx, currenty)) return;
   float newLight = lastLight-map.getLightBlockingAmmoutAt(currentx, currenty);
   if (newLight <= map.getLight(currentx, currenty)) return;

   map.setLight(currentx, currenty, newLight);

   applyLightRec(currentx+1, currenty, newLight);
   applyLightRec(currentx, currenty+1, newLight);
   applyLightRec(currentx-1, currenty, newLight);
   applyLightRec(currentx, currenty-1, newLight);
     */




    public Map(int size){
        mg=new MapGen(size);
        System.out.println("mapgen done");
        length=mg.getLength();
        height=mg.getHeight();
        System.out.println("map dimensions:"+length+" x "+height);
        biomemap=new int[length];
        groundlvlmap=new int[length];
        blocks=new Block[length][height];
        lightSources=new float[length][height];
        light=new double[length][height];
        dark=new float[length][height];
        shading=new DarknessOverlay[length][height];
        sky=new boolean[length][height];

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
                //g.setColor(Color.black);
                //g.drawString("src: "+(int)lightSources[loadx][loady]+"", WIDTH/2+((loadx-(length/2))*blockSize)-p1.getX()+5, HEIGHT/2+((loady-(height/2))*blockSize)+p1.getY()+10);
                //g.drawString("lit: "+(int)light[loadx][loady]+"", WIDTH/2+((loadx-(length/2))*blockSize)-p1.getX()+5, HEIGHT/2+((loady-(height/2))*blockSize)+p1.getY()+20);
            }
        }
        for (int i=0; i<en.size(); i++){
            en.get(i).draw(g, p1.getX(), p1.getY(), length);
        }
        p1.draw(g);

        for(int loadx=p1.getLoadXMin();loadx<p1.getLoadXMax();loadx++){
            for(int loady=p1.getLoadYMin(); loady<p1.getLoadYMax(); loady++){
                shading[loadx][loady].draw(g,WIDTH/2+((loadx-(length/2))*blockSize)-p1.getX(), HEIGHT/2+((loady-(height/2))*blockSize)+p1.getY());
            }
        }


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
        }else if (p1.selectedItemIsPlacable()){
            if (blocks[clickx][clicky].getitemID()==0) {
                blocks[clickx][clicky] = new Block(getRealMouseX(x), getRealMouseY(y), p1.selectedItemID);
                p1.useSelectedItem();
                timer=0;
                updateLight();
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
        if (timer==1) {
            updateLight();
        }
        p1.move(this);
        for (int i=0; i<en.size();i++){
            en.get(i).move(this);
        }
        bak.move(p1.getRealy(), p1.getRealx(), groundlvlmap, height, biomemap);
        if (timer<20){
            timer++;
        }else {
            timer=0;
        }
        if (timer==5) {
            if (!bak.isDay()) {
                int dice = (int) (Math.random() * 100);
                int dice2 = (int) (Math.random() * 100);
                if (dice < 10 && dice2 < 40) {
                    en.add(new NPC(this));
                    System.out.println("spawned enemy");
                }
            }else {
                if (en.size()>0) {
                    int dice = (int) (Math.random() * 100);
                    if (dice < /*10*/20) {
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



    public void updateLight(){
        updateLightSources();
        for (int x1=p1.getLoadXMin()-5; x1<p1.getLoadXMax()+5;x1++){
            for (int y1=p1.getLoadYMin()-5; y1<p1.getLoadYMax()+5;y1++){
                //shading[x1][y1].update(0);
                if (lightSources[x1][y1]!=0) {
                    spreadLight(x1,y1,lightSources[x1][y1]);
                }
            }
        }

    }

    public void spreadLight(int currentx, int currenty, double lastLight){
        if (!isValidPosition(currentx, currenty)){ return; }
        double newLight = (lastLight-blocks[currentx][currenty].getLightBlockage());
        if (newLight <= shading[currentx][currenty].getLightlvl()) { return; }
        shading[currentx][currenty].update(newLight);
        spreadLight(currentx+1, currenty, newLight);
        spreadLight(currentx, currenty+1, newLight);
        spreadLight(currentx-1, currenty, newLight);
        spreadLight(currentx, currenty-1, newLight);
    }

    public boolean isValidPosition(int x, int y){
        if (x>0&&x<length&&y>0&&y<height){
            return true;
        }else {
            return false;
        }
    }


    public void updateLightSources(){
        double time=bak.getTime();
        double sunlightlvl=0;
        if (time>3&&time<9){
            sunlightlvl=3;
        }else if (time>15&&time<21){
            sunlightlvl=1;
        }else {
            if (time<=3){
                sunlightlvl=2+(.5*(time/3));
            }else if (time>=9&&time<=15){
                time=time-9;
                sunlightlvl=3-((time/6));
            }else {
                time=time-21;
                sunlightlvl=1+(.5*(time/3));
            }
        }
        for (int x=p1.getLoadXMin()-5; x<p1.getLoadXMax()+5;x++){
            for (int y=p1.getLoadYMin()-5; y<p1.getLoadYMax()+5;y++){
                lightSources[x][y]=0;
                if (y<groundlvlmap[x]+15&&blocks[x][y].isPassable()) {
                    lightSources[x][y] += sunlightlvl;
                }
                if (blocks[x][y].isLightSrc){
                    lightSources[x][y]+=blocks[x][y].getLightlvl();
                }
            }
        }
    }

    /*public void setLight(){
        double time=bak.getTime();
        double sunlightlvl;
        if (time>3&&time<9){
            sunlightlvl=5;
        }else if (time>15&&time<21){
            sunlightlvl=1;
        }else {
            if (time<=3){
                sunlightlvl=3+(2*(time/3));
            }else if (time>=9&&time<=15){
                time=time-9;
                sunlightlvl=5-(4*(time/6));
            }else {
                time=time-21;
                sunlightlvl=1+(2*(time/3));
            }
        }
        for (int x=0; x<length;x++) {
            for (int y =0; y < height; y++) {
                lightSources[x][y]=0;
                if (y<groundlvlmap[x]+15&&blocks[x][y].isPassable()) {
                    lightSources[x][y]+=sunlightlvl;
                }
                if (blocks[x][y].isLightSrc){
                    lightSources[x][y]+=blocks[x][y].getLightlvl();
                }
            }
        }
        for (int x1=0; x1<length;x1++) {
            for (int y1 =0; y1 < height; y1++) {
                if (lightSources[x1][y1]!=0) {
                    spreadLight(x1,y1,lightSources[x1][y1]);
                }
            }
        }

    }*/



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