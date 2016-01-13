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
    int WINDOW_WIDTH = 1080;
    int WINDOW_HEIGHT = 720;
    int GROUND_HEIGHT = 700;

    int ENEMY_ROW = 4;
    int ENEMY_COL = 6;
    int ENEMY_NB = ENEMY_ROW * ENEMY_COL;
    int ENEMY_BASE_X_SPEED = 2;
    int ENEMY_SCORE = 500;

    int PLAYER_START_X_POS = 515;
    int PLAYER_START_Y_POS = 670;
    int PLAYER_SPEED = 5;
    int PLAYER_HEALTH = 3;

    int LASER_SPEED = 8;

    int DELAY = 17;
}
