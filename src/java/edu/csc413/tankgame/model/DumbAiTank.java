package edu.csc413.tankgame.model;

import edu.csc413.tankgame.Constants;
import edu.csc413.tankgame.model.PlayerTank;

public class DumbAiTank extends Tank {
    public DumbAiTank(String id, double x, double y, double angle, double health) {
        super(id, x, y, angle, health);
    }

    private int cooldown = 200;
    @Override
    public void move(GameWorld gameWorld) {
        moveForward(Constants.TANK_MOVEMENT_SPEED);
        turnRight(Constants.TANK_TURN_SPEED);

        cooldown++;
        if (cooldown > 205) {
            fireShell(gameWorld);
            cooldown = 0;
        }
    }
    @Override
    public void setX(double x) {
        this.x = x;

    }

    @Override
    public void setY(double y) {
        this.y = y;
    }

    @Override
    public double getHealth() {
        return health;
    }

    @Override
    public void setHealth(){
        health--;
    }


}
