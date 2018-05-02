import java.awt.*;

public class Recipe {
    boolean furnaceNeeded;
    boolean tableNeeded;
    boolean anvilNeeded;
    boolean craftable=false;
    Item[] neededItems;
    int productID;
    Item product;
    Color invColor=new Color(204, 204, 179);
    int numproducts=1;



    public Recipe(int productID){
        this.productID=productID;
        product=new Item(productID);
        boolean furnaceNeeded=false;
        boolean tableNeeded=false;
        boolean anvilNeeded=false;
        switch (productID){
            case 13://crafting table
                System.out.println("crafting table recipe created");
                craftable=true;
                neededItems=new Item[1];
                addToNeededItems(5,4, 0);
                break;
            case 14://wood planks
                System.out.println("wood planks recipe created");
                craftable=true;
                neededItems=new Item[1];
                addToNeededItems(5,4, 0);
                numproducts=16;
                break;
            default:
                System.out.println("recipe for uncraftable item created");
                craftable=false;
                break;
        }
    }


    public void addToNeededItems(int itemID, int amt, int itemnumonrecipe){
        neededItems[itemnumonrecipe]=new Item(itemID, amt);
    }


    public boolean canCraft(Item[][] inv, int invLength, int invHeight){
        if(craftable) {
            boolean[] hasItem = new boolean[neededItems.length];
            for (int i = 0; i < hasItem.length; i++) {
                hasItem[i] = false;
            }
            for (int invy = 0; invy < invHeight-1; invy++) {
                for (int invx = 0; invx < invLength-1; invx++) {
                    for (int i = 0; i < neededItems.length; i++) {
                        if (inv[invy][invx].has(neededItems[i])) {
                            hasItem[i] = true;
                        }
                    }
                }
            }
            for (int i = 0; i < hasItem.length; i++) {
                if (!hasItem[i]) {
                    return false;
                }
            }
            return true;
        }else{
            return false;
        }
    }


    public String toString(){
        return product.itemName;
    }


    public void draw(Graphics g,int x, int y, boolean isSelected){
        if (isSelected) {
            g.setColor(invColor);
            g.fillRect(x - 10, y - 10, 40, 40);
        }else {
            g.setColor(invColor);
            g.drawRect(x - 10, y - 10, 40, 40);
        }
        product.draw(g, x, y);
        for (int i=0; i<neededItems.length; i++){
            g.setColor(invColor);
            g.drawRect(x+50+(60*i), y-10, 40, 40);
            neededItems[i].draw(g, x+60+(60*i), y);
        }
    }


    public void craftRecipe(Item[][] inv, int invLength, int invHeight){
        boolean[] removedItem=new boolean[neededItems.length];
        for (int invy=0; invy<invHeight-1;invy++){
            for (int invx=0;invx<invLength-1;invx++){
                for (int i=0; i<neededItems.length;i++){
                    if (inv[invy][invx].has(neededItems[i])&&!removedItem[i]){
                        inv[invy][invx].changeAmt(0-neededItems[i].amt);
                        removedItem[i]=true;
                    }
                }
            }
        }
        boolean hasItem=false;
        for (int invy=0;invy<invHeight-1;invy++){
            for (int invx=0; invx<invLength-1;invx++){
                if (inv[invy][invx].itemID==productID){
                    for (int i=0; i<numproducts;i++) {
                        inv[invy][invx].changeAmt(true);
                    }
                    hasItem=true;
                    invx=invLength;
                    invy=invHeight;
                }
            }
        }
        if (!hasItem){
            for (int invy=0;invy<invHeight-1;invy++){
                for (int invx=0; invx<invLength-1;invx++){
                    if (!inv[invy][invx].exists){
                        inv[invy][invx]=new Item(productID,invx,invy, numproducts);
                        invx=invLength;
                        invy=invHeight;
                    }
                }
            }
        }
    }
}
