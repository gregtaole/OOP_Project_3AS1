/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package resources;

/**
 * Class containing various String constants representing paths to resources or texture names.
 */
public class ResourceReference
{
    /**
     * Path to the texture sheet.
     */
    public static final String TEXTURE_SHEET = "/resources/sheet.png";

    /**
     * Path to the texture atlas.
     */
    public static final String TEXTURE_ATLAS = "/resources/sheet.xml";

    /**
     * Path to the GUI texture sheet.
     */
    public static final String GUI_SHEET = "/resources/uipackSpace_sheet.png";

    /**
     * Path to the GUI texture atlas.
     */
    public static final String GUI_ATLAS = "/resources/uipackSpace_sheet.xml";

    /**
     * Path to the font file.
     */
    public static final String FONT = "/resources/fonts/kenvector_future.ttf";


    /**
     * Texture for the blue button.
     */
    public static final String BLUE_BUTTON = "buttonBlue.png";


    /**
     * Player texture file name.
     */
    public static String PLAYER_TEXTURE = "playerShip1_blue.png";

    /**
     * Player health points texture file name.
     */
    public static String PLAYER_HEALTH_TEXTURE = "playerLife1_blue.png";

    /**
     * Player laser texture file name.
     */
    public static final String PLAYER_LASER_TEXTURE = "laserBlue01.png";


    /**
     * Enemy texture file name
     */
    public static final String ENEMY_TEXTURE = "enemyRed1.png";
    /**
     * Enemy laser texture file name
     */
    public static final String ENEMY_LASER_TEXTURE = "laserRed01.png";


    /**
     * Path to the background music file.
     */
    public static final String BGM_FILE = "/resources/sound/bgm.wav";

    /**
     * Path to the enemy explosion sound effect file.
     */
    public static final String SFX_ENEMY_EXPLOSION = "/resources/sound/explosion_enemy_16.wav";

    /**
     * Path to the enemy laser sound effect file.
     */
    public static final String SFX_ENEMY_LASER = "/resources/sound/laser_enemy_16.wav";

    /**
     * Path to the player explosion sound effect file.
     */
    public static final String SFX_PLAYER_EXPLOSION = "/resources/sound/explosion_player_16.wav";

    /**
     * Path to the player laser sound effect file.
     */
    public static final String SFX_PLAYER_LASER = "/resources/sound/laser_player_16.wav";


    /**
     * List of textures available for the player.
     */
    public static final String[] PLAYER_SKIN_LIST = {"playerShip1_blue.png", "playerShip1_green.png", "playerShip1_orange.png", "playerShip1_red.png", "playerShip2_blue.png", "playerShip2_green.png", "playerShip2_orange.png", "playerShip2_red.png", "playerShip3_blue.png", "playerShip3_green.png", "playerShip3_orange.png", "playerShip3_red.png"};

    /**
     * List of player health points textures.
     */
    public static final String[] PLAYER_HEALTH_LIST = {"playerLife1_blue.png", "playerLife1_green.png", "playerLife1_orange.png", "playerLife1_red.png", "playerLife2_blue.png", "playerLife2_green.png", "playerLife2_orange.png", "playerLife2_red.png", "playerLife3_blue.png", "playerLife3_green.png", "playerLife3_orange.png", "playerLife3_red.png"};
}
