package com.some_game.states;

import com.some_game.GamePanel;
import com.some_game.Graphics.Sprite;
import com.some_game.util.KeyHandler;
import com.some_game.util.MouseHandler;

import java.awt.*;

public class GameOverState extends GameState {
    private Sprite GameOver;
    public GameOverState(GameStateManager gsm) {
        super(gsm);
        GameOver=new Sprite("Stats/game_over.png", 1200, 673);
    }

    @Override
    public void update() {

    }

    @Override
    public void input(MouseHandler mouse, KeyHandler key) {

    }

    @Override
    public void render(Graphics2D g) {
        g.drawImage(GameOver.getSpriteSheet(), 0, 0, GamePanel.width, GamePanel.height, null);
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
