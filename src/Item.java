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



    public Item(int type){
        setBlock(type);
    }

    public void setBlock(int type){
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
                color = Color.green;
                hasImg = true;
                img = tk.getImage(DOCPATH + "grass.png");
                break;
            case 2://grey rock
                isNotStandable=false;
                isNotClimbable=false;
                isPassable=false;
                itemName = "Grey Rock";
                isStackable = true;
                isBlock = true;
                color = Color.GRAY;
                isStackable = true;
                hasImg = true;
                img = tk.getImage(DOCPATH + "grey rock.png");
                break;
            case 3://black rock
                isNotStandable=false;
                isNotClimbable=false;
                isPassable=false;
                isBlock = true;
                itemName = "Black Rock";
                isStackable = true;
                color = Color.BLACK;
                isStackable = true;
                hasImg = true;
                img = tk.getImage(DOCPATH + "black rock.png");
                break;
            case 4://dirt
                itemName = "Dirt";
                isBlock = true;
                isStackable = true;
                isStackable = true;
                color = new Color(102, 51, 0);
                hasImg = true;
                img = tk.getImage(DOCPATH + "dirt.png");
                break;
            case 5://wood
                itemName = "Wood";
                isBlock = true;
                isStackable = true;
                isStackable = true;
                color = new Color(153, 102, 51);
                hasImg = true;
                img = tk.getImage(DOCPATH + "wood.png");
                break;
            case 6://leaves
                itemName = "leaves";
                isBlock = true;
                isStackable = true;
                color = new Color(0, 153, 51);
                hasImg = true;
                img = tk.getImage(DOCPATH + "leaves.png");
                break;
            case 7://clouds
                itemName = "Cloud";
                isBlock = true;
                isStackable = true;
                color = Color.white;
                break;
            case 8://sand
                itemName = "Sand";
                isBlock = true;
                color = Color.yellow;
                isStackable = true;
                hasImg = true;
                img = tk.getImage(DOCPATH + "sand.png");
                break;
            case 9://snow
                itemName = "Snow";
                isBlock = true;
                color = Color.white;
                isStackable = true;
                hasImg = true;
                img = tk.getImage(DOCPATH + "snow.png");
                break;
            case 10://snowy leaves
                itemName = "Snowy Leaves";
                isBlock = true;
                isStackable = true;
                color = Color.white;
                hasImg = true;
                img = tk.getImage(DOCPATH + "snowy leaves.png");
                break;
            case 11://cactus
                itemName = "Cactus";
                isBlock = true;
                isStackable = true;
                hasImg = true;
                img = tk.getImage(DOCPATH + "cactus.png");
                break;
            case 12://pickaxe
                itemName = "pickaxe";
                isTool = true;
                isBlock = false;
                isStackable = false;
                color = new Color(0, 120, 3);
                break;
            case 13://table
                isComplex = true;
                itemName = "CTable";
                isBlock = true;
                isStackable = true;
                color = new Color(250, 100, 60);
                color2 = new Color(102, 51, 0);
                break;
            case 14://wood planks
                isComplex = true;
                itemName = "WoodPlanks";
                isBlock = true;
                isStackable = true;
                color = new Color(200, 100, 50);
                color2 = new Color(103, 51, 0);
                break;
            case 15://iron ore
                isBlock = true;
                itemName = "Iron Ore";
                isStackable = true;
                color = Color.BLACK;
                isStackable = true;
                hasImg = true;
                img = tk.getImage(DOCPATH + "iron ore.png");
                break;
            case 16://coal
                isBlock = true;
                itemName = "Coal";
                isStackable = true;
                color = Color.BLACK;
                isStackable = true;
                hasImg = true;
                img = tk.getImage(DOCPATH + "coal ore.png");
                break;
            case 17://copper ore
                isBlock = true;
                itemName = "Copper Ore";
                isStackable = true;
                color = Color.BLACK;
                isStackable = true;
                hasImg = true;
                img = tk.getImage(DOCPATH + "copper ore.png");
                break;
        }
    }

    public void draw(Graphics g) { }
    public int getY() { return 0; }
    public int getX() { return 0; }
}
