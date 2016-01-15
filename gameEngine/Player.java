/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameEngine;

import gui.ResourceReference;
import gui.SoundEffects;

import java.awt.event.KeyEvent;

/**
 * Class representing the player.
 */
public class Player extends SpriteObject
{
    /**
     * Number of health points.
     */
    private int healthPoints;

    /**
     * Player horizontal speed.
     */
    private int dx;

    /**
     * Projectile.
     */
    private Laser shot;

    /**
     * Class constructor.
     * @param shipType String indicating the ship texture.
     */
    public Player(String shipType)
    {
        super(shipType);
        this.speed = GameConstants.PLAYER_SPEED;
        this.shot = new Laser(ResourceReference.PLAYER_LASER_TEXTURE);
        this.healthPoints = GameConstants.PLAYER_HEALTH;
        this.xPos = GameConstants.PLAYER_START_X_POS;
        this.yPos = GameConstants.PLAYER_START_Y_POS;
        this.resizeTexture(0.5);
        dx = 0;
    }

    /**
     * Moves the player ship.
     * <p>
     *     If either the left or right border is reached, the ship stays in the same position.
     * </p>
     */
    public void move()
    {
        this.xPos += dx;

        if(xPos >= GameConstants.WINDOW_WIDTH - 2 * getWidth())
        {
            this.xPos = GameConstants.WINDOW_WIDTH - 2 * getWidth();
        }
        else if(xPos <= getWidth())
        {
            this.xPos = getWidth();
        }
    }


    /**
     * Fires the projectile.
     * <p>
     *     A new projectile can be fired only if the previous has already disappeared from the screen.
     * </p>
     */
    public void shoot()
    {
        if(!shot.isVisible())
        {
            shot.setXPos(this.xPos + this.getWidth() / 2);
            shot.setYPos(this.yPos);
            shot.setVisible(true);
            shot.setDestroyed(false);

            SoundEffects.PLAYER_LASER.play();
        }
    }

    /**
     * Determines what to do when a key is pressed on the keyboard.
     * <p>
     *     Depending on which key is pressed :
     *     <ul>
     *         <li>Left key arrow : sets dx so that player will move to the left.</li>
     *         <li>Right key arrow : sets dx so that player will move to the right.</li>
     *         <li>Space bar : shoot the projectile.</li>
     *         <li>Other : does nothing</li>
     *     </ul>
     *
     * @param e Incoming key event.
     */
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

    /**
     * Determines what to do when a key is released on the keyboard.
     * <p>
     *     If either left or right arrow keys are pressed, sets dx to 0 so that the player will stop moving.
     *     Otherwise, does nothing.
     * </p>
     * @param e Incoming key event.
     */
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

    /**
     * Getter for healthPoints.
     * @return If for some reason healthPoints less than 0, sets it back to 0.
     */
    public int getHealthPoints()
    {
        if(this.healthPoints < 0)
            this.healthPoints = 0;
        return this.healthPoints;
    }

    /**
     * Getter for shot.
     * @return Laser object.
     */
    public Laser getShot()
    {
        return this.shot;
    }

    /**
     * Setter for healthPoints.
     * @param newHealth New amount of health points.
     */
    public void setHealthPoints(int newHealth)
    {
        if(newHealth < 0)
            newHealth = 0;
        this.healthPoints = newHealth;
    }
}
