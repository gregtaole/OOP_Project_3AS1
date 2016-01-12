package gameEngine;

import java.awt.*;

/**
 * Created by dinervoid on 1/9/16.
 */
public class Laser extends SpriteObject
{
    private int speed;
    public Laser(String texture)
    {
        super(texture);
        this.speed = LASER_SPEED;
        this.resizeTexture(0.5);
    }

    /**
     *
     * @param direction Direction of the movement : true->up, false->down
     */
    public void move(boolean direction)
    {
        int y = yPos;
        if(direction)
            y -= this.speed;
        else
            y += this.speed;

        if(y < 0 || y >= GROUND_HEIGHT - this.getHeight())
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
