/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameEngine;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Iterator;
import javax.swing.JPanel;

/**
 *
 * @author dinervoid
 */
public class Canvas extends JPanel implements Runnable, GameConstants
{  
    private final GameLoop game;
    private boolean ingame;
    private Thread animator;
    
    public Canvas()
    {
        super();
        
        ingame = true;
        
        this.addKeyListener(new KeyAdapter()
            {
                @Override
                public void keyReleased(KeyEvent e)
                {
                    game.getPlayer().keyReleased(e);
                }

                @Override
                public void keyPressed(KeyEvent e)
                {
                    game.getPlayer().keyPressed(e);
                }
            });
        
        this.game = new GameLoop();
        
 
        this.initCanvas();
    }
    
    private void initCanvas()
    {
        this.setBackground(Color.black);
        this.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        this.repaint();

        if(animator == null)
        {
            this.animator = new Thread(this);
            animator.start();
        }
        
        this.setDoubleBuffered(true);
    }
    
    @Override
    public void paint(Graphics g)
    {
        super.paint(g);
        
        drawPlayer(g);
        drawEnemies(g);

        g.drawImage(this.game.getTestLaser().getTexture(), this.game.getTestLaser().getXPos(), this.game.getTestLaser().getYPos(), this);

        Toolkit.getDefaultToolkit().sync();
        g.dispose();
    }
    
    private void drawPlayer(Graphics g)
    {
       g.drawImage(this.game.getPlayer().getTexture(), this.game.getPlayer().getXPos(), this.game.getPlayer().getYPos(), this);
    }

    private void drawEnemies(Graphics g)
    {
        Iterator it = this.game.getEnemies().iterator();


        while(it.hasNext())
        {
            Enemy tmp = (Enemy)it.next();
            g.drawImage(tmp.getTexture(), tmp.getXPos(), tmp.getYPos(), this);
        }
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
            try
            {
                Thread.sleep(sleep);
            }
            catch (InterruptedException e)
            {
                System.out.println("interrupted");
            }
            beforeTime = System.currentTimeMillis();
        }
    }
}
