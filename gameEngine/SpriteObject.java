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
import java.awt.image.BufferedImage;

public class SpriteObject
{
    private int xPos;
    private int yPos;
    private int width;
    private int height;
    private final BufferedImage texture;
    
    public SpriteObject(String spriteName)
    {
        this.texture = resources.LoadImageResource.getTexture(spriteName);
        this.width = texture.getWidth();
        this.height = texture.getHeight();
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
    
    public BufferedImage getTexture()
    {
        return this.texture;
    }
    
    public void setXPos(int newPos)
    {
        this.xPos = newPos;
    }
    
    public void setYPos(int newPos)
    {
        this.yPos = newPos;
    }
    
    public void setWidth(int newWidth)
    {
        this.width = newWidth;
    }
    
    public void setHeight(int newHeight)
    {
        this.height = newHeight;
    }
}
