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
public class Player extends  Spaceship implements GameConstants
{
    private int healthPoints;
    private int dx;
    
    public Player(String shipType)
    {
        super(shipType);
        this.healthPoints = PLAYER_HEALTH;
        this.xPos = PLAYER_START_X_POS;
        this.yPos = PLAYER_START_Y_POS;
        this.resizeTexture(0.5);
        dx = 0;
    }

    public void move()
    {
        this.xPos += dx;

        if(xPos >= WINDOW_WIDTH - 2 * getWidth())
        {
            this.xPos = WINDOW_WIDTH - 2 * getWidth();
        }
        else if(xPos <= getWidth())
        {
            this.xPos = getWidth();
        }
    }
    
    public void keyPressed(KeyEvent e)
    {
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT)
        {
            dx = -PLAYER_SPEED;
        }

        if (key == KeyEvent.VK_RIGHT)
        {
            dx = PLAYER_SPEED;
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
