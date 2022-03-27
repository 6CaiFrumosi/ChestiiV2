package com.some_game.tiles;
import com.some_game.Graphics.Sprite;
import com.some_game.tiles.blocks.Block;
import com.some_game.tiles.blocks.HoleBlock;
import com.some_game.tiles.blocks.ObjBlock;
import com.some_game.util.Vector2f;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
public class TileMapObj extends TileMap{
    public static HashMap<String, Block> tmp_blocks;
    public TileMapObj(String data, Sprite sprite, int width, int height, int tileWidth, int tileHeight, int tileColumns)
    {
        Block tempBlock;
        tmp_blocks=new HashMap<String, Block>();
        String[] block=data.split(",");
        for(int i=0; i<(width*height); i++)
        {
            int temp=Integer.parseInt(block[i].replaceAll("\\s+", ""));
            if(temp!=0)
            {
                if(temp<=178)
                {
                    tempBlock=new HoleBlock(sprite.getSprite((int)((temp-1)%tileColumns), (int)((temp-1)/tileColumns)), new Vector2f((int)(i%width)*tileWidth, (int)(i/tileHeight)*tileHeight), tileWidth, tileHeight);
                }
                else
                {
                    tempBlock=new ObjBlock(sprite.getSprite((int)((temp-1)%tileColumns), (int)((temp-1)/tileColumns)), new Vector2f((int)(i%width)*tileWidth, (int)(i/tileHeight)*tileHeight), tileWidth, tileHeight);
                }
                tmp_blocks.put(String.valueOf((int)(i%width))+", "+String.valueOf((int)(i/height)), tempBlock);
            }

        }
    }

    public void render(Graphics2D g)
    {
        for(Block block: tmp_blocks.values())
        {
            block.render(g);
        }

    }
}
