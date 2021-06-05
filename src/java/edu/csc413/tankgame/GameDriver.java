package edu.csc413.tankgame;

import edu.csc413.tankgame.model.*;
import edu.csc413.tankgame.view.*;

import java.awt.event.ActionEvent;


public class GameDriver {
    private final MainView mainView;
    private final RunGameView runGameView;
    private final GameWorld gameWorld;

    public GameDriver() {
        mainView = new MainView(this::startMenuActionPerformed);
        runGameView = mainView.getRunGameView();
        gameWorld = new GameWorld();
    }

    public void start() {
        mainView.setScreen(MainView.Screen.START_GAME_SCREEN);
    }

    private void startMenuActionPerformed(ActionEvent actionEvent) {
        switch (actionEvent.getActionCommand()) {
            case StartMenuView.START_BUTTON_ACTION_COMMAND -> runGame();
            case StartMenuView.EXIT_BUTTON_ACTION_COMMAND -> mainView.closeGame();
            default -> throw new RuntimeException("Unexpected action command: " + actionEvent.getActionCommand());
        }
    }

    private void runGame() {
        mainView.setScreen(MainView.Screen.RUN_GAME_SCREEN);
        Runnable gameRunner = () -> {
            setUpGame();
            while (updateGame()) {
                runGameView.repaint();
                try {
                    Thread.sleep(10L);
                } catch (InterruptedException exception) {
                    throw new RuntimeException(exception);
                }
            }
            mainView.setScreen(MainView.Screen.END_MENU_SCREEN);
            resetGame();
        };
        new Thread(gameRunner).start();
    }

    /**
     * setUpGame is called once at the beginning when the game is started. Entities that are present from the start
     * should be initialized here, with their corresponding sprites added to the RunGameView.
     */
    private void setUpGame() {
        Tank playerTank = new PlayerTank(Constants.PLAYER_TANK_ID, Constants.PLAYER_TANK_INITIAL_X, Constants.PLAYER_TANK_INITIAL_Y, Constants.PLAYER_TANK_INITIAL_ANGLE, Constants.PLAYER_TANK_INITIAL_HEALTH);
        Tank aiTank = new DumbAiTank(Constants.AI_TANK_1_ID, Constants.AI_TANK_1_INITIAL_X, Constants.AI_TANK_1_INITIAL_Y, Constants.AI_TANK_1_INITIAL_ANGLE, Constants.DUMBAI_TANK_INITIAL_HEALTH);
        Tank awareAiTank = new AwareAiTank(Constants.AI_TANK_2_ID, Constants.AI_TANK_2_INITIAL_X, Constants.AI_TANK_2_INITIAL_Y, Constants.AI_TANK_2_INITIAL_ANGLE, Constants.AI_TANK_INITIAL_HEALTH);

        gameWorld.addEntity(playerTank);
        gameWorld.addEntity(aiTank);
        gameWorld.addEntity(awareAiTank);


        runGameView.addSprite(playerTank.getId(), RunGameView.PLAYER_TANK_IMAGE_FILE, playerTank.getX(), playerTank.getY(), playerTank.getAngle());
        runGameView.addSprite(aiTank.getId(), RunGameView.AI_TANK_IMAGE_FILE, aiTank.getX(), aiTank.getY(), aiTank.getAngle());
        runGameView.addSprite(awareAiTank.getId(), RunGameView.AI_TANK_IMAGE_FILE, awareAiTank.getX(), awareAiTank.getY(), awareAiTank.getAngle());

        WallInformation.readWalls();
        for(int i = 0; i <WallInformation.readWalls().size(); i++ )
        {
            String wall = "Wall-" + i;
            Walls walls = new Walls(wall, WallInformation.readWalls().get(i).getX(), WallInformation.readWalls().get(i).getY(), 0, 2);
            gameWorld.addEntity(walls);
            runGameView.addSprite(wall, WallInformation.readWalls().get(i).getImageFile(), walls.getX(), walls.getY(), 0
            );
        }
    }

    /**
     * updateGame is repeatedly called in the gameplay loop. The code in this method should run a single frame of the
     * game. As long as it returns true, the game will continue running. If the game should stop for whatever reason
     * (e.g. the player tank being destroyed, escape being pressed), it should return false.
     */
    private boolean updateGame() {
        for (Entity entity : gameWorld.getEntities()) {
            entity.move(gameWorld);
            entity.checkBounds(gameWorld);
        }

        for (Entity shells : gameWorld.getShells()) {
            runGameView.addSprite(shells.getId(), RunGameView.SHELL_IMAGE_FILE, shells.getX(), shells.getY(), shells.getAngle());
            gameWorld.addEntity(shells);
        }
        if(gameWorld.getShells().size() > 0){
            gameWorld.getShells().clear();
        }

        for (Entity shell : gameWorld.getTempShell()) {
            runGameView.removeSprite(shell.getId());
        }
        gameWorld.getTempShell().clear();

        for (Entity entity : gameWorld.getEntities()) {
            runGameView.setSpriteLocationAndAngle(entity.getId(), entity.getX(), entity.getY(), entity.getAngle());
        }
        for(Entity entity1: gameWorld.getEntities() ){
            for(Entity entity2:gameWorld.getEntities()){
                if(checkOverlap(entity1, entity2)){
                    if  (!entity1.getId().equals(entity2.getId())) {
                        handleCollision(entity1, entity2);
                    }

                }
            }
        }
        KeyboardReader keyboard = KeyboardReader.instance();
        if(keyboard.escapePressed()){
            mainView.setScreen(MainView.Screen.END_MENU_SCREEN);
        }
        return true;
    }

    /**
     * resetGame is called at the end of the game once the gameplay loop exits. This should clear any existing data from
     * the game so that if the game is restarted, there aren't any things leftover from the previous run.
     */
    private void resetGame() {
        // TODO: Implement.
        runGameView.reset();
    }
    public void updateHealth(Entity entity){
        entity.decreaseHealth(gameWorld);
        if(entity.getHealth() == 0){
            runGameView.removeSprite(entity.getId());
        }
    }

    public static void main(String[] args) {
        GameDriver gameDriver = new GameDriver();
        gameDriver.start();
    }
    //checks if two entities overlap
    private boolean checkOverlap(Entity entity1, Entity entity2) {
        return entity1.getX() < entity2.getXBound() && entity1.getXBound() > entity2.getX() && entity1.getY() < entity2.getYBound() && entity1.getYBound() > entity2.getY();
    }

    private void handleCollision(Entity entity1, Entity entity2) {
        //tank colliding with a tank
        if (entity1 instanceof Tank && entity2 instanceof Tank) {
            double x1 = entity1.getXBound() - entity2.getX();
            double x2 = entity2.getXBound() - entity1.getX();
            double y1 = entity1.getYBound() - entity2.getY();
            double y2 = entity2.getYBound() - entity1.getY();
            double half_d;

            if (x1 < x2 && x1 < y1 && x1 < y2) {
                half_d = x1 / 2;
                entity1.setX(entity1.getX() - half_d);
                entity2.setX(entity2.getX() + half_d);
            } else if (x2 < x1 && x2 < y1 && x2 < y2) {
                half_d = x2 / 2;
                entity1.setX(entity1.getX() + half_d);
                entity2.setX(entity2.getX() - half_d);
            } else if (y1 < x2 && y1 < x1 && y1 < y2) {
                half_d = y1 / 2;
                entity1.setY(entity1.getY() - half_d);
                entity2.setY(entity2.getY() + half_d);
            } else if (y2 < y1 && y2 < x1 && y2 < x2) {
                half_d = y2 / 2;
                entity1.setY(entity1.getY() + half_d);
                entity2.setY(entity2.getY() - half_d);
            }
        }
        //tank colliding with a wall
        else if (entity1 instanceof Tank && entity2 instanceof Walls) {
            double x1 = entity1.getXBound() - entity2.getX();
            double x2 = entity2.getXBound() - entity1.getX();
            double y1 = entity1.getYBound() - entity2.getY();
            double y2 = entity2.getYBound() - entity1.getY();
            double d;

            if (x1 < x2 && x1 < y1 && x1 < y2) {
                d = x1;
                entity1.setX(entity1.getX() - d);
            } else if (x2 < x1 && x2 < y1 && x2 < y2) {
                d = x2;
                entity1.setX(entity1.getX() + d);
            } else if (y1 < x2 && y1 < x1 && y1 < y2) {
                d = y1;
                entity1.setY(entity1.getY() - d);
            } else if (y2 < y1 && y2 < x1 && y2 < x2) {
                d = y2;
                entity1.setY(entity1.getY() + d);
            }
        }
        //was not able to make this work properly. My idea was to call the addTempShell function so the shell could be added and the sprite would be removed
        //in the update game method after the shell has been added.
        else if (entity1 instanceof Shell && entity2 instanceof Tank) {
            double health = entity2.getHealth() - Constants.DAMAGE;
            if(health <= 0){
                runGameView.removeSprite(entity2.getId());
            }
            gameWorld.addTempShell(entity1);
            runGameView.addAnimation((RunGameView.SHELL_EXPLOSION_ANIMATION), RunGameView.SHELL_EXPLOSION_FRAME_DELAY, entity1.getX(), entity2.getY());

        } else if (entity1 instanceof Shell && entity2 instanceof Walls) {
            double hp = entity2.getHealth() - Constants.DAMAGE;
            if(hp <= 0){
                runGameView.removeSprite(entity2.getId());
            }

            gameWorld.addTempShell(entity1);
            runGameView.addAnimation((RunGameView.SHELL_EXPLOSION_ANIMATION), RunGameView.SHELL_EXPLOSION_FRAME_DELAY, entity1.getX(), entity2.getY());

        } else if(entity1 instanceof Shell || entity2 instanceof Shell){
            gameWorld.addTempShell(entity1);
            gameWorld.addTempShell(entity2);
            runGameView.addAnimation((RunGameView.SHELL_EXPLOSION_ANIMATION), RunGameView.SHELL_EXPLOSION_FRAME_DELAY, entity1.getX(), entity2.getY());
        }
    }

}



