package com.some_game.states;

import com.some_game.GamePanel;
import com.some_game.Graphics.Sprite;
import com.some_game.util.KeyHandler;
import com.some_game.util.MouseHandler;

import java.awt.*;

public class MenuState extends GameState {

    private Sprite Win;
    public MenuState(GameStateManager gsm) {
        super(gsm);
        Win=new Sprite("Stats/win.png", 852, 480);
    }

    @Override
    public void update() {

    }

    @Override
    public void input(MouseHandler mouse, KeyHandler key) {

    }

    @Override
    public void render(Graphics2D g) {
        g.drawImage(Win.getSpriteSheet(), 0, 0, GamePanel.width, GamePanel.height, null);
    }

    @Override
    public boolean isStill_play() {
        return true;
    }

    @Override
    public boolean isWin() {
        return false;
    }
}
