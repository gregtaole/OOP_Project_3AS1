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
public class Enemy  extends Spaceship implements GameConstants
{
    private int xSpeed;
    private int ySpeed;

    public Enemy(String shipType)
    {
        super(shipType);
        this.resizeTexture(0.5);
        this.xSpeed = ENEMY_BASE_X_SPEED;
        this.ySpeed = ENEMY_BASE_Y_SPEED;
    }

    /**
     * Moves the sprite on the horizontal axis
     *
     * @param direction Determines the direction of the movement (true->left, false->right)
     **/
    public void moveX(boolean direction)
    {
        if(direction)
            this.xPos -= xSpeed;
        else
            this.yPos += ySpeed;
    }

    public void moveY()
    {
        this.yPos += ySpeed;
    }

    public void accelerate(double scale)
    {
        xSpeed = (int) (scale * xSpeed);
        ySpeed = (int) (scale * ySpeed);
    }
}
