import javafx.stage.Screen;

import java.applet.Applet;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.applet.*;
import java.awt.*;
import java.awt.event.*;
import java.security.Key;
//https://docs.oracle.com/javase/tutorial/uiswing/components/textfield.html

public class Main extends Applet implements Runnable, KeyListener, MouseListener {

    private final int WIDTH=1280, HEIGHT=900;
    private Thread thread;
    private int screenNum=0;
    Graphics gfx;
    Image img;
    private boolean firstLaunch=true;
    private Map map;

    public void init(){
        this.resize(WIDTH, HEIGHT);
        this.addKeyListener(this);
        img=createImage(WIDTH,HEIGHT);
        gfx=img.getGraphics();
        System.out.println("creating thread");
        thread=new Thread(this);
        System.out.println("thread created, starting thread");
        thread.start();
        System.out.println("thread started");
    }



    public void paint(Graphics g){
        if(screenNum==0) {
            if (firstLaunch){
                gfx.setFont(new Font("default", Font.BOLD, 12));
                firstLaunch=false;
            }
            gfx.setColor(Color.BLACK);//background
            gfx.fillRect(0,0,WIDTH,HEIGHT);//background size
            gfx.setColor(Color.WHITE);
            gfx.drawString("Enter: New Map", (WIDTH/2)-200, (HEIGHT/2));


        }else if(screenNum==1){
            //System.out.println("drawing main");
            gfx.setColor(Color.BLACK);//background
            gfx.fillRect(0,0,WIDTH,HEIGHT);//background size
            map.draw(gfx);

        }
        g.drawImage(img,0,0,this);
    }

    public void update(Graphics g){
        paint(g);
    }

    public boolean txtcreated=false;

    public void run() {

        for (;;){

            if(screenNum==0){
            }else if(screenNum==1) {
                map.move();
            }
            repaint();
            try{
                Thread.sleep(15);
            } catch (InterruptedException e) {
                e.printStackTrace();
                System.out.println("GAME FAILED TO RUN");
            }
        }
    }

    //@Override
    public void keyTyped(KeyEvent e) {

    }


    public void keyPressed(KeyEvent e) {
        if(screenNum==1) {
            if (e.getKeyCode() == KeyEvent.VK_D) {
                map.setMovingRight(true);
            }else if (e.getKeyCode() == KeyEvent.VK_A) {
                map.setMovingLeft(true);
            }else if(e.getKeyCode()==KeyEvent.VK_SPACE){
                map.jump();
            }
        }
    }


    public void keyReleased(KeyEvent e) {
        System.out.println("key released");
        if(screenNum==1) {
            if (e.getKeyCode() == KeyEvent.VK_D) {
                map.setMovingRight(false);
            }else if (e.getKeyCode() == KeyEvent.VK_A) {
                map.setMovingLeft(false);
            }
        }else if(screenNum==0){
            if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                map=new Map(1);
                screenNum=1;
                System.out.println("pressed enter");
            }

        }
    }


    public void mouseClicked(MouseEvent e) {

    }


    public void mousePressed(MouseEvent e) {

    }


    public void mouseReleased(MouseEvent e) {

    }


    public void mouseEntered(MouseEvent e) {

    }


    public void mouseExited(MouseEvent e) {

    }
}


