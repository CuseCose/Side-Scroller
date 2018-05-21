import java.awt.*;

public class InvItem extends Item implements Sprite {
    /*int itemID;
    boolean isBlock;
    String itemName;
    boolean exists=false;
    Color color;
    boolean isStackable;

    boolean isTool=false;
    int amt=0;
    boolean isRecipe=false;
    boolean isIngredient=false;
    boolean isComplex=false;
    Color color2;
    boolean hasImg;
    Image img;
*/
    int x,y;


    public void draw(Graphics g){//drawing in inv
        g.setColor(invTextColor);
        g.drawString(amt + "", 20 + (60 * x) + 30, 50 + (60 * y) + 30);//show amount of an item
        if (amt>0) {
            if (isBlock) {
                g.drawImage(img, 20 + (60 * x) + 10, 50 + (60 * y) + 10, 20, 20, null);//draw block in inv
            }else {
                g.drawImage(img, 20 + (60 * x) + 5, 50 + (60 * y) + 5, 30, 30, null);//draw block in inv
            }
        }
    }


    public int getY() { return 0; }

    public int getX() { return 0; }


    public void draw(Graphics g, int x, int y) {
        g.setColor(invTextColor);
        g.drawString(amt+"", x + 20, y + 30);//show amount of an item
        if (!hasImg) {
            if (isBlock) {
                g.setColor(color);
                g.fillRect(x, y, 20, 20);//draw block in inv
                if (isComplex) {
                    if (itemID == 13) {
                        g.setColor(color2);
                        g.fillRect(x + 5, y, 10, 5);
                    } else if (itemID == 14) {
                        for (int i = 0; i < 5; i++) {
                            g.setColor(color2);
                            g.fillRect(x, y + (i * 4), 20, 2);
                        }
                    }
                }
            } else {
                if (exists) {
                    g.setColor(Color.black);
                    g.drawString(itemName, x, y);
                }
            }
        }else {
            g.drawImage(img,20 + (60 * x)+10, 50+(60*y)+10, 20, 20, null);//draw block in inv
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


    public void changeAmt(int amtchange){
        amt=amt+amtchange;
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


    public InvItem(int itemnum){
        super(itemnum);
        amt=1;
        isRecipe=true;
    }


    public InvItem(int itemnum, int amt){
        super(itemnum);
        super.amt=amt;

    }


    public InvItem(int itemnum, int x, int y){
        super(itemnum);
        itemID=itemnum;
        this.x=x;
        this.y=y;
    }


    public InvItem(int itemnum, int x, int y, int amt){
        super(itemnum);
        itemID=itemnum;
        this.x=x;
        this.y=y;
        super.amt=amt;

    }





    public boolean has(InvItem other){
        if (amt>=other.amt&&itemID==other.itemID){
            return true;
        }else {
            return false;
        }
    }


    public int getItemID(){return itemID;}
}
