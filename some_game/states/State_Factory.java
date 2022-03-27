package com.some_game.states;

import static com.some_game.states.GameStateManager.PLAY;
import static com.some_game.states.GameStateManager.MENU;
import static com.some_game.states.GameStateManager.GAMEOVER;

public class State_Factory {

    private static State_Factory singleton_instance=null;
    public GameState getState(GameStateManager manager, int state)
    {
        if(state==PLAY)
        {
            return new PlayState(manager);
        }
        if(state==MENU)
        {
            return new MenuState(manager);
        }
        if(state==GAMEOVER)
        {
            return new GameOverState(manager);
        }
        return null;
    }

    public static State_Factory getInstance()
    {
        if(singleton_instance==null)
            singleton_instance=new State_Factory();
        return singleton_instance;
    }

}
