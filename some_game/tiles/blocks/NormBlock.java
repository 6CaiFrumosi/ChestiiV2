package com.some_game.tiles.blocks;

import com.some_game.util.AABB;
import com.some_game.util.Vector2f;

import java.awt.*;
import java.awt.image.BufferedImage;

public class NormBlock extends Block{

    public NormBlock(BufferedImage img, Vector2f pos, int w, int h)
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
    }
}
