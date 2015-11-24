package com.kilobolt.gameworld;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.kilobolt.gameobjects.Bird;
import com.kilobolt.gameobjects.ScrollHandler;
import com.kilobolt.zbHelpers.AssetLoader;




public class GameWorld {

    private Bird bird;
    private ScrollHandler scroller;
    private Rectangle ground;
    private int score = 0;
    private float runTime = 0;
    private int midPointY;

    private GameState currentState;





    public enum GameState {

        READY, RUNNING, GAMEOVER, HIGHSCORE, MENU

    }

    public GameWorld(int midPointY) {
        currentState = GameState.MENU;
        this.midPointY=midPointY;
        ground = new Rectangle(0, midPointY + 66, 136, 11);
        bird = new Bird(33, midPointY-5, 17, 12);
        scroller = new ScrollHandler(this, midPointY+66);

    }

    public void updateRunning(float delta) {
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
            currentState=GameState.GAMEOVER;

            if(score > AssetLoader.getHighScore()){
                AssetLoader.setHighScore(score);
                currentState = GameState.HIGHSCORE;
            }
        }


    }



    public void update(float delta){
        runTime += delta;
        switch(currentState){
            case READY:
            case MENU:
                updateReady(delta);
                break;
            case RUNNING:
                updateRunning(delta);
                break;
            default:
                break;
        }
    }

    private void updateReady(float delta){
        bird.updateReady(runTime);
        scroller.updateReady(delta);
    }

    public void start(){

        currentState = GameState.RUNNING;
    }

    public void restart(){
        currentState = GameState.READY;
        score = 0;
        bird.onRestart(midPointY-5);
        scroller.onRestart();
        currentState = GameState.READY;
    }

    public Bird getBird() {
        return bird;

    }

    public int getMidPointY() {
        return midPointY;
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

    public boolean isHighScore() {
        return currentState == GameState.HIGHSCORE;
    }

    public boolean isReady(){
        return currentState == GameState.READY;
    }

    public void ready() {
        currentState = GameState.READY;
    }



    public boolean isGameOver(){

        return currentState == GameState.GAMEOVER;
    }

    public boolean isMenu() {
        return currentState == GameState.MENU;
    }

    public boolean isRunning() {
        return currentState == GameState.RUNNING;
    }

}

