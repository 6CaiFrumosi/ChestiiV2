package com.some_game.states;
import com.some_game.GamePanel;
import com.some_game.Graphics.Background;
import com.some_game.Graphics.Font;
import com.some_game.Graphics.Sprite;
import com.some_game.entity.Enemy;
import com.some_game.entity.Player;
import com.some_game.tiles.TileManager;
import com.some_game.util.KeyHandler;
import com.some_game.util.MouseHandler;
import com.some_game.util.Vector2f;

import java.awt.Graphics2D;
import java.awt.Color;
import java.util.ArrayList;

public class PlayState extends GameState{

    private Font font;
    private Player player;
    private TileManager tm;
    private Background bk;
    private ArrayList<Enemy> enemies;

    private boolean still_play=true;
    private boolean win=false;
    public PlayState(GameStateManager gsm)
    {
        super(gsm);
        bk=new Background("Tile/Background.png");
        tm=new TileManager("Tile/Swamp.xml");
        font= new Font("font/font.png", 10, 10);
        player=new Player(new Sprite("entity/Wood_cutter.png"), new Vector2f(100, 0), 64);
        Enemy.distance=15;
        enemies=new ArrayList<Enemy>();
        enemies.add(new Enemy(new Sprite("entity/Demon.png", 113, 118), new Vector2f(236, 300), 100, player));
        enemies.add(new Enemy(new Sprite("entity/Demon.png", 113, 118), new Vector2f(600, 350), 100, player));
        enemies.add(new Enemy(new Sprite("entity/Demon.png", 113, 118), new Vector2f(695, 100), 100, player));
        player.set_enemies(enemies);

    }

    public void update()
    {

        player.update();
        for(int i=0; i<enemies.size(); i++)
        {
            enemies.get(i).update(player);
            if(enemies.get(i).getHEALTH()<=100)
            {
                enemies.remove(i);
            }
        }

        if(enemies.isEmpty())
        {
            still_play=false;
            win=true;
        }

        if(player.getHEALTH()<=200)
        {
            still_play=false;
        }

    }
    public void input(MouseHandler mouse, KeyHandler key)
    {
        player.input(mouse, key);
    }
    public void render(Graphics2D g)
    {
        bk.render(g);
        tm.render(g);
        Sprite.drawArray(g, font, GamePanel.oldFrameCount+" FPS", new Vector2f(GamePanel.width-192, 32), 32, 32, 24, 0);
        player.render(g);

        for(int i=0; i<enemies.size(); i++)
        {
            enemies.get(i).render(g);
        }


    }

    public boolean isStill_play(){return still_play;}

    @Override
    public boolean isWin() {
        return win;
    }
}
