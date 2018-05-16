import java.awt.*;

public class RecipeList implements Sprite{
    Recipe[] allRecipes;
    Recipe[] availableRecipes;
    int maxItemId=14;
    int selectedItemLoc=0;


    public RecipeList(){
        allRecipes=new Recipe[maxItemId+1];
        for (int i=0;i<=maxItemId;i++){
               allRecipes[i]=new Recipe(i);
        }
    }


    public void updateAvailable(Item[][]inv, int invLength, int invHeight){
        Recipe[] newAvailable;
        int numRecipesAvailable=0;
        for (int i=0;i<=maxItemId;i++){
            if (allRecipes[i].canCraft(inv, invLength, invHeight)){
                numRecipesAvailable++;
            }
        }
        newAvailable=new Recipe[numRecipesAvailable];
        int addednum=0;
        for (int i=0;i<=maxItemId;i++){
            if (allRecipes[i].canCraft(inv, invLength, invHeight)){
                newAvailable[addednum]=allRecipes[i];
                addednum++;
            }
        }
        availableRecipes=newAvailable;
    }


    public void draw(Graphics g){
        if (availableRecipes.length>0){
            for (int i=0; i<availableRecipes.length;i++){
                if (i==selectedItemLoc) {
                    availableRecipes[i].draw(g, 30, (int) (HEIGHT * .2 + (60 * i)), true);
                }else {
                    availableRecipes[i].draw(g, 30, (int) (HEIGHT * .2 + (60 * i)), false);
                }
            }
        }else {
            g.drawString("no recipes available",WIDTH/10, (int)(HEIGHT*.2));
        }
    }


    public void move() {
    }


    public void changeSelected(boolean inc){
        if (inc){
            if (selectedItemLoc<availableRecipes.length-1){
                selectedItemLoc++;
            }
        }else if (!inc){
            if (selectedItemLoc>0){
                selectedItemLoc--;
            }
        }
    }


    public void craftSelected(Item[][] inv, int invLength, int invHeight){
        availableRecipes[selectedItemLoc].craftRecipe(inv, invLength, invHeight);
    }


    public int getY() { return 0; }


    public int getX() { return 0; }
}
