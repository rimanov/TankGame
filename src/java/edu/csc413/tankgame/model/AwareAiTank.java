package edu.csc413.tankgame.model;

import edu.csc413.tankgame.Constants;
import edu.csc413.tankgame.model.PlayerTank;

public class AwareAiTank extends Tank {
    public AwareAiTank(String id, double x, double y, double angle, double health) {
        super(id, x, y, angle, health);
    }

    private int cooldown = 200;
    @Override
    public void move(GameWorld gameWorld) {
        Entity playerTank = gameWorld.getEntity(Constants.PLAYER_TANK_ID);

        double dx = playerTank.getX() - getX();
        double dy = playerTank.getY() - getY();

        double angleToPlayer = Math.atan2(dy, dx);
        double angleDifference = getAngle() - angleToPlayer;

        angleDifference -=
                Math.floor(angleDifference / Math.toRadians(360.0) + 0.5)
                        * Math.toRadians(360.0);

        if (angleDifference < -Math.toRadians(3.0)) {
            turnRight(Constants.TANK_TURN_SPEED);
        } else if (angleDifference > Math.toRadians(3.0)) {
            turnLeft(Constants.TANK_TURN_SPEED);
        }
        double d = Math.sqrt(dx*dx+dy*dy);
        if(d > 200) {
            moveForward(Constants.TANK_MOVEMENT_SPEED);
        }

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
