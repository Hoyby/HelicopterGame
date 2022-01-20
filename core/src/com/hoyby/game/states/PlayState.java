package com.hoyby.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.hoyby.game.MyHeliGame;
import com.hoyby.game.sprites.Helicopter;

public class PlayState extends State {

    private final Helicopter helicopter;
    private final Texture bg;

    public PlayState(GameStateManager gsm) {
        super(gsm);
        helicopter = new Helicopter(50, 300);
        cam.setToOrtho(false, MyHeliGame.WIDTH, MyHeliGame.HEIGHT);
        bg = new Texture("bg.jpg");
        helicopter.fly();
    }

    @Override
    protected void handleInput() {
        if (Gdx.input.justTouched()) {

        }
    }

    @Override
    public void update(float dt) {
        handleInput();
        helicopter.update(dt);
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(cam.combined);
        sb.begin();
        sb.draw(bg, 0, 0);
        sb.draw(helicopter.getTexture(), helicopter.getVelocity().x > 0 ? helicopter.getPosition().x + helicopter.getTexture().getWidth() : helicopter.getPosition().x, helicopter.getPosition().y, helicopter.getVelocity().x > 0 ? -helicopter.getTexture().getWidth() : helicopter.getTexture().getWidth(), helicopter.getTexture().getHeight());
        sb.end();

    }

    @Override
    public void dispose() {

    }
}
