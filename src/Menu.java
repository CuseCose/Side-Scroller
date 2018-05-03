import java.awt.*;

public class Menu implements Sprite{
    int menunum;
    boolean menuOpen;
    int numbuttons;
    String[] buttonLabels;
    int selectedButton=0;
    Color menuColor=new Color(204, 204, 179);
    Color selectedButtonColor=new Color(255, 102, 102);
    Color buttonColor=new Color(122, 122, 82);

    public Menu(int menunum){
        this.menunum=menunum;
        menuOpen=true;
        setMenuSettings();
    }

    private void setMenuSettings(){
        switch (menunum){
            case 0:
                numbuttons=3;
                buttonLabels= new String[]{"New Game", "Load Game", "Exit"};
                break;
        }
    }

    public void draw(Graphics g){
        if (menunum==0) {
            g.setColor(menuColor);//background
            g.fillRect(0, 0, WIDTH, HEIGHT);//background size
        }
        for (int i=0; i<numbuttons; i++){
            if (selectedButton==i){
                g.setColor(selectedButtonColor);
            }else {
                g.setColor(buttonColor);
            }
            g.fillRect(WIDTH/2-100,HEIGHT/2-(int)(.5*(80*numbuttons))+(80*i), 200, 60);
            g.setColor(Color.black);
            g.drawString(buttonLabels[i], WIDTH/2-100+10,HEIGHT/2-(int)(.5*(80*numbuttons))+(80*i)+30);
        }
    }

    public int getSelectedButton() { return selectedButton; }

    public void changeSelectedButton(boolean up){
        if (up){
            if (selectedButton>0){
                selectedButton--;
            }
        }else {
            if (selectedButton<numbuttons-1){
                selectedButton++;
            }
        }
    }

    public int getY() { return 0; }
    public int getX() { return 0; }
}
