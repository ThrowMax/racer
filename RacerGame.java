package com.javarush.games.racer;

import com.javarush.engine.cell.Game;
import com.javarush.engine.cell.*;
import com.javarush.games.racer.road.RoadManager;

public class RacerGame extends Game {
    public static final int WIDTH = 64;
    public static final int HEIGHT = 64;
    public static final int CENTER_X = WIDTH / 2;
    public static final int ROADSIDE_WIDTH = 14;
    private PlayerCar player;
    private RoadManager roadManager;

    private RoadMarking roadMarking;
    private boolean isGameStopped;
    private FinishLine finishLine;
    private static final int  RACE_GOAL_CARS_COUNT=40;
    private ProgressBar progressBar;
    private int score;

    @Override
    public void initialize() {
        showGrid(false);
        setScreenSize(WIDTH, HEIGHT);
        createGame();
    }

    private void createGame() {
        roadMarking = new RoadMarking();
        player = new PlayerCar();
        roadManager = new RoadManager();
        finishLine = new FinishLine();
        progressBar = new ProgressBar(RACE_GOAL_CARS_COUNT);
        drawScene();
        setTurnTimer(40);
        isGameStopped =false;
        score = 3500;
    }

    private void drawScene() {
        drawField();
        finishLine.draw(this);
        roadMarking.draw(this);
        player.draw(this);
        roadManager.draw(this);
        progressBar.draw(this);
    }

    private void drawField() {
        for (int x = 0; x < WIDTH; x++) {
            for (int y = 0; y < HEIGHT; y++) {
                if (x == CENTER_X) {
                    setCellColor(x, y, Color.WHITE);
                } else if (x >= ROADSIDE_WIDTH && x < WIDTH - ROADSIDE_WIDTH) {
                    setCellColor(x, y, Color.GREY);
                } else {
                    setCellColor(x, y, Color.DARKOLIVEGREEN);
                }
            }
        }
    }

    @Override
    public void setCellColor(int x, int y, Color color) {
        if (x > WIDTH - 1 || x < 0 || y < 0 || y > HEIGHT - 1) {
            return;
        }
        super.setCellColor(x, y, color);
    }

    private void moveAll() {
        roadMarking.move(player.speed);
        player.move();
        roadManager.move(player.speed);
        finishLine.move(player.speed);
        progressBar.move(roadManager.getPassedCarsCount());
    }

    @Override
    public void onTurn(int x) {
        if (roadManager.checkCrush(player)) {
            gameOver();
            drawScene();
            return;
        }
        roadManager.generateNewRoadObjects(this);
           if(roadManager.getPassedCarsCount()>=RACE_GOAL_CARS_COUNT)
                finishLine.show();
         if(finishLine.isCrossed(player)) {
                win();
                drawScene();
                return;
            }
            moveAll();
            score-=5;
            setScore(score);
            drawScene();
    }

    @Override
    public void onKeyPress(Key key) {
        if (key == Key.LEFT) {
            player.setDirection(Direction.LEFT);
        }
        if (key == Key.RIGHT)
            player.setDirection(Direction.RIGHT);
        if(isGameStopped && key == Key.SPACE)
            createGame();
        if(key == Key.UP)
            player.speed= player.speed*2;
    }

    @Override
    public void onKeyReleased(Key key) {
        if (key == Key.LEFT && player.getDirection()==Direction.LEFT) {
            player.setDirection(Direction.NONE);
        } else {
        }
        if (key == Key.RIGHT && player.getDirection()==Direction.RIGHT) {
            player.setDirection(Direction.NONE);
        }  if(key == Key.UP)
            player.speed=1;
    }
    private void gameOver(){
        isGameStopped=true;
        showMessageDialog(Color.WHITE,"Try one more time",Color.RED,78);
        stopTurnTimer();
        player.stop();
    }
    private void win(){
        isGameStopped=true;
        showMessageDialog(Color.WHEAT,"YOU ARE THE BEST!",Color.FORESTGREEN,75);
        stopTurnTimer();
    }
}