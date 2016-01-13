/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameEngine;

import resources.TextureReference;

import java.awt.AWTEvent;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import javax.swing.JPanel;

/**
 *
 * @author dinervoid
 */
public class Canvas extends JPanel implements Runnable, GameConstants, TextureReference
{
    private Player player;
    private ArrayList<Enemy> enemies;
    private boolean alienDirection;
    private boolean ingame;
    private int nbKill;
    private int score;
    private int rand;
    private Thread animator;
    private Font font;
    private ArrayList<GameOverListener> gameOverListeners = new ArrayList<GameOverListener>();

    public Canvas()
    {
        super();

        ingame = true;
        alienDirection = true;
        nbKill = 0;
        score = 0;
        rand = 42;

        try
        {
            InputStream fontStream = getClass().getResourceAsStream(TextureReference.FONT);
            font = Font.createFont(Font.TRUETYPE_FONT, fontStream);
            font = font.deriveFont(Font.PLAIN, 10);
        }
        catch(FontFormatException e)
        {
            e.printStackTrace();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }

        this.player = new Player(PLAYER_TEXTURE);
        resetEnemies();

        this.addKeyListener(new KeyAdapter()
        {
            @Override
            public void keyReleased(KeyEvent e)
            {
                player.keyReleased(e);
            }

            @Override
            public void keyPressed(KeyEvent e)
            {
                player.keyPressed(e);
            }
        });

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

    private void resetEnemies()
    {
        this.enemies = new ArrayList();
        for(int j = 0 ; j < ENEMY_COL ; ++j)
        {
            for(int i = 0 ; i < ENEMY_ROW ; ++i)
            {
                System.out.println("Creating enemy...");
                Enemy tmp = new Enemy(ENEMY_TEXTURE);
                tmp.setXPos(344 + j * (tmp.getWidth() + 20));
                tmp.setYPos(42 + i * (tmp.getHeight() + 10));
                enemies.add(tmp);
            }
        }
    }

    private void checkCollisions()
    {
        Iterator enemyIt;
        if(player.getShot().getVisible())
        {
            enemyIt = enemies.iterator();

            while(enemyIt.hasNext())
            {
                Enemy tmp = (Enemy) enemyIt.next();
                if(tmp.getBounds().intersects(player.getShot().getBounds()))
                {
                    tmp.setDestroyed(true);
                    tmp.setVisible(false);
                    tmp.setTexture(ENEMY_DESTROYED_TEXTURE);
                    player.getShot().setDestroyed(true);
                    nbKill++;
                    score += ENEMY_SCORE;
                }
            }
            player.getShot().move(true);
        }
    }

    private void moveEnemies()
    {
        Iterator enemyIt = enemies.iterator();
        while(enemyIt.hasNext())
        {
            Enemy tmp2 = (Enemy) enemyIt.next();
            int x = tmp2.getXPos();

            if(x >= WINDOW_WIDTH - 2 * player.getWidth() && !alienDirection)
            {
                alienDirection = true;
                Iterator it = enemies.iterator();
                while(it.hasNext())
                {
                    Enemy tmp3 = (Enemy) it.next();
                    tmp3.moveY();
                    if(tmp3.getSpeed() <= 2 * ENEMY_BASE_X_SPEED)
                        tmp3.accelerate(1.5);
                }
            }

            if(x <= player.getWidth() && alienDirection)
            {
                alienDirection = false;
                Iterator it = enemies.iterator();
                while(it.hasNext())
                {
                    Enemy tmp3 = (Enemy) it.next();
                    tmp3.moveY();
                }
            }
        }

        enemyIt = enemies.iterator();
        while(enemyIt.hasNext())
        {
            Enemy toMove = (Enemy) enemyIt.next();
            if(!toMove.isDestroyed())
            {
                int y = toMove.getYPos();

                if(y > GROUND_HEIGHT - toMove.getHeight())
                {
                    ingame = false;
                }
                toMove.moveX(alienDirection);
            }
        }
    }

    private void enemyBombing()
    {
        Iterator enemyIt = enemies.iterator();
        while(enemyIt.hasNext())
        {
            Enemy tmp = (Enemy) enemyIt.next();
            rand = (int) (Math.random() * 1000);
            if(!tmp.getVisible() && rand == 42)
            {
                tmp.shoot();
            }

            if(tmp.getShot().getVisible())
            {
                if(player.getBounds().intersects(tmp.getShot().getBounds()))
                {
                    tmp.getShot().setDestroyed(true);
                    player.setHealthPoints(player.getHealthPoints() - 1);
                    if(player.getHealthPoints() == 0)
                        ingame = false;
                }
                else if(tmp.getShot().getYPos() >= GROUND_HEIGHT)
                {
                    tmp.getShot().setDestroyed(true);
                }
                else
                {
                    tmp.getShot().move(false);
                }
            }
        }
    }

    @Override
    public void paint(Graphics g)
    {
        super.paint(g);

        g.setFont(font);

        g.setColor(Color.green);
        g.drawLine(0, GROUND_HEIGHT, WINDOW_WIDTH, GROUND_HEIGHT);
        g.drawString("Score : " + score, 10, 715);

        drawPlayer(g);
        drawEnemies(g);

        Toolkit.getDefaultToolkit().sync();
        g.dispose();
    }
    
    private void drawPlayer(Graphics g)
    {
        g.drawImage(this.player.getTexture(), this.player.getXPos(), this.player.getYPos(), this);
        if(this.player.getShot().getVisible())
        {
            g.drawImage(this.player.getShot().getTexture(), this.player.getShot().getXPos(), this.player.getShot().getYPos(), this);
        }
    }

    private void drawEnemies(Graphics g)
    {
        Iterator it = this.enemies.iterator();


        while(it.hasNext())
        {
            Enemy tmp = (Enemy)it.next();
            if(!tmp.isDestroyed())
                g.drawImage(tmp.getTexture(), tmp.getXPos(), tmp.getYPos(), this);
            if(tmp.getShot().getVisible())
                g.drawImage(tmp.getShot().getTexture(), tmp.getShot().getXPos(), tmp.getShot().getYPos(), this);
        }
    }

    public void animationCycle()
    {
        if (nbKill == ENEMY_NB)
        {
            nbKill = 0;
            resetEnemies();
        }
        player.move();

        checkCollisions();

        moveEnemies();

        enemyBombing();
    }

    public void processGameOverEvent(GameOverEvent event)
    {
        ArrayList<GameOverListener> tempGameOverListeners;

        synchronized(this)
        {
            if(gameOverListeners.size() == 0)
                return;
            tempGameOverListeners = (ArrayList<GameOverListener>) gameOverListeners.clone();
        }

        for(GameOverListener listener : tempGameOverListeners)
        {
            listener.gameOverReceived(event);
        }
    }


    @Override
    public void run()
    {
        long beforeTime, timeDiff, sleep;

        beforeTime = System.currentTimeMillis();

        while(ingame)
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
        processGameOverEvent(new GameOverEvent(this, AWTEvent.RESERVED_ID_MAX + 1, !ingame, score));
    }

    public synchronized void addGameOverListener(GameOverListener listener)
    {
        if(!gameOverListeners.contains(listener))
        {
            gameOverListeners.add(listener);
        }
    }
}
