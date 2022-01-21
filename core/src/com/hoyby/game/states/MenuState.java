package com.hoyby.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.hoyby.game.MyHeliGame;

public class MenuState extends State {
    private final Texture background;
    private final Texture playBtn;

    public MenuState(GameStateManager gsm) {
        super(gsm);
        background = new Texture("bg.jpg");
        playBtn = new Texture("playbtn.png");
    }


    @Override
    protected void handleInput() {
        if (Gdx.input.isTouched()) {
            gsm.set(new PlayState(gsm));
        }
    }

    @Override
    public void update(float dt) {
        handleInput();
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.begin();
        sb.draw(background, 0, 0, MyHeliGame.WIDTH, MyHeliGame.HEIGHT);
        sb.draw(playBtn, (MyHeliGame.WIDTH / 2) - (playBtn.getWidth() / 2), (MyHeliGame.HEIGHT / 2));
        sb.end();
    }

    @Override
    public void dispose() {
        background.dispose();
        playBtn.dispose();
    }
}
