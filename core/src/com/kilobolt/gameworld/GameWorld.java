package com.kilobolt.gameworld;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.kilobolt.gameobjects.Bird;
import com.kilobolt.gameobjects.ScrollHandler;
import com.kilobolt.zbHelpers.AssetLoader;




public class GameWorld {

    private int score = 0;

    private Rectangle ground;

    private Bird bird;
    private ScrollHandler scroller;

    public GameWorld(int midPointY) {

        ground = new Rectangle(0, midPointY + 66, 136, 11);
        bird = new Bird(33, midPointY-5, 17, 12);
        scroller = new ScrollHandler(this, midPointY+66);
    }

    public void update(float delta) {
        if(delta > .15f){
            delta = .15f;
        }
        bird.update(delta);
        scroller.update(delta);

        if( scroller.collides(bird) && bird.isAlive()){
            scroller.stop();
            bird.die();
            AssetLoader.dead.play();
        }

        if(Intersector.overlaps(bird.getBoundingCircle(), ground)){
            scroller.stop();
            bird.die();
            bird.decelerate();
        }


    }

    public Bird getBird() {
        return bird;

    }

    public ScrollHandler getScroller(){
        return scroller;
    }

    public int getScore() {
        return score;
    }

    public void addScore(int increment){
        score += increment;
    }


}
