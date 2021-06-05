package edu.csc413.tankgame.model;

import edu.csc413.tankgame.Constants;

/**
 * A general concept for an entity in the Tank Game. This includes everything that can move or be interacted with, such
 * as tanks, shells, walls, power ups, etc.
 */
public abstract class Entity {
    /** All entities can move, even if the details of their move logic may vary based on the specific type of Entity. */
    protected String id;
    protected double x;
    protected double y;
    protected double angle;
    protected double health;

    public Entity(String id, double x, double y, double angle, double health) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.angle = angle;
        this.health = health;
    }
    public abstract void move(GameWorld gameWorld);

    public String getId() {
        return id;
    }

    public abstract void  setX(double x);

    public abstract void  setY(double y);

    public double getX() {
        return x;
    }


    public double getY() {
        return y;
    }

    public double getHealth() {
        return health;
    }

    public void setHealth(){
    }

    public void decreaseHealth(GameWorld gameWorld){
        setHealth();
    }


    public abstract double getXBound();

    public abstract double getYBound();

    public abstract void checkBounds(GameWorld gameWorld);


    public double getAngle() {
        return angle;
    }
    protected void moveForward(double movementSpeed) {
        x += movementSpeed * Math.cos(angle);
        y += movementSpeed * Math.sin(angle);
    }

    protected void moveBackward(double movementSpeed) {
        x -= movementSpeed * Math.cos(angle);
        y -= movementSpeed * Math.sin(angle);
    }

    protected void turnLeft(double turnSpeed) {
        angle -= turnSpeed;
    }

    protected void turnRight(double turnSpeed) {
        angle += turnSpeed;
    }


}
