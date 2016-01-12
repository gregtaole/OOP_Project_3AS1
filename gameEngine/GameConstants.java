/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameEngine;

/**
 *
 * @author dinervoid
 */
public interface GameConstants
{
    public final int WINDOW_WIDTH = 1080;
    public final int WINDOW_HEIGHT = 720;
    public final int GROUND_HEIGHT = 700;

    public final int ENEMY_ROW = 4;
    public final int ENEMY_COL = 6;
    public final int ENEMY_NB = ENEMY_ROW * ENEMY_COL;
    public final int ENEMY_BASE_X_SPEED = 2;
    public final int ENEMY_SCORE = 500;

    public final int PLAYER_START_X_POS = 515;
    public final int PLAYER_START_Y_POS = 670;
    public final int PLAYER_SPEED = 5;
    public final int PLAYER_HEALTH = 3;

    public final int LASER_SPEED = 8;

    public final int DELAY = 17;
}
