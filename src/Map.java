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
    double[][] lightSources;
    double[][] light;
    double[][] dark;
    boolean[][] sky;





    public Map(int size){
        mg=new MapGen(size);
        System.out.println("mapgen done");
        length=mg.getLength();
        height=mg.getHeight();
        System.out.println("map dimensions:"+length+" x "+height);
        biomemap=new int[length];
        groundlvlmap=new int[length];
        blocks=new Block[length][height];
        lightSources=new double[length][height];
        light=new double[length][height];
        dark=new double[length][height];
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
        setLight();
    }

    public Map(String fileloc, int type){//type indicates if your using a old map and new char, or new map and old char

    }


    public Map(String fileloc, String charfile){

    }


    public void draw(Graphics g) {
        bak.draw(g);
        for(int loadx=p1.getLoadXMin();loadx<p1.getLoadXMax();loadx++){
            for(int loady=p1.getLoadYMin(); loady<p1.getLoadYMax(); loady++){
                blocks[loadx][loady].draw(g,WIDTH/2+((loadx-(length/2))*blockSize)-p1.getX(), HEIGHT/2+((loady-(height/2))*blockSize)+p1.getY(), dark[loadx][loady]);
                g.setColor(Color.black);
                //g.drawString((int)dark[loadx][loady]+"", WIDTH/2+((loadx-(length/2))*blockSize)-p1.getX()+5, HEIGHT/2+((loady-(height/2))*blockSize)+p1.getY()+10);
                //g.drawString("l:"+(int)light[loadx][loady]+"", WIDTH/2+((loadx-(length/2))*blockSize)-p1.getX()+5, HEIGHT/2+((loady-(height/2))*blockSize)+p1.getY()+20);
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
        setLightSources();
        for (int x1=p1.getLoadXMin()-5; x1<p1.getLoadXMax()+5;x1++){
            for (int y1=p1.getLoadYMin()-5; y1<p1.getLoadYMax()+5;y1++){
                double lightlevel = 0;
                if (lightSources[x1][y1]!=0) {
                    lightlevel = lightSources[x1][y1];
                }
                for (int dtbe=1;dtbe<5;dtbe++) {
                    lightlevel += (lightSources[x1 + dtbe][y1] / (dtbe + 1));
                    lightlevel += (lightSources[x1 - dtbe][1] / (dtbe + 1));
                    lightlevel += (lightSources[x1][y1 + dtbe] / (dtbe + 1));
                    lightlevel += (lightSources[x1][y1 - dtbe] / (dtbe + 1));
                    for (int i=1;i<1+2*(dtbe-1);i++){
                        lightlevel+= (lightSources[x1 + dtbe][y1+i] / (dtbe + 1+i));
                        lightlevel+= (lightSources[x1 - dtbe][y1-i] / (dtbe + 1+i));
                        lightlevel+= (lightSources[x1-i][y1+dtbe] / (dtbe + 1+i));
                        lightlevel+= (lightSources[x1 + i][y1-dtbe] / (dtbe + 1+i));

                    }
                }
                light[x1][y1]=lightlevel;
            }
        }
        for (int x1=p1.getLoadXMin()-5; x1<p1.getLoadXMax()+5;x1++) {
            for (int y1 = p1.getLoadYMin()-5; y1 < p1.getLoadYMax()+5; y1++) {
                shading[x1][y1].update(12-Math.sqrt(light[x1][y1]));
            }
        }
    }


    public void setLightSources(){
        double time=bak.getTime();
        double sunlightlvl;
        if (time>3&&time<9){
            sunlightlvl=10;
        }else if (time>15&&time<21){
            sunlightlvl=2;
        }else {
            if (time<=3){
                sunlightlvl=6+(4*(time/3));
            }else if (time>=9&&time<=15){
                time=time-9;
                sunlightlvl=10-(8*(time/6));
            }else {
                time=time-21;
                sunlightlvl=2+(4*(time/3));
            }
        }
        for (int x=p1.getLoadXMin()-5; x<p1.getLoadXMax()+5;x++){
            for (int y=p1.getLoadYMin()-5; y<p1.getLoadYMax()+5;y++){
                lightSources[x][y]=0;
                if (y<groundlvlmap[x]+15&&blocks[x][y].isPassable()) {
                    lightSources[x][y]+=sunlightlvl;

                }else {
                }
                if (blocks[x][y].isLightSrc){
                    lightSources[x][y]+=blocks[x][y].getLightlvl();
                }
            }
        }
    }

    public void setLight(){
        double time=bak.getTime();
        double sunlightlvl;
        if (time>3&&time<9){
            sunlightlvl=10;
        }else if (time>15&&time<21){
            sunlightlvl=2;
        }else {
            if (time<=3){
                sunlightlvl=6+(4*(time/3));
            }else if (time>=9&&time<=15){
                time=time-9;
                sunlightlvl=10-(8*(time/6));
            }else {
                time=time-21;
                sunlightlvl=2+(4*(time/3));
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
                double lightlevel = 0;
                if (lightSources[x1][y1]!=0) {
                    lightlevel = lightSources[x1][y1];
                }
                for (int dtbe=1;dtbe<5;dtbe++) {
                    if (x1-dtbe>0&&x1+dtbe<height&&y1+dtbe<height&&y1-dtbe>0) {
                        lightlevel += (lightSources[x1 + dtbe][y1] / (dtbe + 1));
                        lightlevel += (lightSources[x1 - dtbe][1] / (dtbe + 1));
                        lightlevel += (lightSources[x1][y1 + dtbe] / (dtbe + 1));
                        lightlevel += (lightSources[x1][y1 - dtbe] / (dtbe + 1));
                        for (int i = 1; i < 1 + 2 * (dtbe - 1); i++) {
                            if (x1-i>0&&x1+i<height&&y1+i<height&&y1-i>0) {
                                lightlevel += (lightSources[x1 + dtbe][y1 + i] / (dtbe + 1 + i));
                                lightlevel += (lightSources[x1 - dtbe][y1 - i] / (dtbe + 1 + i));
                                lightlevel += (lightSources[x1 - i][y1 + dtbe] / (dtbe + 1 + i));
                                lightlevel += (lightSources[x1 + i][y1 - dtbe] / (dtbe + 1 + i));
                            }

                        }
                    }
                }
                light[x1][y1]=lightlevel;
            }
        }
        for (int x1=0; x1<length;x1++) {
            for (int y1 =0; y1 < height; y1++) {
                dark[x1][y1]=12-Math.sqrt(light[x1][y1]);
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