package edu.csc413.tankgame.model;

import edu.csc413.tankgame.Constants;

public class Shell extends Entity{
    private static int uniqueId = 0;
    public Shell (double x, double y, double angle, double health){
        super("shell-"+uniqueId, x, y, angle, health);
        uniqueId++;
    }

    @Override
    public void move(GameWorld gameWorld){
        moveForward(Constants.SHELL_MOVEMENT_SPEED);
    }

    @Override
    public void checkBounds(GameWorld gameWorld) {
        for (Entity bringShell : gameWorld.getShells()) {

            if (getX() < Constants.SHELL_X_LOWER_BOUND) {
                gameWorld.removeShell(bringShell);
            }
            if (getX() < Constants.SHELL_X_UPPER_BOUND) {
                gameWorld.removeShell(bringShell);
            }

            if (getY() < Constants.SHELL_Y_LOWER_BOUND) {
                gameWorld.removeShell(bringShell);
            }
            if (getY() > Constants.SHELL_Y_UPPER_BOUND) {
                gameWorld.removeShell(bringShell);

            }
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
    public double getXBound() {
        return getX()+24.0;
    }
    @Override
    public double getYBound() {
        return getY() + 24.0;
    }

    @Override
    public double getHealth() {
        return health;
    }

}
