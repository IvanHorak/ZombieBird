package com.kilobolt.gameworld;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;


public class GameWorld {

    private Rectangle rect = new Rectangle(0,0,20,20);

    public void update(float delta) {

        rect.x++;
        if (rect.x>137)
            rect.x=0;
        Gdx.app.log("GameWorld","update");
    }

    public Rectangle getRect() {
        return rect;
    }
}
