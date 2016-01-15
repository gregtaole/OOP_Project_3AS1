/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameEngine;


import gui.ResourceReference;
import gui.SoundEffects;

import java.awt.Rectangle;

/**
 *Class representing an enemy ship.
 */
public class Enemy  extends SpriteObject
{
    /**
     * Vertical speed.
     */
    private int ySpeed;

    /**
     * Projectile.
     */
    private Laser shot;

    /**
     * Class constructor
     * @param shipType String indicating the ship texture.
     */
    public Enemy(String shipType)
    {
        super(shipType);
        this.resizeTexture(0.5);
        this.speed = GameConstants.ENEMY_BASE_X_SPEED;
        this.ySpeed = this.getHeight();
        this.shot = new Laser(ResourceReference.ENEMY_LASER_TEXTURE);
        this.shot.flipSprite(true);
    }

    /**
     * Moves the sprite on the horizontal axis
     *
     * @param direction Determines the direction of the movement : true=left, false=right.
     **/
    public void moveX(boolean direction)
    {
        if(direction)
            this.xPos -= this.speed;
        else
            this.xPos += this.speed;
    }

    /**
     * Moves the sprite on the vertical axis
     */
    public void moveY()
    {
        this.yPos += this.ySpeed;
    }

    /**
     * Modifies the movement speed.
     * @param rate The acceleration factor.
     */
    public void accelerate(double rate)
    {
        this.speed = (int)(this.speed * rate);
    }

    /**
     * Fires the projectile.
     * <p>
     *     A new projectile can be fired only if the previous has already disappeared from the screen.
     * </p>
     */
    public void shoot()
    {
        if(!this.shot.isVisible())
        {
            shot.setXPos(this.xPos + this.getWidth() / 2);
            shot.setYPos(this.yPos + this.getHeight());
            shot.setVisible(true);
            shot.setDestroyed(false);

            SoundEffects.ENEMY_LASER.play();
        }
    }

    /**
     * Getter for the projectile.
     * @return Laser object.
     */
    public Laser getShot()
    {
        return this.shot;
    }

    @Override
    public Rectangle getBounds()
    {
        if(isDestroyed())
            return new Rectangle(0, 0, 0, 0);
        else
            return super.getBounds();
    }
}
