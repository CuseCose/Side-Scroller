import java.awt.*;

public class Item {
    int itemID;
    boolean isBlock;
    String itemName;
    boolean exists=false;
    Color color;
    boolean isStackable;
    int x,y;
    boolean isTool=false;
    int amt=0;

    public void draw(Graphics g){
        g.setColor(Color.black);
        g.drawString(amt+"", 20 + (60 * x) + 30, 50 + (60 * y) + 30);//show amount of an item
        if (isBlock){
            g.setColor(color);
            g.fillRect(20 + (60 * x)+10, 50+(60*y)+10, 20, 20);//draw block in inv
        }else {
            if (exists) {
                g.setColor(Color.black);
                g.drawString(itemName, 20 + (60 * x) + 5, 50 + (60 * y) + 10);
            }
        }
    }

    public void changeAmt(boolean isIncreasing){
        if (isIncreasing){
            amt++;
        }else {
            amt--;
        }
        if (amt<=0){
            itemName="null";
            exists=false;
            isBlock=false;
            isTool=false;
            amt=0;
            itemID=0;
        }
    }

    public void removeThis(){

    }

    public Item(int itemnum,int x,int y){
        itemID=itemnum;
        this.x=x;
        this.y=y;
        exists=true;
        isTool=false;
        amt=1;
        switch (itemID){
            case 0://nothing
                itemName="null";
                exists=false;
                isBlock=false;
                amt=0;
                break;
            case 1://grass
                itemName="Grass";
                isStackable=true;
                isBlock=true;
                isStackable=true;
                color=Color.green;
                break;
            case 2://grey rock
                itemName="Grey Rock";
                isStackable=true;
                isBlock=true;
                color=Color.GRAY;
                isStackable=true;
                break;
            case 3://black rock
                isBlock=true;
                itemName="Black Rock";
                isStackable=true;
                color=Color.BLACK;
                isStackable=true;
                break;
            case 4://dirt
                itemName="Dirt";
                isBlock=true;
                isStackable=true;
                isStackable=true;
                color=new Color(102,51,0);
                break;
            case 5://wood
                itemName="Wood";
                isBlock=true;
                isStackable=true;
                isStackable=true;
                color=new Color(153,102,51);
                break;
            case 6://leaves
                itemName="leaves";
                isBlock=true;
                isStackable=true;
                color=new Color(0, 153, 51);
                break;
            case 7://clouds
                itemName="Cloud";
                isBlock=true;
                isStackable=true;
                color=Color.white;
                break;
            case 8://sand
                itemName="Sand";
                isBlock=true;
                color=Color.yellow;
                isStackable=true;
                break;
            case 9://snow
                itemName="Snow";
                isBlock=true;
                color=Color.white;
                isStackable=true;
                break;
            case 10://snowy leaves
                itemName="Snowy Leaves";
                isBlock=true;
                isStackable=true;
                color=Color.white;
                break;
            case 11://cactus
                itemName="Cactus";
                isBlock=true;
                isStackable=true;
                color=new Color(0,120,3);
                break;
            case 12://pickaxe
                itemName="pickaxe";
                isTool=true;
                isBlock=false;
                isStackable=false;
                color=new Color(0,120,3);
                break;
        }
    }

    public int getItemID(){return itemID;}
}
