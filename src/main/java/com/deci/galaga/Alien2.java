package com.deci.galaga;

public class Alien2 extends GObject {
    private static final String IMG_URL = "https://raw.githubusercontent.com/jsvana/galaga/master/assets/images/enemy1.png";


    Alien2() {
        super(IMG_URL);
        super.setY(100);
        super.setX(10);
        isAlive=true;

    }

    boolean isAlive() {
        return isAlive;
    }


    void draw() {
        GalagaEngine.instance.image(this.getGameImg(), this.getX(), this.getY());
    }

    @Override
    void update() {
    while(x<800 && X>0) {
        while(y > x - 5 || y < x + 5) {
            while (y > x - 5) {
                y--;
            }

            while (y < x + 5) {
                y++;
            }
        }
    }


    }

    @Override
    void manifest() {
        if (isAlive)
            super.manifestInternal(this);
    }

    @Override
    void move(MovementTypes mt) {

    }

    @Override
    void handleKey(final char c) {

    }

    void explode() {
        SequentialImage.create(Assets.EG_GITHUB_ASSETS_ROOT, "explosion_f2.png", "explosion_f3.png", "explosion_f4.png").animate(getPoint());
    }

    @Override
    void destroy() {
        if (isAlive) {
            getSound().play();
            isAlive = false;


        }


    }
    }
//test
