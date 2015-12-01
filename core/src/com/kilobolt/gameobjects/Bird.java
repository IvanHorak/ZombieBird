package com.kilobolt.gameobjects;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import com.kilobolt.zbHelpers.AssetLoader;

public class Bird {

    private float originalY;

    private boolean isAlive;

    private Vector2 position;
    private Vector2 velocity;
    private Vector2 acceleration;

    private float rotation;
    private int width;
    private int height;

    private Circle boundingCircle;

    public Bird(float x, float y, int width, int height) {
        isAlive=true;
        this.width = width;
        this.height = height;
        this.originalY = y;
        position = new Vector2(x, y);
        velocity = new Vector2(0, 0);
        acceleration = new Vector2(0, 460);
        boundingCircle = new Circle();
    }



    public void update(float delta) {

        velocity.add(acceleration.cpy().scl(delta));

//      maksimalna brzina po y osi neka bude 200
        if (velocity.y > 200) {
            velocity.y = 200;
        }

//      ovaj IF sprječava da ptica leti izvan ekrana
        if (position.y < -13){
            position.y = -13;
            velocity.y = 0;
        }

        position.add(velocity.cpy().scl(delta));

//      Postavi centar kruga (9, 6) u odnosu na lika.
//      Postavi radijus kruga na 6.5f;
        boundingCircle.set(position.x + 9, position.y + 6, 6.5f);

//      ukoliko lik JE u usponu, rotiraj lika suprotno od kazaljke na satu
        if (velocity.y < 0) {
            rotation -= 600 * delta;
//        sprječavanje daljnje rotacije lika iznad dozvoljene granice
            if (rotation < -20) {
                rotation = -20;
            }
        }

     /*ako JE u slobodnom padu ili lik NIJE živ,
     rotiraj za 90 stupnjeva prema dolje i stani   */
        if (isFalling() || !isAlive) {
            rotation += 480 * delta;
            if (rotation > 90) {
                rotation = 90;
            }


        }

    }





    public boolean isFalling() {
        return velocity.y > 110;
    }

    public boolean shouldntFlap() {

        return velocity.y > 70 || !isAlive;
    }

    public void onClick() {
        if(isAlive){
            velocity.y = -140;
            AssetLoader.flap.play();

        }
    }

    public void die(){
        isAlive=false;
        velocity.y=0;
    }

    public void decelerate(){
        acceleration.y=0;
    }

    public float getX() {
        return position.x;
    }

    public float getY() {
        return position.y;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public float getRotation() {
        return rotation;
    }

    public Circle getBoundingCircle() {
        return boundingCircle;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public void onRestart(int y){
        rotation = 0;
        position.y = y;
        velocity.x = 0;
        velocity.y = 0;
        acceleration.x = 0;
        acceleration.y = 460;
        isAlive = true;
    }

    public void updateReady(float runTime) {
        position.y = 2 * (float) Math.sin(7 * runTime) + originalY;
    }


}



