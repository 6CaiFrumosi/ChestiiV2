package com.some_game.Stats;

import com.some_game.GamePanel;
import com.some_game.Graphics.Sprite;
import com.some_game.util.Vector2f;
import com.some_game.Graphics.Font;
import java.awt.*;

public class Health {
    private Sprite hp;
    private Vector2f pos;
    private int HEALTH=600;
    private int w;
    private int h;

    public Health(String path, Vector2f pos, int w, int h)
    {

        hp=new Sprite(path, 630, 179);
        this.pos=pos;
        this.w=w;
        this.h=h;
    }


    public void render(Graphics2D g)
    {
        g.drawImage(hp.getSpriteArray(HEALTH/100-1)[0], (int) (pos.x), (int)(pos.y), w, h, null);

    }

    public void update(boolean attacked)
    {
        if(attacked)
        {
            if(HEALTH/100>=1) {
                HEALTH--;
                System.out.println("Current health: "+HEALTH);
            }
        }
    }

    public int getW()
    {
        return hp.getWidth();
    }

    public int getH()
    {
        return hp.getHEight()/hp.gethSprite();
    }

    public int getHEALTH(){return HEALTH;}

    public Vector2f getPos(){return pos;}
}
