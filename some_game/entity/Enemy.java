package com.some_game.entity;

import com.some_game.GamePanel;
import com.some_game.Graphics.Sprite;
import com.some_game.Stats.Health;
import com.some_game.util.AABB;
import com.some_game.util.Vector2f;
import com.some_game.Graphics.Font;
import java.awt.*;

public class Enemy extends Entity {

    private AABB sense;
    private int walk = 200;
    boolean my_switch=true;
    private Health some_hp;
    public static int distance;
    private Font font;
    public Enemy(Sprite sprite, Vector2f origin, int size, Entity player) {
        super(sprite, origin, size);
        maxSpeed = 1f;
        acc = 0.5f;
        deacc = 0.1f;
        sense = new AABB(new Vector2f(origin.x-40, origin.y-40), size+120);
        right = true;
        left = false;
        RIGHT=0;
        LEFT=1;
        TOOGLE_R=2;
        TOOGLE_L=3;
        ATTACK=4;
        HURT=5;
        DEATH=6;
        some_hp=new Health("Stats/Health.png",new Vector2f(GamePanel.width-380, distance), 150, 40);
        distance+= some_hp.getH()+20;
        font=new Font("font/font.png", 10, 10);
    }

    @Override
    public void render(Graphics2D g) {
        Sprite.drawArray(g, font, "Darkin Demon soldier", new Vector2f(some_hp.getPos().x-260, some_hp.getPos().y+15), 20, 20, 14, 0);
        g.setColor(Color.blue);
        g.drawRect((int) (pos.x), (int) (pos.y), (int) (bounds.getWidth()), (int) (bounds.getHeight()));
        g.setColor(Color.red);
        g.drawOval((int) (sense.getPos().x), (int)(sense.getPos().y), (int) sense.getRadius(), (int) sense.getRadius());
        g.setColor(Color.MAGENTA);
        g.drawOval((int)(hitBounds.getPos().x), (int)(hitBounds.getPos().y), (int)hitBounds.getRadius(), (int)hitBounds.getRadius());
        some_hp.render(g);
        g.drawImage(ani.getImage(), (int) (pos.x), (int) (pos.y), size, size, null);

    }


    public void chase(Player player) {
        AABB playerBounds = player.getBounds();

        if (pos.x > player.pos.x + 1) {
            left = true;
        } else {
            left = false;
        }
        if (pos.x < player.pos.x - 1) {
            right = true;
        } else {
            right = false;
        }
    }

    public AABB getSense()
    {
        return sense;
    }
    public void move() {
        if (left) {
            dx -= acc;
            if (dx < -maxSpeed) {
                dx = -maxSpeed;
            }
            walk += dx;
        } else {
            if (dx < 0) {
                dx += deacc;
                if (dx > 0) {
                    dx = 0;
                }
            }
        }


        if (right) {
            dx += acc;
            if (dx > maxSpeed) {
                dx = maxSpeed;
            }
            walk -= dx;
        } else {
            if (dx > 0) {
                dx -= deacc;
                if (dx < 0) {
                    dx = 0;
                }
            }
        }

        dy += acc;
        if (dy > 2 * maxSpeed) {
            dy = 2 * maxSpeed;
        }

    }

    public void update(Player player) {
        super.update();
        ani.setDelay(7);
        move();




            if(player.getHitBounds().colCircleBox(this.getBounds()))
            {
                some_hp.update(player.attack);
            }

            if(hitBounds.colCircleBox(player.getBounds()))
            {
                System.out.println("Hitbounds collision");
                if(right&&currentAnimation==RIGHT)
                    idle_r=true;

                if(left&&currentAnimation==LEFT)
                    idle_l=true;
                if((currentAnimation==TOOGLE_L||currentAnimation==TOOGLE_R)&&ani.hasPlayed(2))
                {
                    System.out.println("Has played once: "+ani.hasPlayedOnce());
                    attack=true;
                    idle_r=false;
                    idle_l=false;
                }
                if(currentAnimation==ATTACK&& ani.hasPlayedOnce())
                {
                    attack=false;
                }
            }else {
                idle_r = false;
                idle_l = false;
                attack = false;
                if (sense.colCircleBox(player.getBounds())) {
                    System.out.println("Sense colides");
                    this.chase(player);
                } else if (walk <= 0) {
                    right = !right;
                    left = !left;

                    walk = 200;
                }
            }

            if (!bounds.colisionTile(dx, 0)&&!bounds.collides(player.bounds, dx, dy)) {
                pos.x += dx;
                sense.update(dx, 0);
                hitBounds.update(dx, 0);
            }

            if (!bounds.colisionTile(0, dy)&&!bounds.collides(player.bounds, dx, dy)) {
                    pos.y += dy;
                    sense.update(0, dy);
                    hitBounds.update(0, dy);
            }

    }

    public int getHEALTH(){
        return some_hp.getHEALTH();
    }
}




