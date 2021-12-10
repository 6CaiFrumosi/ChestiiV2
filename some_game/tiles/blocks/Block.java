package com.some_game.tiles.blocks;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import com.some_game.util.Vector2f;
import com.some_game.util.AABB;
public abstract class Block {
    protected int w;
    protected int h;

    protected BufferedImage img;
    protected Vector2f pos;

    public Block(BufferedImage img, Vector2f pos, int w, int h)
    {
        this.img=img;
        this.pos=pos;
        this.w=w;
        this.h=h;
    }
    public abstract boolean update(AABB p);

    public void render(Graphics2D g)
    {
        g.drawImage(img, (int) pos.getVarX(), (int)pos.getVarY(), w, h, null);
    }

}
