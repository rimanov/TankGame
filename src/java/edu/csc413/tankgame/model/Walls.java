package edu.csc413.tankgame.model;

import edu.csc413.tankgame.Constants;

public class Walls extends Entity{
    public Walls(String id, double x, double y, double angle, double health){
        super(id, x, y, angle, health);
    }

    @Override
    public double getX() {
        return x;
    }

    @Override
    public double getY() {
        return y;
    }

    @Override
    public double getHealth() {
        return health;
    }

    @Override
    public void setHealth(){
        health--;
    }

    @Override
    public void setX(double x) {
    }

    @Override
    public double getXBound() {
        return getX() + 30.0;
    }

    @Override
    public double getYBound() {
        return getY() + 30.0;
    }

    @Override
    public void setY(double y) {

    }

    @Override
    public void move(GameWorld gameWorld){

    }
    @Override
    public void checkBounds(GameWorld gameWorld) {

    }

}
