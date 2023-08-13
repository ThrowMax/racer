package com.javarush.games.racer;

import com.javarush.games.racer.GameObject;
import com.javarush.games.racer.RacerGame;
import com.javarush.games.racer.ShapeMatrix;
import com.javarush.games.racer.road.RoadObject;

public class FinishLine extends GameObject {
    private boolean isVisible = false;
    public FinishLine() {
   super(RacerGame.ROADSIDE_WIDTH,-1 * ShapeMatrix.FINISH_LINE.length,ShapeMatrix.FINISH_LINE);
    }

    public void move(int boost){
        if(!isVisible){
        }else{
            y+=boost;
        }
    }
    public void show(){
        isVisible = true;
    }
    public boolean isCrossed(PlayerCar playerCar){
        if(playerCar.y < y){
            return true;
        }
        return false;
    }
}
