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
    protected int speed;
    
    public Spaceship(String shipType)
    {
        super(shipType);
        this.speed = 10;
    }

    public int getSpeed()
    {
        return this.speed;
    }
}
