package com.some_game.tiles.blocks;

import com.some_game.util.AABB;
import com.some_game.util.Vector2f;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ObjBlock extends Block{

    public ObjBlock(BufferedImage img, Vector2f pos, int w, int h)
    {
        super(img, pos, w, h);
    }
    @Override
    public boolean update(AABB p) {
        return false;
    }
    public void render(Graphics2D g)
    {
        super.render(g);
        g.setColor(Color.white);
        g.drawRect((int)pos.getVarX(), (int)pos.getVarY(), w, h);
    }
}
