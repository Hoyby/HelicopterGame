package com.hoyby.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.hoyby.game.sprites.Helicopter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class PlayState extends State {

    private final Helicopter helicopter;
    private final Texture bg;
    private final BitmapFont coords;
    private final HashMap<String, Integer> bounds = new HashMap<>();
    private final List<Helicopter> enemies = new ArrayList<>();


    public PlayState(GameStateManager gsm) {
        super(gsm);

        bg = new Texture("bg.jpg");

        cam.setToOrtho(false, bg.getWidth() / 2f, bg.getHeight() / 2f);

        coords = new BitmapFont();
        coords.getData().setScale(1.8f);

        bounds.put("minX", 0);
        bounds.put("maxX", bg.getWidth());
        bounds.put("minY", 0);
        bounds.put("maxY", bg.getHeight());

        helicopter = new Helicopter(bg.getWidth() / 2, bg.getHeight() / 2, bounds);

        for (int i = 0; i < 10; i++) {
            Helicopter newEnemy = new Helicopter(
                    bounds.get("maxX") - new Random().nextInt(bg.getWidth()),
                    bounds.get("maxY") - new Random().nextInt(bg.getHeight()),
                    bounds);
            enemies.add(newEnemy);
            newEnemy.flyRandom();
        }
    }

    @Override
    protected void handleInput() {
        if (Gdx.input.isTouched()) {
            float xMultiplier = (float) bg.getWidth() / (float) Gdx.graphics.getWidth();
            float yMultiplier = (float) bg.getHeight() / (float) Gdx.graphics.getHeight();
            float x = Gdx.input.getX() * xMultiplier - helicopter.getTexture().getWidth() / 2f;
            float y = bg.getHeight() - (Gdx.input.getY() * yMultiplier) - helicopter.getTexture().getHeight() / 2f;
            helicopter.flyTowards(x, y);
        }
    }

    @Override
    public void update(float dt) {
        handleInput();
        for (Helicopter e : enemies) {
            e.update(dt);
        }
        helicopter.update(dt);
        cam.position.set(helicopter.getPosition().x + (helicopter.getTexture().getWidth() / 2f), helicopter.getPosition().y + (helicopter.getTexture().getHeight() / 2f), 0);
        for (Helicopter enemy : enemies) {
            for (Helicopter other : enemies) {
                if (enemy != other) {
                    other.collides(enemy);
                }
            }
            helicopter.collides(enemy);
            cam.update();
        }
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(cam.combined);
        sb.begin();
        sb.draw(bg, 0, 0);
        sb.draw(helicopter.getTexture(), helicopter.getVelocity().x > 0 ? helicopter.getPosition().x + helicopter.getTexture().getWidth() : helicopter.getPosition().x, helicopter.getPosition().y, helicopter.getVelocity().x > 0 ? -helicopter.getTexture().getWidth() : helicopter.getTexture().getWidth(), helicopter.getTexture().getHeight());
        for (Helicopter enemy : enemies) {
            sb.draw(enemy.getTexture(), enemy.getVelocity().x > 0 ? enemy.getPosition().x + enemy.getTexture().getWidth() : enemy.getPosition().x, enemy.getPosition().y, enemy.getVelocity().x > 0 ? -enemy.getTexture().getWidth() : helicopter.getTexture().getWidth(), helicopter.getTexture().getHeight());
        }
        coords.draw(sb, "X: " + Math.round(helicopter.getPosition().x) + " Y: " + Math.round(helicopter.getPosition().y), cam.position.x - (cam.viewportWidth / 2) + 20, cam.position.y + (cam.viewportHeight / 2) - 20);
        sb.end();
    }

    @Override
    public void dispose() {
        bg.dispose();
        helicopter.dispose();
        coords.dispose();
        for (Helicopter enemy : enemies) {
            enemy.dispose();
        }
    }
}
