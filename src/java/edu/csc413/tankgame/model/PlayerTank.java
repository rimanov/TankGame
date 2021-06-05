package edu.csc413.tankgame.model;

import edu.csc413.tankgame.Constants;
import edu.csc413.tankgame.KeyboardReader;

public class PlayerTank extends Tank {
    public PlayerTank(String id, double x, double y, double angle, double health) {
        super(id, x, y, angle, health);
    }

    private int cooldown = 200;

    @Override
    public void move(GameWorld gameWorld) {
        KeyboardReader keyboard = KeyboardReader.instance();
        if(keyboard.upPressed()){
            moveForward(Constants.TANK_MOVEMENT_SPEED);
            System.out.println("moving forward");
        }
        if(keyboard.downPressed()){
            moveBackward(Constants.TANK_MOVEMENT_SPEED);
            System.out.println("moving backward");
        }
        if(keyboard.leftPressed()){
            turnLeft(Constants.TANK_TURN_SPEED);
            System.out.println("turning left");
        }
        if(keyboard.rightPressed()){
            turnRight(Constants.TANK_TURN_SPEED);
            System.out.println("turning right");
        }
        if(keyboard.spacePressed()){
            cooldown++;
            if (cooldown > 200) {
                fireShell(gameWorld);
                cooldown = 0;
            }
            System.out.println("shell fired");
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
