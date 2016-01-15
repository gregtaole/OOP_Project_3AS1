/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameEngine;

import gui.LoadImageResource;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

/**
 * Base class for all objects needing a sprite.
 */

public class SpriteObject
{
    /**
     * Horizontal position of the sprite.
     */
    protected int xPos;

    /**
     * Vertical position of the sprite.
     */
    protected int yPos;

    /**
     * Texture and hitbox width.
     */
    private int width;

    /**
     * Texture and hitbox height.
     */
    private int height;

    /**
     * Movement speed.
     */
    protected int speed;

    /**
     * Flag which determines whether or not the sprite should be displayed.
     */
    private boolean isVisible;

    /**
     * Flag which determines whether or not the object is destroyed.
     */
    private boolean isDestroyed;

    /**
     * Texture of the object.
     */
    private BufferedImage texture;


    /**
     * Class constructor.
     * @param spriteName String indicating the object texture.
     */
    public SpriteObject(String spriteName)
    {
        this.texture = LoadImageResource.getTexture(spriteName);
        this.width = texture.getWidth();
        this.height = texture.getHeight();
        this.xPos = 0;
        this.yPos = 0;
        this.speed = 0;
        this.isVisible = false;
    }


    /**
     * Resizes the texture given the scale.
     * @param scale Scale of the transformation.
     */
    public void resizeTexture(double scale)
    {
        int newWidth = (int) (scale * texture.getWidth());
        int newHeight = (int) (scale * texture.getHeight());

        Image tmp = this.texture.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
        texture = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);
        
        Graphics2D g2d = texture.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();

        this.width = texture.getWidth();
        this.height = texture.getHeight();
    }

    /**
     * Flips the sprite along the given axis.
     * @param axis Direction of the transformation : true=vertical, false=horizontal.
     */
    public void flipSprite(boolean axis)
    {
        if(axis)//Flip vertically
        {
            AffineTransform tx = AffineTransform.getScaleInstance(1, -1);
            tx.translate(0, -texture.getHeight(null));
            AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
            texture = op.filter(texture, null);
        }
        else//Flip horizontally
        {
            AffineTransform tx = AffineTransform.getScaleInstance(-1, 1);
            tx.translate(-texture.getWidth(null), 0);
            AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
            texture = op.filter(texture, null);
        }
    }

    /**
     * Get the rectangular hitbox of the object.
     * @return Returns a Rectangle object with dimensions similar to those of the texture.
     */
    public Rectangle getBounds()
    {
        return new Rectangle(this.xPos, this.yPos, this.width, this.height);
    }

    /**
     * Getter for xPos.
     * @return xPos.
     */
    public int getXPos()
    {
        return this.xPos;
    }

    /**
     * Getter for yPos.
     * @return yPos.
     */
    public int getYPos()
    {
        return this.yPos;
    }

    /**
     * Getter for width.
     * @return width.
     */
    public int getWidth()
    {
        return this.width;
    }

    /**
     * Getter for height.
     * @return height.
     */
    public int getHeight()
    {
        return this.height;
    }

    /**
     * Geter for speed.
     * @return speed.
     */
    public int getSpeed()
    {
        return this.speed;
    }

    /**
     * Getter for isVisible.
     * @return isVisible.
     */
    public boolean isVisible()
    {
        return this.isVisible;
    }

    /**
     * Getter for texture.
     * @return texture.
     */
    public BufferedImage getTexture()
    {
        return this.texture;
    }

    /**
     * Getter for isDestroyed.
     * @return isDestroyed.
     */
    public boolean isDestroyed()
    {
        return isDestroyed;
    }

    /**
     * Setter for xPos.
     * @param newPos New xPos.
     */
    public void setXPos(int newPos)
    {
        this.xPos = newPos;
    }

    /**
     * Setter for yPos.
     * @param newPos New yPos.
     */
    public void setYPos(int newPos)
    {
        this.yPos = newPos;
    }

    /**
     * Setter for isVisible
     * @param visibility New visibility.
     */
    public void setVisible(boolean visibility)
    {
        this.isVisible = visibility;
    }

    /**
     * Setter for isDestroyed.
     * <p>
     *     If destroyed is true, then we can also set isVisible to false.
     * </p>
     * @param destroyed New state.
     */
    public void setDestroyed(boolean destroyed)
    {
        isDestroyed = destroyed;
        if(destroyed)
            this.setVisible(false);
    }
}
