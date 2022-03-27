package com.some_game.tiles;
import com.some_game.Graphics.Sprite;
import com.some_game.tiles.blocks.Block;
import com.some_game.tiles.blocks.NormBlock;
import com.some_game.util.Vector2f;

import java.awt.Graphics2D;
import java.util.HashMap;


public class TileMapNorm extends TileMap{
    public static HashMap<String, Block> blocks;
    public TileMapNorm(String data, Sprite sprite, int width, int height, int tileWidth, int tileHeight, int tileColumns)
    {
        blocks=new HashMap<String, Block>();
        String[] block=data.split(",");
        int blocks_w=sprite.getWidth();
        int blocks_h=sprite.getHEight();
        for(int i=0; i<(width*height); i++)
        {
            int temp=Integer.parseInt(block[i].replaceAll("\\s+", ""));
            if(temp!=0)
            {
                int x=(int) (i %width) * tileHeight;
                int y=(int) (i/width) * tileWidth;
                blocks.put(String.valueOf((int) (i %width))+", "+String.valueOf((int) (i/width)), new NormBlock(sprite.getSprite((int) ((temp - 1) % tileColumns), (int) ((temp - 1) / tileColumns) ), new Vector2f(x, y), tileWidth, tileHeight));
            }

        }
    }

    public void render(Graphics2D g)
    {
        for(Block b : blocks.values())
        {
            b.render(g);
        }

    }
}
