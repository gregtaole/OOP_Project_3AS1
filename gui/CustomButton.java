/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import resources.LoadImageResource;

/**
 *
 * @author dinervoid
 */
public class CustomButton extends JButton
{
    public CustomButton(String label, String type)
    {
        super(label, new ImageIcon(LoadImageResource.getTexture(type)));
        this.setBorderPainted(false);
        this.setContentAreaFilled(false);
        this.setVerticalTextPosition(SwingConstants.CENTER);
        this.setHorizontalTextPosition(SwingConstants.CENTER);
    }
    
    @Override
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);
    }
    
     @Override
    public Dimension getPreferredSize()
    {
        return super.getPreferredSize();
    }
}
