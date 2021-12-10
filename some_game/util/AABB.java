package com.some_game.util;

import com.some_game.entity.Entity;
import com.some_game.tiles.TileMapNorm;

public class AABB {
    private Vector2f pos;
    private float w;
    private float h;
    private float r;
    private int size;
    public AABB(Vector2f pos, int w, int h)
    {
        this.pos=pos;
        this.w=w;
        this.h=h;
        size=Math.max(w, h);

    }

    public AABB(Vector2f pos, int r)
    {
        this.pos=pos;
        this.r=r;
        size=r;
    }

    public Vector2f getPos(){return pos;}
    public float getRadius(){return r;}
    public float getWidth(){return w;}
    public float getHeight(){return h;}
    public void setBox(Vector2f pos, int w, int h)
    {
        this.pos=pos;
        this.w=w;
        this.h=h;

        size=Math.max(w, h);
    }

    public void setCircle(Vector2f pos, int r)
    {
        this.pos=pos;
        this.r=r;
        size=r;


    }

    public void setRadius(float r)
    {
        this.r=r;
    }

    public void setWidth(float f){w=f;}
    public void setHeight(float f){h=f;}
    public boolean collides(AABB bBox, float dx, float dy)
    {
        float ax=pos.getVarX()+dx+w/2;
        float ay=pos.getVarY()+dy+h/2;
        float bx=bBox.pos.getVarX()+ bBox.getWidth()/2;
        float by=bBox.pos.getVarY()+bBox.getHeight()/2;
        if(Math.abs(ax-bx)<(this.w/2)+(bBox.getWidth()/2))
        {
            if(Math.abs(ay-by)<(this.h/2)+(bBox.getHeight()/2))
            {
                return true;
            }

        }
        return false;
    }

    public boolean colCircleBox(AABB aBox) {

        float dx = Math.max(aBox.getPos().x, Math.min(pos.x + (r / 2), aBox.getPos().x+ aBox.getWidth()));
        float dy = Math.max(aBox.getPos().y, Math.min(pos.y + (r / 2), aBox.getPos().y+ aBox.getHeight()));

        dx = pos.x + (r / 2) - dx;
        dy = pos.y + (r / 2) - dy;

        if(Math.sqrt(dx * dx + dy * dy) < r / 2) {
            return true;
        }

        return false;
    }

public boolean colisionTile(float  ax, float ay)
{
    for(int i=2; i<4; i++)
    {
        int xt=(int)((pos.x+ax)+(i%2)*w)/32;
        int yt=(int)((pos.y+ay)+((int)(i/2))*h)/32;
        if(TileMapNorm.blocks.containsKey(String.valueOf(xt)+", "+String.valueOf(yt)))
        {
            return TileMapNorm.blocks.get(String.valueOf(xt)+", "+String.valueOf(yt)).update(this);
        }


    }
    return false;
}

    public boolean circle_collides_rectangle(AABB e) {
        float min_x=e.getPos().x;
        float min_y=e.getPos().y;

        float max_x=e.getPos().x+e.size;
        float max_y=e.getPos().y+e.size;

        float closest_x=pos.x;
        float closest_y=pos.y;

        if(closest_x < min_x)
            closest_x=min_x;
        else if(closest_x>max_x)
            closest_x=max_x;

        if(closest_y < min_y)
            closest_y=min_y;
        else if(closest_y>max_y)
            closest_y=max_y;

        float dif_x=pos.x-closest_x;
        float dif_y=pos.y-closest_y;


        return Math.pow(dif_x, 2) + Math.pow(dif_y, 2) < Math.pow(this.r, 2);
    }


public int GetSize(){return size;}

public void update(float dx, float dy)
{
    pos.x+=dx;
    pos.y+=dy;
}


}
