/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameEngine;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JPanel;

/**
 *
 * @author dinervoid
 */
public class Canvas extends JPanel implements Runnable
{
    private final int DELAY;
    
    private final GameLoop game;
    private boolean ingame;
    private Thread animator;
    
    public Canvas()
    {
        super();
        
        ingame = true;
        DELAY = 17;
        
        this.setFocusable(true);
        this.requestFocusInWindow();
        this.addKeyListener(new KeyAdapter()
            {
                @Override
                public void keyReleased(KeyEvent e)
                {
                    System.out.println("Key released.");
                    game.getPlayer().keyReleased(e);
                }

                @Override
                public void keyPressed(KeyEvent e)
                {
                    System.out.println("Key pressed.");
                    game.getPlayer().keyPressed(e);
                }
            });
        
        this.game = new GameLoop();
        this.setBackground(Color.black);
        this.setSize(1080, 720);
        this.game.getPlayer().resizeTexture(0.5);
        this.game.getPlayer().setXPos(400);
        this.game.getPlayer().setYPos(300);
        this.repaint();
 
        if(animator == null)
        {
            this.animator = new Thread(this);
            animator.start();
        }
        
        this.setDoubleBuffered(true);
    }
    
    @Override
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        
        drawPlayer(g);
    }
    
    private void drawPlayer(Graphics g)
    {
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(this.game.getPlayer().getTexture(), this.game.getPlayer().getXPos(), this.game.getPlayer().getYPos(), this);
        Toolkit.getDefaultToolkit().sync();
    }

    public void animationCycle()
    {
        game.getPlayer().move();
    }
    
    @Override
    public void run()
    {
        long beforeTime, timeDiff, sleep;

        beforeTime = System.currentTimeMillis();

        while (ingame)
        {
            repaint();
            animationCycle();

            timeDiff = System.currentTimeMillis() - beforeTime;
            sleep = DELAY - timeDiff;

            if (sleep < 0) 
                sleep = 2;
            try {
                Thread.sleep(sleep);
            } catch (InterruptedException e) {
                System.out.println("interrupted");
            }
            beforeTime = System.currentTimeMillis();
        }
    }
}
