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

public class GameLoop implements TextureReference
{
    private Player player;
    private ArrayList<Enemy> enemies;
    
    public GameLoop()
    {
        this.player = new Player(PLAYER_TEXTURE);
        this.enemies = new ArrayList();
    }
    
    public Player getPlayer()
    {
        return this.player;
    }
}
