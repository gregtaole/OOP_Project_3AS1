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
public class GameLoop
{
    private Player player;
    private Enemy[] enemies;
    
    public GameLoop()
    {
        this.player = new Player("playerShip1_blue.png");
    }
    
    public Player getPlayer()
    {
        return this.player;
    }
}
