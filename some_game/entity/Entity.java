package com.some_game.entity;

import com.some_game.Graphics.Animation;
import com.some_game.Graphics.Sprite;
import com.some_game.util.AABB;
import com.some_game.util.KeyHandler;
import com.some_game.util.MouseHandler;
import com.some_game.util.Vector2f;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class Entity {
    protected int UP= 0;
    protected int DOWN=3;
    protected int RIGHT=1;
    protected int LEFT=2;

    protected int ATTACK=4;
    protected int TOOGLE_R=5;
    protected int TOOGLE_L=6;
    protected int DEATH;
    protected int HURT;


    protected int currentAnimation;

    protected Animation ani;
    protected Sprite sprite;
    protected Vector2f pos;
    protected int size;

    protected boolean up;
    protected boolean down;
    protected boolean right;
    protected boolean left;
    protected boolean attack;
    protected boolean idle_r;
    protected boolean idle_l;
    protected boolean attackSpeed;
    protected int attackDuration;


    protected boolean jumped=false;
    protected boolean fall;

    protected float dx;
    protected float dy;

    protected float maxSpeed=2f;
    protected float acc=1f;
    protected float deacc=0.1f;

    protected AABB hitBounds;
    protected AABB bounds;



    public Entity(Sprite sprite, Vector2f origin, int size){
        this.sprite=sprite;
        pos=origin;
        this.size=size;
        bounds =new AABB(origin, size-10, size);
        hitBounds=new AABB(new Vector2f(origin.x-10, origin.y-10), size+40);
        ani= new Animation();
        setAnimation(RIGHT, sprite.getSpriteArray(RIGHT), 10);

}
    public void setAnimation(int i, BufferedImage[] frames, int delay)
    {
        currentAnimation=i;
        ani.setFrames(frames);
        ani.setDelay(delay);
    }

    public int getSize(){return size;}
    public Animation getAnimation(){return ani; }
    public void setSprite(Sprite sprite)
    {
        this.sprite=sprite;
    }
    public void  setSize(int i){size=i;}
    public void setMaxPpeed(float f){maxSpeed=f;}
   public void setAcc(float f){acc=f;}
   public void setDeacc(float f){deacc=f;}


    public void animate()
    {
        if(idle_r)
        {
            if(currentAnimation!=TOOGLE_R||ani.getDelay()==-1)
            {
                setAnimation(TOOGLE_R, sprite.getSpriteArray(TOOGLE_R), 5);
            }
        }
        else if(idle_l)
        {
            if(currentAnimation!=TOOGLE_L||ani.getDelay()==-1)
            {
                setAnimation(TOOGLE_L, sprite.getSpriteArray(TOOGLE_L), 5);
            }
        }
        else if(attack) {
            if(currentAnimation!=ATTACK||ani.getDelay()==-1)
            {
                setAnimation(ATTACK, sprite.getSpriteArray(ATTACK), 5);
            }

        }else if(down) {
                if (currentAnimation != DOWN || ani.getDelay() == -1) {
                    setAnimation(DOWN, sprite.getSpriteArray(DOWN), 5);
                }
            }else if(left){
                    if(currentAnimation!=LEFT||ani.getDelay()==-1)
                    {
                        setAnimation(LEFT, sprite.getSpriteArray(LEFT), 5);
                    }
                }
                else if(right) {
                    if (currentAnimation != RIGHT || ani.getDelay() == -1) {
                        setAnimation(RIGHT, sprite.getSpriteArray(RIGHT), 5);
                    }
            }
                else if(up)
                {
                    if (currentAnimation != UP || ani.getDelay() == -1) {
                        setAnimation(UP, sprite.getSpriteArray(UP), 5);
                    }
                }
            else {
            if ((currentAnimation != TOOGLE_R || ani.getDelay() == -1)&&(currentAnimation==RIGHT||currentAnimation==UP||currentAnimation==DOWN||currentAnimation==ATTACK)) {
                setAnimation(TOOGLE_R, sprite.getSpriteArray(TOOGLE_R), 15);
            }
            else if ((currentAnimation != TOOGLE_L || ani.getDelay() == -1)&&currentAnimation==LEFT) {
                setAnimation(TOOGLE_L, sprite.getSpriteArray(TOOGLE_L), 15);
            }
            }

        }

    public void update()
    {
        animate();
        ani.update();
    }

    public abstract void render(Graphics2D g);

    public Vector2f getPos(){return pos;}

    public void input(KeyHandler key, MouseHandler mouse)
    {}
    public AABB getHitBounds(){return hitBounds;}
    public AABB getBounds(){return bounds;}
}
