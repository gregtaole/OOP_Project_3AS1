/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameEngine;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JPanel;

/**
 *
 * @author dinervoid
 */
public class Canvas extends JPanel
{
    private GameLoop game;
    
    public Canvas()
    {
        super();
        this.game = new GameLoop();
        this.setBackground(Color.black);
        this.setSize(1080, 720);
       this.repaint();
    }
    
    @Override
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        
        drawPlayer(g);
    }
    
    private void drawPlayer(Graphics g)
    {
        g.drawImage(game.getPlayer().getTexture(), 400, 300, null);
    }
}
