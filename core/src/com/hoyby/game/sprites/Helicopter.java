package com.hoyby.game.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector3;
import com.hoyby.game.MyHeliGame;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class Helicopter {
    private final Vector3 position;
    private final Vector3 velocity;

    private final Texture helicopter;

    public Helicopter(int x, int y) {
        position = new Vector3(x, y, 0);
        velocity = new Vector3(0, 0, 0);
        helicopter = new Texture("helicopter/heli1.png");
    }

    public void update(float dt) {
        if (position.y > 0) {
            velocity.add(0, 0, 0);
        }
        velocity.scl(dt);
        position.add(velocity.x, velocity.y, 0);
        if (position.y < 0) {
            velocity.y = Math.abs(velocity.y);
        } else if (position.y > (MyHeliGame.HEIGHT) - helicopter.getHeight()) {
            velocity.y = -Math.abs(velocity.y);
        }
        if (position.x < 0) {
            velocity.x = Math.abs(velocity.x);
        } else if (position.x > (MyHeliGame.WIDTH) - helicopter.getWidth()) {
            velocity.x = -Math.abs(velocity.x);
        }

        velocity.scl(1 / dt);
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

    public void fly() {
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                // ensures constant velocity in a random direction
                velocity.x = new Random().nextInt(200) - 100;
                velocity.y = new Random().nextBoolean() ? -(100 - (Math.abs(velocity.x))) : 100 - (Math.abs(velocity.x));


            }
        }, 0, 3 * 1000);
    }
}
