package com.hoyby.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MenuState extends State {
    private final Texture background;
    private final Texture playBtn;


    public MenuState(GameStateManager gsm) {
        super(gsm);
        background = new Texture("bg.jpg");
        playBtn = new Texture("playbtn.png");
        cam.setToOrtho(false, background.getWidth() / 2f, background.getHeight() / 2f);
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
        sb.setProjectionMatrix(cam.combined);
        sb.begin();
        sb.draw(background, 0, -background.getHeight() / 4f);
        sb.draw(playBtn, cam.position.x - (playBtn.getWidth() / 2f), (cam.position.y));
        sb.end();
    }

    @Override
    public void dispose() {
        background.dispose();
        playBtn.dispose();
    }
}
