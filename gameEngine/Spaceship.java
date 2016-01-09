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
public class Spaceship extends SpriteObject
{
    private boolean destroyed;
    private int speed;
    
    public Spaceship(String shipType)
    {
        super(shipType);
        this.destroyed = false;
        this.speed = 10;
    }
    
    public boolean getDestroyed()
    {
        return this.destroyed;
    }
    
    public void setDestroyed(boolean newStatus)
    {
        this.destroyed = newStatus;
    }

    public void shoot()
    {
        
    }
    
    public int getSpeed()
    {
        return this.speed;
    }
    
    public void setSpeed(int newSpeed)
    {
        this.speed = newSpeed;
    }
}
