/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import java.awt.*;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.SwingConstants;

/**
 * Reimplementation of JButton to use custom button background.
 */
@SuppressWarnings("serial")
public class CustomButton extends JButton
{
    public CustomButton(String label, String type)
    {
        super(label, new ImageIcon(LoadImageResource.getTexture(type)));
        this.setBorderPainted(false);
        this.setContentAreaFilled(false);
        this.setVerticalTextPosition(SwingConstants.CENTER);
        this.setHorizontalTextPosition(SwingConstants.CENTER);
        this.setAlignmentX(Component.CENTER_ALIGNMENT);
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
