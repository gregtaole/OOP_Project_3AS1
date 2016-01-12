/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameEngine;


import java.awt.Rectangle;

/**
 *
 * @author dinervoid
 */
public class Enemy  extends Spaceship
{
    private int ySpeed;
    private Laser shot;


    public Enemy(String shipType)
    {
        super(shipType);
        this.resizeTexture(0.5);
        this.speed = ENEMY_BASE_X_SPEED;
        this.ySpeed = this.getHeight();
        this.shot = new Laser(ENEMY_LASER_TEXTURE);
        this.shot.flipSprite(true);
    }

    /**
     * Moves the sprite on the horizontal axis
     *
     * @param direction Determines the direction of the movement (true->left, false->right)
     **/
    public void moveX(boolean direction)
    {
        if(direction)
            this.xPos -= this.speed;
        else
            this.xPos += this.speed;
    }

    public void moveY()
    {
        this.yPos += this.ySpeed;
    }

    public void accelerate(double rate)
    {
        this.speed = (int)(this.speed * rate);
    }

    public void shoot()
    {
        if(!this.shot.getVisible())
        {
            shot.setXPos(this.xPos + this.getWidth() / 2);
            shot.setYPos(this.yPos + this.getHeight());
            shot.setVisible(true);
            shot.setDestroyed(false);
        }
    }

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
