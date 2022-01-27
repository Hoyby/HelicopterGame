package com.hoyby.game.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector3;

import java.util.HashMap;
import java.util.Random;

public class Helicopter {
    private final Vector3 position;
    private final Vector3 velocity;
    private final HashMap<String, Integer> bounds;
    private final Animation helicopterAnimation;


    public Helicopter(int x, int y, HashMap<String, Integer> bounds) {
        position = new Vector3(x, y, 0);
        velocity = new Vector3(0, 0, 0);
        helicopterAnimation = new Animation(0.5f);
        this.bounds = bounds;
    }

    public Vector3 getPosition() {
        return position;
    }

    public Texture getTexture() {
        return helicopterAnimation.getFrame();
    }

    public Vector3 getVelocity() {
        return velocity;
    }

    private Boolean validateCurrentPos() {
        if (position.y < bounds.get("minY")) {
            velocity.y = Math.abs(velocity.y);
            return false;
        } else if (position.y > bounds.get("maxY") - helicopterAnimation.getFrame().getHeight()) {
            velocity.y = -Math.abs(velocity.y);
            return false;
        }
        if (position.x < bounds.get("minX")) {
            velocity.x = Math.abs(velocity.x);
            return false;
        } else if (position.x > bounds.get("maxX") - helicopterAnimation.getFrame().getWidth()) {
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
        helicopterAnimation.update(dt);

        velocity.scl(1 / dt);
    }

    public boolean collides(Helicopter other) {
        if (other.getPosition().x > position.x + helicopterAnimation.getFrame().getWidth() || other.getPosition().x + other.getTexture().getWidth() < position.x) {
            return false;
        } else if (other.getPosition().y > position.y + helicopterAnimation.getFrame().getHeight() || other.getPosition().y + other.getTexture().getHeight() < position.y) {
            return false;
        } else {
            other.getVelocity().x = (other.getPosition().x - position.x) * 3;
            other.getVelocity().y = (other.getPosition().y - position.y) * 3;
        }
        return true;

    }

    public void flyTowards(float x, float y) {
        if (validateCurrentPos()) {
            velocity.y = y - position.y;
            velocity.x = x - position.x;
        }
    }

    public Vector3 getRandomVelocity() {
        final int speed = new Random().nextInt(800) + 200;
        return new Vector3(new Random().nextInt(speed) - speed / 2f, new Random().nextBoolean() ? -(speed / 2f - (Math.abs(velocity.x))) : speed / 2f - (Math.abs(velocity.x)), 0);
    }

    public void flyRandom() {
        // ensures constant velocity in a random direction
        Vector3 newVelocity = getRandomVelocity();
        velocity.x = newVelocity.x;
        velocity.y = newVelocity.y;
    }

    public void dispose() {
        helicopterAnimation.dispose();
    }
}
