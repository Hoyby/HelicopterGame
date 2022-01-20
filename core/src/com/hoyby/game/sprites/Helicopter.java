package com.hoyby.game.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector3;

import java.util.HashMap;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class Helicopter {
    private final Vector3 position;
    private final Vector3 velocity;
    private final Texture helicopter;
    private final HashMap<String, Integer> bounds;


    public Helicopter(int x, int y, HashMap<String, Integer> bounds) {
        position = new Vector3(x, y, 0);
        velocity = new Vector3(0, 0, 0);
        helicopter = new Texture("helicopter/heli1.png");
        this.bounds = bounds;
    }

    public Vector3 getPosition() {
        return position;
    }

    public Texture getTexture() {
        return helicopter;
    }

    public Vector3 getVelocity() {
        return velocity;
    }

    private Boolean validateCurrentPos() {
        if (position.y < bounds.get("minY")) {
            velocity.y = Math.abs(velocity.y);
            return false;
        } else if (position.y > bounds.get("maxY") - helicopter.getHeight()) {
            velocity.y = -Math.abs(velocity.y);
            return false;
        }
        if (position.x < bounds.get("minX")) {
            velocity.x = Math.abs(velocity.x);
            return false;
        } else if (position.x > bounds.get("maxX") - helicopter.getWidth()) {
            velocity.x = -Math.abs(velocity.x);
            return false;
        }
        return true;
    }

    public void update(float dt) {
        if (position.y > 0) {
            velocity.add(0, 0, 0);
        }
        velocity.scl(dt);
        position.add(velocity.x, velocity.y, 0);
        validateCurrentPos();

        velocity.scl(1 / dt);
    }

    public void flyTowards(int x, int y) {
        if (validateCurrentPos()) {
            velocity.y = y - position.y;
            velocity.x = x - position.x;
        }
    }

    public void flyRandom() {
        final int speed = 1000;
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                // ensures constant velocity in a random direction
                velocity.x = new Random().nextInt(speed) - speed / 2;
                velocity.y = new Random().nextBoolean() ? -(speed / 2 - (Math.abs(velocity.x))) : speed / 2 - (Math.abs(velocity.x));
            }
        }, 0, 3 * 1000);
    }
}
