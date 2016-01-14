/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package resources;

/**
 *
 * @author dinervoid
 */
public interface ResourceReference
{
    String TEXTURE_SHEET = "/resources/sheet.png";
    String TEXTURE_ATLAS = "/resources/sheet.xml";
    String GUI_SHEET = "/resources/uipackSpace_sheet.png";
    String GUI_ATLAS = "/resources/uipackSpace_sheet.xml";
    String FONT = "/resources/fonts/kenvector_future.ttf";

    String BLUE_BUTTON = "buttonBlue.png";

    String PLAYER_TEXTURE = "playerShip1_blue.png";
    String PLAYER_LASER_TEXTURE = "laserBlue01.png";
    String PLAYER_HEALTH_TEXTURE = "playerLife1_blue.png";

    String ENEMY_TEXTURE = "enemyRed1.png";
    String ENEMY_LASER_TEXTURE = "laserRed01.png";

    String BGM_FILE = "/resources/sound/bgm.wav";
    String SFX_ENEMY_EXPLOSION = "/resources/sound/explosion_enemy_16.wav";
    String SFX_ENEMY_LASER = "/resources/sound/laser_enemy_16.wav";
    String SFX_PLAYER_EXPLOSION = "/resources/sound/explosion_player_16.wav";
    String SFX_PLAYER_LASER = "/resources/sound/laser_player_16.wav";
}
