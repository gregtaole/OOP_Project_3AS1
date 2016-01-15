/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameEngine;

/**
 *Contains various constants about the game window and the game itself.
 */
public class GameConstants
{
    /**
     * Window width.
     */
    public static int WINDOW_WIDTH = 1080;

    /**
     * Window height.
     */
    public static int WINDOW_HEIGHT = 720;

    /**
     * Ground height.
     */
    public static int GROUND_HEIGHT = 700;

    /**
     * Number of enemies in a column.
     */
    public static int ENEMY_ROW = 4;

    /**
     * Number of enemies in  a row.
     */
    public static int ENEMY_COL = 6;

    /**
     * Total number of enemies.
     */
    public static int ENEMY_NB = ENEMY_ROW * ENEMY_COL;

    /**
     * Enemies base horizontal speed.
     */
    public static int ENEMY_BASE_X_SPEED = 2;

    /**
     * Number of points earned by killing each enemies.
     */
    public static final int ENEMY_SCORE = 500;

    /**
     * Each game turn an enemy has 1/ENEMY_BOMB_CHANCE to fire.
     */
    public static int ENEMY_BOMB_CHANCE = 2000;

    /**
     * Player's starting horizontal position.
     */
    public static int PLAYER_START_X_POS = 515;

    /**
     * Player's vertical position.
     */
    public static int PLAYER_START_Y_POS = 670;

    /**
     * Player's movement speed.
     */
    public static int PLAYER_SPEED = 5;

    /**
     * Player's number of health point.
     */
    public static final int PLAYER_HEALTH = 3;


    /**
     * Speed of a laser.
     */
    public static int LASER_SPEED = 8;


    /**
     * Determines game speed. Higher values results in a slower game.
     */
    public static final int DELAY = 24;
}
