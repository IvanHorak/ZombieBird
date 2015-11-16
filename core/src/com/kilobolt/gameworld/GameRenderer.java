package com.kilobolt.gameworld;

import com.badlogic.gdx.Gdx;

/**
 * Created by ivan- on 16.11.2015..
 */
public class GameRenderer {

    private GameWorld myWorld;

    public GameRenderer(GameWorld world) {
        myWorld=world;

    }

    public void render() {

        Gdx.app.log("GameRenderer","render");
    }

}
