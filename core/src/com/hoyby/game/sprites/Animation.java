package com.hoyby.game.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Array;

public class Animation {
    private final Array<Texture> frames;
    private final float maxFrameTime;
    private float currentFrameTime;
    private int frame;

    public Animation(float cycleTime) {
        frames = new Array<>();
        frames.add(new Texture("helicopter/heli1.png"));
        frames.add(new Texture("helicopter/heli2.png"));
        frames.add(new Texture("helicopter/heli3.png"));
        frames.add(new Texture("helicopter/heli4.png"));

        maxFrameTime = cycleTime / frames.size;
        frame = 0;
    }

    public void update(float dt) {
        currentFrameTime += dt;
        if (currentFrameTime > maxFrameTime) {
            frame++;
            currentFrameTime = 0;
        }
        if (frame >= frames.size) {
            frame = 0;
        }
    }

    public Texture getFrame() {
        return frames.get(frame);
    }

    public void dispose() {
        for (Texture frame : frames) {
            frame.dispose();
        }
    }

}
