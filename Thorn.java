package com.javarush.games.racer.road;

import com.javarush.games.racer.road.RoadObject;

public class Thorn extends RoadObject {
    public Thorn(int x,int y){
        super(RoadObjectType.THORN, x, y);
        speed=0;
    }
}
