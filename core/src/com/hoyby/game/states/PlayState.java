package com.hoyby.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.hoyby.game.MyHeliGame;
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
        System.out.println(bg.getWidth() + " " + bg.getHeight());
        cam.setToOrtho(false, bg.getWidth() / 2, bg.getHeight() / 2);
        coords = new BitmapFont();
        bounds.put("minX", -((bg.getWidth() / 2) - (MyHeliGame.WIDTH / 2)));
        bounds.put("maxX", bounds.get("minX") + bg.getWidth());
        bounds.put("minY", -((bg.getHeight() / 2) - (MyHeliGame.HEIGHT / 2)));
        bounds.put("maxY", bounds.get("minY") + bg.getHeight());
        helicopter = new Helicopter(50, 300, bounds);
        for (int i = 0; i < 10; i++) {
            enemies.add(new Helicopter(
                    bounds.get("maxX") - new Random().nextInt(bg.getWidth()),
                    bounds.get("maxY") - new Random().nextInt(bg.getHeight()),
                    bounds));
        }
        for (Helicopter enemy : enemies) {
            enemy.flyRandom();
        }
    }

    @Override
    protected void handleInput() {
        if (Gdx.input.isTouched()) {
            int x = bounds.get("minX") + (Gdx.input.getX() * (bg.getWidth() / MyHeliGame.WIDTH)) - (helicopter.getTexture().getWidth() / 2);
            int y = bounds.get("maxY") - (Gdx.input.getY() * (bg.getHeight() / MyHeliGame.HEIGHT)) - (helicopter.getTexture().getHeight() / 2);
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
        cam.position.set(helicopter.getPosition().x + (helicopter.getTexture().getWidth() / 2), helicopter.getPosition().y + (helicopter.getTexture().getHeight() / 2), 0);
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
        sb.draw(bg, -((bg.getWidth() / 2) - (MyHeliGame.WIDTH / 2)), -((bg.getHeight() / 2) - (MyHeliGame.HEIGHT / 2)));
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
        for (Helicopter enemy : enemies) {
            enemy.dispose();
        }
    }
}
