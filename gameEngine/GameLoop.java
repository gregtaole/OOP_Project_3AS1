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
import java.util.ArrayList;

import resources.TextureReference;

public class GameLoop implements TextureReference, GameConstants
{
    private Player player;
    private ArrayList<Enemy> enemies;
    private Laser testLaser;
    
    public GameLoop()
    {
        this.player = new Player(PLAYER_TEXTURE);
        this.enemies = new ArrayList();
        Enemy test = new Enemy(ENEMY_TEXTURE);
        System.out.println("Enemy width : " + test.getWidth() + ", height : " + test.getHeight());
        for(int j = 0 ; j < ENEMY_COL ; ++j)
        {
            for(int i = 0 ; i < ENEMY_ROW ; ++i)
            {
                System.out.println("Creating enemy...");
                Enemy tmp = new Enemy(ENEMY_TEXTURE);
                tmp.setXPos(344 + j * (tmp.getWidth() + 20));
                tmp.setYPos(42 + i * (tmp.getHeight() + 10));
                enemies.add(tmp);
            }
        }

        testLaser = new Laser(ENELY_LASER_TEXTURE);
        testLaser.setXPos(540);
        testLaser.setYPos(360);

    }

    public Laser getTestLaser()
    {
        return this.testLaser;
    }

    public Player getPlayer()
    {
        return this.player;
    }

    public ArrayList<Enemy> getEnemies()
    {
        return this.enemies;
    }
}
