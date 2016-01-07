/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameEngine;

import java.awt.event.KeyEvent;

/**
 *
 * @author dinervoid
 */
public class Player extends  Spaceship
{
    private int healthPoints;
    private int dx;
    
    public Player(String shipType)
    {
        super(shipType);
        this.healthPoints = 3;
        dx = 0;
    }
            
    @Override
    public void move()
    {
        this.xPos += dx;
    }
    
    public void keyPressed(KeyEvent e)
    {
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT)
        {
            dx = -10;
        }

        if (key == KeyEvent.VK_RIGHT)
        {
            dx = 10;
        }
    }

    public void keyReleased(KeyEvent e)
    {
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT)
        {
            dx = 0;
        }

        if (key == KeyEvent.VK_RIGHT)
        {
            dx = 0;
        }
    }

    public int getHealthPoints()
    {
        return this.healthPoints;
    }
    
    public int getDx()
    {
        return this.dx;
    }
    
    public void setHealthPoints(int newHealth)
    {
        this.healthPoints = newHealth;
    }
    
    public void setDx(int newDx)
    {
        this.dx = newDx;
    }
}
