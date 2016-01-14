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
import resources.ResourceReference;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

public class SpriteObject implements GameConstants, ResourceReference
{
    protected int xPos;
    protected int yPos;
    private int width;
    private int height;
    private boolean isVisible;
    private boolean isDestroyed;
    private BufferedImage texture;
    
    public SpriteObject(String spriteName)
    {
        this.texture = resources.LoadImageResource.getTexture(spriteName);
        this.width = texture.getWidth();
        this.height = texture.getHeight();
        this.xPos = 0;
        this.yPos = 0;
        this.isVisible = false;
    }
    
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

    public Rectangle getBounds()
    {
        return new Rectangle(this.xPos, this.yPos, this.width, this.height);
    }

    public int getXPos()
    {
        return this.xPos;
    }
    
    public int getYPos()
    {
        return this.yPos;
    }
    
    public int getWidth()
    {
        return this.width;
    }
    
    public int getHeight()
    {
        return this.height;
    }

    public boolean getVisible()
    {
        return this.isVisible;
    }

    public BufferedImage getTexture()
    {
        return this.texture;
    }

    public boolean isDestroyed()
    {
        return isDestroyed;
    }

    public void setXPos(int newPos)
    {
        this.xPos = newPos;
    }

    public void setYPos(int newPos)
    {
        this.yPos = newPos;
    }

    public void setVisible(boolean visibility)
    {
        this.isVisible = visibility;
    }

    public void setDestroyed(boolean destroyed)
    {
        isDestroyed = destroyed;
        if(destroyed)
            this.setVisible(false);
    }
}
