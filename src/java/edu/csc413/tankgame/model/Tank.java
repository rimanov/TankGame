package edu.csc413.tankgame.model;

import edu.csc413.tankgame.Constants;

/** Entity class representing all tanks in the game. */
public abstract class Tank extends Entity {
    // TODO: Implement. A lot of what's below is relevant to all Entity types, not just Tanks. Move it accordingly to
    //       Entity class.

    public Tank(String id, double x, double y, double angle, double health) {
       super(id, x, y, angle, health);
    }

    // TODO: The methods below are provided so you don't have to do the math for movement. You should call these methods
    //       from the various subclasses of Entity in their implementations of move.


    // The following methods will be useful for determining where a shell should be spawned when it
    // is created by this tank. It needs a slight offset so it appears from the front of the tank,
    // even if the tank is rotated. The shell should have the same angle as the tank.
    protected double getShellX() {
        return getX() + Constants.TANK_WIDTH / 2 + 45.0 * Math.cos(getAngle()) - Constants.SHELL_WIDTH / 2;
    }

    protected double getShellY() {
        return getY() + Constants.TANK_HEIGHT / 2 + 45.0 * Math.sin(getAngle()) - Constants.SHELL_HEIGHT / 2;
    }

    protected void fireShell(GameWorld gameWorld){
        Shell shell = new Shell(getShellX(),getShellY(),getAngle(), 1);
        gameWorld.addShell(shell);
    }
    @Override
    public void checkBounds(GameWorld gameWorld){
        if(getX()<Constants.TANK_X_LOWER_BOUND){
            x = Constants.TANK_X_LOWER_BOUND;
        }
        if(getX()>Constants.TANK_X_UPPER_BOUND){
            x = Constants.TANK_X_UPPER_BOUND;
        }
        if(getY()<Constants.TANK_Y_LOWER_BOUND){
            y = Constants.TANK_Y_LOWER_BOUND;
        }
        if(getY()>Constants.TANK_Y_UPPER_BOUND){
            y = Constants.TANK_Y_UPPER_BOUND;
        }
    }

    @Override
    public double getXBound() {
        return getX()+55.0;
    }


    @Override
    public double getYBound() {
        return getY()+55.0;
    }

    public double getHealth() {
        return health;
    }

    public void setHealth(){
        health--;
    }
}
