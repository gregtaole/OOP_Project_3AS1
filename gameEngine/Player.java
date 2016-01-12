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
    private Laser shot;
    
    public Player(String shipType)
    {
        super(shipType);
        this.speed = PLAYER_SPEED;
        this.shot = new Laser(PLAYER_LASER_TEXTURE);
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

    public void shoot()
    {
        if(!shot.getVisible())
        {
            shot.setXPos(this.xPos + this.getWidth() / 2);
            shot.setYPos(this.yPos);
            shot.setVisible(true);
            shot.setDestroyed(false);
        }
    }
    
    public void keyPressed(KeyEvent e)
    {
        int key = e.getKeyCode();

        if(key == KeyEvent.VK_LEFT)
        {
            dx = -this.speed;
        }

        if(key == KeyEvent.VK_RIGHT)
        {
            dx = this.speed;
        }

        if(key == KeyEvent.VK_SPACE)
        {
            this.shoot();
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

    public Laser getShot()
    {
        return this.shot;
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
