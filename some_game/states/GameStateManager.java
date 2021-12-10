package com.some_game.states;
import java.util.ArrayList;

import com.some_game.GamePanel;
import com.some_game.util.KeyHandler;
import com.some_game.util.MouseHandler;
import com.some_game.util.Vector2f;
import java.awt.*;

public class GameStateManager {
    private ArrayList<GameState> states;
    public static Vector2f map;
    public static final int PLAY=0;
    public static final int MENU=1;
    public static final int PAUSE=2;
    public static final int GAMEOVER=3;

    public static State_Factory my_factory=State_Factory.getInstance();

    public GameStateManager() {
        map=new Vector2f(GamePanel.width, GamePanel.height);
        states=new ArrayList<GameState>();
        states.add(new PlayState(this));
    }
    public void pop(int state){
        states.remove(state);
    }
    public void add(int state)
    {
            states.add(my_factory.getState(this, state));
    }
    public void addAndpop(int state)
    {
        states.remove(0);
        add(state);
    }

    public void update() {
        for(int i=0; i<states.size(); i++)
        {
            states.get(i).update();
            if(!states.get(i).isStill_play())
            {
                if(states.get(i).isWin())
                    this.addAndpop(MENU);
                else
                    this.addAndpop(GAMEOVER);
            }
        }

    }

    public void input(MouseHandler mouse, KeyHandler key)
    {
        for(int i=0; i<states.size(); i++)
        {
            states.get(i).input(mouse, key);
        }
    }

    public void render(Graphics2D g)
    {
        for(int i=0; i<states.size(); i++)
        {
            states.get(i).render(g);
        }
    }
}