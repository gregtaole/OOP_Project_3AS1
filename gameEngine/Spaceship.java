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
    
    public Spaceship(String shipType)
    {
        super(shipType);
        destroyed = false;
    }
    
    public boolean getDestroyed()
    {
        return this.destroyed;
    }
    
    public void setDestroyed(boolean newStatus)
    {
        this.destroyed = newStatus;
    }
    
    public void moveLeft()
    {
        
    }
    
    public void moveRight()
    {
        
    }
    
    public void shoot()
    {
        
    }
}
