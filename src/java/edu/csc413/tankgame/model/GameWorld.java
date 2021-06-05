package edu.csc413.tankgame.model;

import java.util.*;

/**
 * GameWorld holds all of the model objects present in the game. GameWorld tracks all moving entities like tanks and
 * shells, and provides access to this information for any code that needs it (such as GameDriver or entity classes).
 */
public class GameWorld {
    // TODO: Implement. There's a lot of information the GameState will need to store to provide contextual information.
    //       Add whatever instance variables, constructors, and methods are needed.
    private List<Entity> entities;

    public GameWorld() {
        entities = new ArrayList<>();
    }

    /** Returns a list of all entities in the game. */
    public List<Entity> getEntities() {
        return entities;
    }

    /** Adds a new entity to the game. */
    public void addEntity(Entity entity) {
        entities.add(entity);
    }

    /** Returns the Entity with the specified ID. */
    public Entity getEntity(String id) {
        for (Entity entity: getEntities()){
            if(entity.getId().equals(id)){
                return entity;
            }
        }
        return null;
    }

    /** Removes the entity with the specified ID from the game. */
    public void removeEntity(String id) {
        for (Entity entity: getEntities()){
            if(entity.getId().equals(id)){
                entities.remove(id);
            }
        }
    }

    List<Entity> shell = new ArrayList<>();

    public void addShell(Entity entity) {
        shell.add(entity);
    }
    public List<Entity> getShells() {
        return shell;
    }
    public void removeShell(Entity shell) {
        for (Entity entity: getEntities()){
            if(entity.getId().equals(shell)){
                entities.remove(shell);
            }
        }
    }

    List<Entity> tempShell = new ArrayList<>();

    public List<Entity> getTempShell() {
        return shell;
    }
    public void addTempShell(Entity shell) {
        tempShell.add(shell);
    }
    public void removeTempShell(Entity shell){
        for (Entity entity: getEntities()){
            if(entity.getId().equals(shell)){
                entities.remove(shell);
            }
        }
    }
}
