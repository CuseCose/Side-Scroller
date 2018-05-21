import java.awt.*;

public abstract class Item implements Sprite {

    int itemID;
    boolean isBlock;
    String itemName;
    boolean exists=false;
    boolean isStackable;
    boolean isTool=false;
    int amt=0;
    boolean isRecipe=false;
    boolean isIngredient=false;
    boolean isComplex=false;
    boolean hasImg;
    Image img;
    boolean isNotStandable, isNotClimbable, isPassable;
    Color color;
    Color color2;
    boolean complex;
    int blockSize;
    boolean isLightSrc;
    int lightlvl;



    public Item(int type){
        setBlock(type);
    }

    public void setBlock(int type){
        itemID=type;
        exists=true;
        complex=false;
        isTool=false;
        amt=1;
        hasImg = true;
        isLightSrc=false;
        switch (itemID) {
            case 0://nothing
                isNotStandable=true;
                isNotClimbable=true;
                isPassable=true;
                itemName = "null";
                exists = false;
                isBlock = false;
                amt = 0;
                isNotStandable=true;
                isNotClimbable=true;
                isPassable=true;
                break;
            case 1://grass
                isNotStandable=false;
                isNotClimbable=false;
                isPassable=false;
                itemName = "Grass";
                isStackable = true;
                isBlock = true;
                isStackable = true;
                break;
            case 2://grey rock
                isNotStandable=false;
                isNotClimbable=false;
                isPassable=false;
                itemName = "Grey Rock";
                isStackable = true;
                isBlock = true;
                isStackable = true;
                break;
            case 3://black rock
                isNotStandable=false;
                isNotClimbable=false;
                isPassable=false;
                isBlock = true;
                itemName = "Black Rock";
                isStackable = true;
                isStackable = true;
                break;
            case 4://dirt
                itemName = "Dirt";
                isNotStandable=false;
                isNotClimbable=false;
                isPassable=false;
                isBlock = true;
                isStackable = true;
                isStackable = true;
                break;
            case 5://wood
                itemName = "Wood";
                isNotStandable=true;
                isNotClimbable=false;
                isPassable=true;
                isBlock = true;
                isStackable = true;
                isStackable = true;
                break;
            case 6://leaves
                isNotStandable=true;
                isNotClimbable=false;
                isPassable=true;
                itemName = "leaves";
                isBlock = true;
                isStackable = true;
                break;
            case 7://clouds
                isNotStandable=false;
                isNotClimbable=false;
                isPassable=true;
                itemName = "Cloud";
                isBlock = true;
                isStackable = true;
                break;
            case 8://sand
                itemName = "Sand";
                isNotStandable=false;
                isNotClimbable=false;
                isPassable=false;
                isBlock = true;
                isStackable = true;
                break;
            case 9://snow
                isNotStandable=false;
                isNotClimbable=false;
                isPassable=false;
                itemName = "Snow";
                isBlock = true;
                isStackable = true;
                break;
            case 10://snowy leaves
                isNotStandable=true;
                isNotClimbable=false;
                isPassable=true;
                itemName = "Snowy Leaves";
                isBlock = true;
                isStackable = true;
                break;
            case 11://cactus
                itemName = "Cactus";
                isNotStandable=true;
                isNotClimbable=true;
                isPassable=true;
                isBlock = true;
                isStackable = true;
                break;
            case 12://pickaxe
                itemName = "pickaxe";
                isTool = true;
                isBlock = false;
                isStackable = false;
                break;
            case 13://table
                complex=true;
                isNotStandable=true;
                isNotClimbable=false;
                isPassable=true;
                isComplex = true;
                itemName = "Crafting Table";
                isBlock = true;
                isStackable = true;
                break;
            case 14://wood planks
                complex=true;
                isNotStandable=true;
                isNotClimbable=true;
                isPassable=true;
                isComplex = true;
                itemName = "Wood Planks";
                isBlock = true;
                isStackable = true;
                hasImg=true;
                break;
            case 15://iron ore
                isNotStandable=false;
                isNotClimbable=false;
                isPassable=false;
                isBlock = true;
                itemName = "Iron Ore";
                isStackable = true;
                isStackable = true;
                break;
            case 16://coal
                isNotStandable=false;
                isNotClimbable=false;
                isPassable=false;
                isBlock = true;
                itemName = "Coal";
                isStackable = true;

                break;
            case 17://copper ore
                isNotStandable=false;
                isNotClimbable=false;
                isPassable=false;
                isBlock = true;
                itemName = "Copper Ore";
                isStackable = true;
                break;
        }
        if (hasImg){
            img=tk.getImage(DOCPATH+type+".png");
        }
    }

    public void draw(Graphics g) { }
    public int getY() { return 0; }
    public int getX() { return 0; }
    public boolean isLightSrc(){return isLightSrc;}
    public int getLightlvl(){return lightlvl;}
}
