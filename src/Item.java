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

    public void draw(Graphics g){
        if (isBlock){
            g.setColor(color);
            g.drawRect(20 + (60 * x), 50+(60*y), 40, 40);
        }else {
            if (exists) {
                g.setColor(Color.black);
                g.drawString(itemName, 20 + (60 * x) + 5, 50 + (60 * y) + 10);
            }
        }
    }

    public Item(int itemnum,int x,int y){
        itemID=itemnum;
        this.x=x;
        this.y=y;
        exists=true;
        isTool=false;
        switch (itemID){
            case 0://nothing
                itemName="null";
                exists=false;
                isBlock=false;
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
