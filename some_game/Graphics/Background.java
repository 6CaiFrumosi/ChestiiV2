package com.some_game.Graphics;
import com.some_game.Window;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;


public class Background {
    private BufferedImage img;
    public Background(String path)
    {
        img=loadSprite(path);
    }

    public void render(Graphics2D g) {
        g.drawImage(img, 0, 0,Window.getW(), Window.getH(), null);
    }

    private BufferedImage loadSprite(String file)
    {
        BufferedImage sprite=null;
        try{
            sprite= ImageIO.read(getClass().getClassLoader().getResourceAsStream(file));
        }
        catch(Exception e)
        {
            System.out.println("ERROR: could not load file: "+file);
        }
        return sprite;
    }

}
