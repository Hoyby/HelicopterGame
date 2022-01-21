package com.hoyby.game.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Array;

import java.io.File;
import java.util.Objects;

public class Animation {
    private final Array<Texture> frames;
    private final float maxFrameTime;
    private final int frameCount;
    private float currentFrameTime;
    private int frame;

    public Animation(float cycleTime) {
        frames = new Array<>();
        String[] framePaths = Objects.requireNonNull(new File("./android/assets/helicopter/").list());
        for (String pathname : framePaths) {
            frames.add(new Texture("helicopter/" + pathname));
        }
        this.frameCount = framePaths.length;
        maxFrameTime = cycleTime / frameCount;
        frame = 0;
    }

    public void update(float dt) {
        currentFrameTime += dt;
        if (currentFrameTime > maxFrameTime) {
            frame++;
            currentFrameTime = 0;
        }
        if (frame >= frameCount) {
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
