import java.awt.*;
import java.awt.event.KeyEvent;

public class TextBox implements Sprite {

    String input="";

    public void TextBox(){

    }


    public void draw(Graphics g) {
        g.setColor(Color.black);
        g.fillRect(0,(int)(HEIGHT*.9),WIDTH/3, 30);
        g.setColor(Color.white);
        g.drawString(input,10, (int)(HEIGHT*.9)+20);
    }

    public void setInput(String s){
        input=s;
    }

    public void changeInput(KeyEvent e){
        if (e.getKeyCode()==KeyEvent.VK_BACK_SPACE){
            if (input.length()>0){
                input=input.substring(0,input.length()-1);
            }
        }else if (e.getKeyCode()==KeyEvent.VK_SPACE) {
            input+=" ";
        }else if(e.getKeyCode()==KeyEvent.VK_SLASH||e.getKeyCode()==KeyEvent.VK_BACK_SLASH){
            input+="/";
        }else if (e.getKeyCode() > 47 && e.getKeyCode() < 58){
            input+=e.getKeyCode() - 48;

        }else {
            input+=(char)(e.getKeyCode()+32);
        }
    }

    public String getInput() {
        return input;
    }

    public int getY() { return 0; }
    public int getX() { return 0; }
}
