package com.javarush.games.racer.road;

import com.javarush.engine.cell.Game;
import com.javarush.games.racer.GameObject;
import com.javarush.games.racer.PlayerCar;
import com.javarush.games.racer.RacerGame;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class RoadManager {
    public static final int LEFT_BORDER = RacerGame.ROADSIDE_WIDTH;
    public static final int RIGHT_BORDER = RacerGame.WIDTH - RacerGame.ROADSIDE_WIDTH;
    private static final int FIRST_LANE_POSITION = 16;
    private static final int FOURTH_LANE_POSITION = 44;
    private List<RoadObject> items = new ArrayList<>();
    private static final int PLAYER_CAR_DISTANCE =12;
    private int passedCarsCount= 0;
    private RoadObject createRoadObject(RoadObjectType type, int x, int y) {
        if (type == RoadObjectType.THORN) {
            return new Thorn(x, y);
        } else if (type == RoadObjectType.DRUNK_CAR) {
            return new MovingCar(x, y);
        } else {
            return new Car(type, x, y);
        }
    }

    private void addRoadObject(RoadObjectType type, Game game) {
        int x = game.getRandomNumber(FIRST_LANE_POSITION-10, FOURTH_LANE_POSITION+10);
        int y = -1 * RoadObject.getHeight(type);
        RoadObject roadObject = createRoadObject(type, x, y);
        if (isRoadSpaceFree(roadObject)) {
            items.add(roadObject);
        }
    }

    public void draw(Game game) {
        for (int i = 0; i < items.size(); i++) {
            items.get(i).draw(game);
        }
    }

    public void move(int boost) {
        for (int i = 0; i < items.size(); i++) {
            items.get(i).move(boost + items.get(i).speed,items);
        }
        deletePassedItems();
    }

    private void generateThorn(Game game) {
        if ((game.getRandomNumber(100)) < 10 && (!isThornExists())) {
            addRoadObject(RoadObjectType.THORN, game);
        }
    }

    public void generateNewRoadObjects(Game game) {
        generateThorn(game);
        generateRegularCar(game);
        generateMovingCar(game);
    }

    private boolean isThornExists() {
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).type == RoadObjectType.THORN) {
                return true;
            }
        }
        return false;
    }

    private void deletePassedItems() {
        List<RoadObject> itemX = new ArrayList<>(items);
        for (RoadObject ro : itemX) {
            if (ro.y >= RacerGame.HEIGHT) {
                items.remove(ro);
                if(!(ro instanceof Thorn)){
                    passedCarsCount++;
                }
            }

        }
    }

    public boolean checkCrush(PlayerCar playerCar) {
         for(RoadObject el : items){
            if (el.isCollision(playerCar))
                return true;
        }
        return false;
    }
    private void generateRegularCar(Game game) {
        if (game.getRandomNumber(100) < 30) {
            int carTypeNumber = game.getRandomNumber(4);
            addRoadObject(RoadObjectType.values()[carTypeNumber], game);
        }
    }
    private boolean isRoadSpaceFree(RoadObject object){
        for (RoadObject element : items) {
            if (element.isCollisionWithDistance(object, PLAYER_CAR_DISTANCE)) {
                return false;
            }
        }
        return true;
    }
    private  boolean isMovingCarExists(){
        for(RoadObject roadObject : items){
            if(roadObject instanceof MovingCar){
                return true;
            }
        }
        return false;
    }
    private  void generateMovingCar(Game game){
        int random = game.getRandomNumber(100);
        if(random < 10 && isMovingCarExists() == false){
         addRoadObject(RoadObjectType.DRUNK_CAR,game);
        }
    }

    public int getPassedCarsCount() {
        return passedCarsCount;
    }
}

