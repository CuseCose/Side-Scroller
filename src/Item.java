import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

public abstract class Item implements Sprite {

  int itemID;
  boolean isBlock;
  String itemName;
  boolean exists = false;
  boolean isStackable;
  boolean isTool = false;
  int amt = 0;
  boolean isRecipe = false;
  boolean isIngredient = false;
  boolean isComplex = false;
  boolean hasImg;
  Image img;
  boolean isNotStandable, isNotClimbable, isPassable;
  Color color;
  Color color2;
  boolean complex;
  int blockSize;
  boolean isLightSrc;
  int lightlvl;
  double lightBlockage;
  boolean isPlacable;
  int pickstr;


  public Item(int type) {
    setBlock(type);
  }

  public void setBlock(int type) {
    itemID = type;
    exists = true;
    complex = false;
    isTool = false;
    amt = 1;
    hasImg = true;
    isLightSrc = false;
    lightBlockage = .2;
    isStackable = true;
    isBlock = true;
    isPlacable = true;

    switch (itemID) {
      case 0://nothing
        isNotStandable = true;
        isNotClimbable = true;
        isPassable = true;
        itemName = "null";
        exists = false;
        isBlock = false;
        amt = 0;
        lightBlockage = .25;
        break;
      case 1://grass
        isNotStandable = false;
        isNotClimbable = false;
        isPassable = false;
        itemName = "Grass";
        break;
      case 2://grey rock
        isNotStandable = false;
        isNotClimbable = false;
        isPassable = false;
        itemName = "Grey Rock";
        break;
      case 3://black rock
        isNotStandable = false;
        isNotClimbable = false;
        isPassable = false;
        itemName = "Black Rock";
        break;
      case 4://dirt
        itemName = "Dirt";
        isNotStandable = false;
        isNotClimbable = false;
        isPassable = false;
        break;
      case 5://wood
        itemName = "Wood";
        isNotStandable = true;
        isNotClimbable = false;
        isPassable = true;
        break;
      case 6://leaves
        isNotStandable = true;
        isNotClimbable = false;
        isPassable = true;
        itemName = "leaves";
        break;
      case 7://clouds
        isNotStandable = false;
        isNotClimbable = false;
        isPassable = true;
        itemName = "Cloud";
        break;
      case 8://sand
        itemName = "Sand";
        isNotStandable = false;
        isNotClimbable = false;
        isPassable = false;
        break;
      case 9://snow
        isNotStandable = false;
        isNotClimbable = false;
        isPassable = false;
        itemName = "Snow";
        break;
      case 10://snowy leaves
        isNotStandable = true;
        isNotClimbable = false;
        isPassable = true;
        itemName = "Snowy Leaves";
        break;
      case 11://cactus
        itemName = "Cactus";
        isNotStandable = true;
        isNotClimbable = true;
        isPassable = true;
        break;
      case 12://pickaxe
        itemName = "pickaxe";
        isTool = true;
        isBlock = false;
        isStackable = false;
        isPlacable = false;
        pickstr = 20;
        break;
      case 13://table
        isNotStandable = true;
        isNotClimbable = false;
        isPassable = true;
        itemName = "Crafting Table";
        break;
      case 14://wood planks
        isNotStandable = true;
        isNotClimbable = true;
        isPassable = true;
        itemName = "Wood Planks";
        break;
      case 15://iron ore
        isNotStandable = false;
        isNotClimbable = false;
        isPassable = false;
        itemName = "Iron Ore";
        break;
      case 16://coal
        isNotStandable = false;
        isNotClimbable = false;
        isPassable = false;
        itemName = "Coal";
        break;
      case 17://copper ore
        isNotStandable = false;
        isNotClimbable = false;
        isPassable = false;
        itemName = "Copper Ore";
        break;
      case 18://torch
        isLightSrc = true;
        isNotStandable = true;
        isNotClimbable = true;
        isPassable = true;
        lightlvl = 4;
        isBlock = false;
        itemName = "Torch";
        break;
    }
    if (isPassable) {
      lightBlockage = .05;
    }

    if (hasImg) {
      img = tk.getImage(DOCPATH + type + ".png");
    }
  }

  public double getLightBlockage() {
    return lightBlockage;
  }

  public void draw(Graphics g) {
  }

  public int getY() {
    return 0;
  }

  public int getX() {
    return 0;
  }

  public boolean isLightSrc() {
    return isLightSrc;
  }

  public int getLightlvl() {
    return lightlvl;
  }

}
