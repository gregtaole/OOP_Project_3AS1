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
public class Player extends  Spaceship
{
    private int healthPoints;
    
    public Player(String shipType)
    {
        super(shipType);
        this.healthPoints = 3;
    }
            
    public int getHealthPoints()
    {
        return this.healthPoints;
    }
    
    public void setHealthPoints(int newHealth)
    {
        this.healthPoints = newHealth;
    }
}
