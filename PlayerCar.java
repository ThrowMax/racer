package com.javarush.games.racer;

import com.javarush.games.racer.road.RoadManager;

public class PlayerCar extends GameObject {
    public  int speed = 1;
    private static int playerCarHeight = ShapeMatrix.PLAYER.length;
    private Direction direction;

    public PlayerCar() {
        super(RacerGame.WIDTH / 2 + 2, RacerGame.HEIGHT - playerCarHeight - 1, ShapeMatrix.PLAYER);
    }
   public void setDirection(Direction direction){
        this.direction = direction;
   }

    public Direction getDirection() {
        return direction;
    }
    public void move(){
        if(direction == direction.LEFT){
            x--;
        }
        else if(direction == direction.RIGHT){
            x++;
        }
        if(x==RacerGame.WIDTH) {
            x = RacerGame.WIDTH - 1;
        }
            if(x==RacerGame.WIDTH-RacerGame.WIDTH-width){
                x=RacerGame.WIDTH-RacerGame.WIDTH-width+1;
            }
    }
    public void stop(){
        matrix = ShapeMatrix.PLAYER_DEAD;
    }
}
