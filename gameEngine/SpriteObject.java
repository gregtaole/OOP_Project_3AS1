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
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;

public class SpriteObject
{
    protected int xPos;
    protected int yPos;
    private int width;
    private int height;
    private BufferedImage texture;
    
    public SpriteObject(String spriteName)
    {
        this.texture = resources.LoadImageResource.getTexture(spriteName);
        this.width = texture.getWidth();
        this.height = texture.getHeight();
        this.xPos = 0;
        this.yPos = 0;
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
