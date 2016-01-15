package gameEngine;

import java.awt.*;

/**
 * Projectile object. Fired by both player and aliens.
 */
public class Laser extends SpriteObject
{
    /**
     * Projectile's speed.
     */
    private int speed;

    /**
     * Class constructor.
     * @param texture String indicating the laser texture.
     */
    public Laser(String texture)
    {
        super(texture);
        this.speed = GameConstants.LASER_SPEED;
        this.resizeTexture(0.5);
    }

    /**
     * Moves the laser in the specified vertical direction.
     * @param direction Direction of the movement : true=up, false=down.
     */
    public void move(boolean direction)
    {
        int y = yPos;
        if(direction)
            y -= this.speed;
        else
            y += this.speed;

        if(y < 0 || y >= GameConstants.GROUND_HEIGHT - this.getHeight())
            this.setVisible(false);
        else
            this.setYPos(y);
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
