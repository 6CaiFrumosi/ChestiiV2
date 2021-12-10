package com.some_game;
import com.some_game.Graphics.Sprite;
import com.some_game.states.GameStateManager;
import com.some_game.util.KeyHandler;
import com.some_game.util.MouseHandler;

import javax.swing.JPanel;
import java.awt.*;
import java.awt.image.BufferedImage;

public class GamePanel extends JPanel implements Runnable{
    public static int width;
    public static int height;
    private Thread thread;
    private BufferedImage image;
    private Graphics2D g;
    private boolean running;
    private MouseHandler mouse;
    private KeyHandler key;
    private GameStateManager gsm;
    public static int oldFrameCount;

    private Sprite sprite;

    private static GamePanel singleton_instance=null;

    public GamePanel(int width, int height)
    {
        this.width =width;
        this.height=height;
        setPreferredSize(new Dimension(width, height));
        setFocusable(true);
        requestFocus();
    }

    public static GamePanel getInstance(int width, int height)
    {
        if(singleton_instance==null)
        {

            System.out.println("Creeare Panou joc!");
            singleton_instance=new GamePanel(width, height);
        }
        else
        {
            System.out.println("Panou joc existent!!");
        }
        return singleton_instance;
    }
    public void addNotify(){
        super.addNotify();

        if(thread==null)
        {
            thread=new Thread(this, "GameThread");
            thread.start();
        }
    }
    public void init()
    {
        running=true;
        image=new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        g=(Graphics2D) image.getGraphics();
        mouse= new MouseHandler(this);
        key=new KeyHandler(this);
        gsm= new GameStateManager();
    }
    public void run() {
        //Initializez fundalul pe care void "desena"/randa
        init();
        //Refresh rate la joc
        final double GAME_HERTZ=60.0;
        final double TBU=1000000000/GAME_HERTZ;//DeltaT-Update
        final int MUBR=5; //Must update before render
        double lastUpdateTime=System.nanoTime();
        double lastRenderTime;
        final double FPS=60;
        final double TTBR=1000000000/FPS;//DeltaT-Render
        int frameCount=0;
        int lastSecondtime=(int)(lastUpdateTime/1000000000);
        oldFrameCount=0;

        while (running) {
            double now=System.nanoTime();
            int updateCount=0;
            while((now-lastUpdateTime)>TBU && updateCount<MUBR)
            {
                update();
                input(mouse, key);
                lastUpdateTime+=TBU;
                updateCount++;
            }
            if(now-lastUpdateTime>TBU)
            {
                lastUpdateTime=now-TBU;
            }
            input(mouse, key);
            render();
            draw();
            lastRenderTime=now;
            frameCount++;
            int this_seconds=(int)(lastUpdateTime/1000000000);
            if(this_seconds>lastSecondtime) {
                if (frameCount != oldFrameCount)
                {
                    System.out.println("NEW SECOND "+this_seconds+" "+frameCount);
                    oldFrameCount=frameCount;
                }
                frameCount=0;
                lastSecondtime=this_seconds;
            }
            while(now -lastRenderTime<TTBR && now-lastUpdateTime<TBU)
            {
                Thread.yield();
                try{
                    Thread.sleep(1);
                }
                catch(Exception e)
                {
                    System.out.println("ERROR: yeild thread");
                }
                now=System.nanoTime();
            }
        }
    }

        public void update(){
        gsm.update();
    }
    public void input(MouseHandler mouse, KeyHandler key)
    {
        gsm.input(mouse, key);
    }
        public void render(){
        if(g!=null)
        {
            g.setColor(new Color(66, 134, 244));
            g.fillRect(0, 0, width, height);
            gsm.render(g);
        }
        }
        public void draw(){
        Graphics g2=(Graphics) this.getGraphics();
        g2.drawImage(image, 0, 0, width, height, null);
        g2.dispose();
    }

}
