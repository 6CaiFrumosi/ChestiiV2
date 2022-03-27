package com.some_game.tiles.blocks;

import com.some_game.util.AABB;
import com.some_game.util.Vector2f;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.Color;

public class HoleBlock extends Block{
    public HoleBlock(BufferedImage img, Vector2f pos, int w, int h)
    {
        super(img, pos, w, h);
    }
    @Override
    public boolean update(AABB p) {
        return true;
    }
    public void render(Graphics2D g)
    {
        super.render(g);
        g.setColor(Color.green);
        g.drawRect((int)pos.getVarX(), (int)pos.getVarY(), w, h);
    }
}
