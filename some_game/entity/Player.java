package com.some_game.entity;

import com.some_game.GamePanel;
import com.some_game.Graphics.Sprite;
import com.some_game.Stats.Health;
import com.some_game.states.PlayState;
import com.some_game.util.AABB;
import com.some_game.util.KeyHandler;
import com.some_game.util.MouseHandler;
import com.some_game.util.Vector2f;
import com.some_game.Graphics.Font;
import java.awt.*;
import java.util.ArrayList;

public class Player extends Entity{

    private int elevation=100;
    private Health hp;
    private Font font;
    private ArrayList<Enemy> enemies;
    private int n;
    public Player(Sprite sprite, Vector2f origin, int size)
    {
        super(sprite, origin, size);
        hp=new Health("Stats/Health.png", new Vector2f(25, 25), 150, 40);
        font=new Font("font/font.png", 10, 10);
    }

    public void move()
    {
        if(up&&!jumped)
        {
            jumped=true;
        }
        else
        {
            dy+=acc;

            if (dy > 2*maxSpeed) {
                dy = 2*maxSpeed;
            }
        }

        if(jumped)
        {
            if(elevation>0)
            {
                dy-=2*acc;

                if (dy < -2*maxSpeed) {
                    dy = -2*maxSpeed;
                }
                elevation+=dy;
            }
        }



        if(down) {
            dy += acc;
            if (dy > 2*maxSpeed) {
                dy = 2*maxSpeed;
            }
        }

        if(left) {
            dx -= acc;
            if (dx < -maxSpeed) {
                dx = -maxSpeed;
            }
        }
            else
            {
                if(dx<0)
                {
                    dx+=deacc;
                    if(dx>0)
                    {
                        dx=0;
                    }
                }
            }


        if(right) {
            dx += acc;
            if (dx > maxSpeed) {
                dx = maxSpeed;
            }
        }
            else
            {
                if(dx>0)
                {
                    dx-=deacc;
                    if(dx<0)
                    {
                        dx=0;
                    }
                }
            }


    }

    public String update_score()
    {
        return Integer.toString(this.n-this.enemies.size());
    }

    public void update(){


        Enemy some_monster;
        super.update();
        move();

        for(int i=0; i<enemies.size(); i++) {
            some_monster= enemies.get(i);
            if (!bounds.collides(some_monster.getBounds(), dx, 0) && !bounds.collides(some_monster.getBounds(), 0, dy)) {

                if (!bounds.colisionTile(dx, 0) && !bounds.collides(some_monster.getBounds(), dx, 0)) {
                    pos.x += dx/enemies.size();
                    hitBounds.update(dx/enemies.size(), 0);
                }
                if (!bounds.colisionTile(0, dy) && !bounds.collides(some_monster.getBounds(), 0, dy)) {
                    pos.y += dy/enemies.size();
                    hitBounds.update(0, dy/enemies.size());
                } else {
                    if (elevation <= 0) {
                        jumped = false;
                    } else {
                        elevation = 0;
                    }
                    if (jumped == false) {
                        elevation = 100;
                    }
                    dy = acc;
                }
            }


            if (some_monster.getHitBounds().colCircleBox(this.getBounds())) {
                hp.update(some_monster.attack);
                System.out.println("Circle Colision: " + some_monster.attack);
            }
        }

    }
    @Override
    public void render(Graphics2D g) {

        g.setColor(Color.blue);
        g.drawRect((int)(bounds.getPos().x), (int)(bounds.getPos().y), (int)bounds.GetSize(), (int)bounds.GetSize());
        g.setColor(Color.MAGENTA);
        g.drawOval((int)(hitBounds.getPos().x), (int)hitBounds.getPos().y, (int)hitBounds.getRadius(), (int)hitBounds.getRadius());
        hp.render(g);
        Sprite.drawArray(g, font, "Woodcutter", new Vector2f(30, 10), 20, 20, 14, 0);
        Sprite.drawArray(g, font, "Score:"+update_score(), new Vector2f(200, 10), 20, 20, 14, 0);
        g.drawImage(ani.getImage(), (int) (pos.x), (int)(pos.y), size, size, null);
    }
    public void input(MouseHandler mouse, KeyHandler key)
    {
        if(mouse.getButton()==1)
        {
            System.out.println("Player: "+pos.x+", "+pos.y);
        }
        if(key.up.down)
        {
          up=true;
        }else
        {
            up=false;
        }
        if(key.down.down)
        {
            down=true;
        }
        else
        {
            down=false;
        }
        if(key.left.down)
        {
            left=true;
        }
        else
        {
            left=false;
        }
        if(key.right.down)
        {
            right=true;
        }
        else
        {
            right=false;
        }
        if(key.attack.down)
        {
            attack=true;
        }
        else
        {
          attack=false;
        }
    }

    public void set_enemies(ArrayList<Enemy> enemies)
    {
        this.enemies=enemies;
        this.n=enemies.size();
    }

    public int getHEALTH(){
        return hp.getHEALTH();
    }
}
