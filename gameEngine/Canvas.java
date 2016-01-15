/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameEngine;

import resources.ResourceReference;
import resources.SoundEffects;

import java.awt.AWTEvent;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import javax.swing.JPanel;

/**
 * Displays the actual game and acts as a game loop
 * <p>
 *     This class is responsible for running the game loop as well as drawing the different elements in the window
 * </p>
 */
@SuppressWarnings("serial")
public class Canvas extends JPanel implements Runnable
{
    /**
     * The player object.
     */
    private Player player;

    /**
     * Linked list of enemies.
     */
    private ArrayList<Enemy> enemies;

    /**
     * Direction aliens are going : true=left , false=right.
     */
    private boolean alienDirection;

    /**
     * Flag indicating whether or not the game is still going.
     */
    private boolean inGame;

    /**
     * Number of enemies destroyed in the current wave.
     */
    private int nbKill;

    /**
     * Player score.
     */
    private int score;

    /**
     * Random variable used in computing the enemy firing chances.
     */
    private int rand;

    /**
     * Thread responsible for the game computation and drawing.
     */
    private Thread animator;

    /**
     * Font used to display text information
     */
    private Font font;

    /**
     * Player health texture.
     */
    private BufferedImage playerHealth;
    private ArrayList<GameOverListener> gameOverListeners = new ArrayList<>();

    /**
     * Class constructor.
     */
    public Canvas()
    {
        super();

        inGame = true;
        alienDirection = true;
        nbKill = 0;
        score = 0;
        rand = 42;
        playerHealth = resources.LoadImageResource.getTexture(ResourceReference.PLAYER_HEALTH_TEXTURE);

        //Resize playerHealth to half its size
        int newWidth = (int) (0.5 * playerHealth.getWidth());
        int newHeight = (int) (0.5 * playerHealth.getHeight());

        Image tmp = this.playerHealth.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
        playerHealth = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2d = playerHealth.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();

        try
        {
            InputStream fontStream = getClass().getResourceAsStream(ResourceReference.FONT);
            font = Font.createFont(Font.TRUETYPE_FONT, fontStream);
            font = font.deriveFont(Font.PLAIN, 10);
        }
        catch(FontFormatException | IOException e)
        {
            e.printStackTrace();
        }

        this.player = new Player(ResourceReference.PLAYER_TEXTURE);
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

    /**
     * Initializes the canvas and starts the animator thread.
     */
    private void initCanvas()
    {
        this.setBackground(Color.black);
        this.setSize(GameConstants.WINDOW_WIDTH, GameConstants.WINDOW_HEIGHT);
        this.repaint();

        if(animator == null)
        {
            this.animator = new Thread(this);
            animator.start();
        }
        
        this.setDoubleBuffered(true);
    }

    /**
     * (Re)initializes the enemies list.
     * <p>
     *     Called in the constructor and also each time nbKill equals the number of aliens in a wave.
     * </p>
     */
    private void resetEnemies()
    {
        this.enemies = new ArrayList<>();
        for(int j = 0 ; j < GameConstants.ENEMY_COL ; ++j)
        {
            for(int i = 0 ; i < GameConstants.ENEMY_ROW ; ++i)
            {
                Enemy tmp = new Enemy(ResourceReference.ENEMY_TEXTURE);
                tmp.setXPos(344 + j * (tmp.getWidth() + 20));
                tmp.setYPos(42 + i * (tmp.getHeight() + 10));
                enemies.add(tmp);
            }
        }
    }

    /**
     * Draws the player, its health and also its projectiles.
     * @param g Graphics object on which this method will paint.
     */
    private void drawPlayer(Graphics g)
    {
        g.drawImage(this.player.getTexture(), this.player.getXPos(), this.player.getYPos(), this);
        if(this.player.getShot().isVisible())
        {
            g.drawImage(this.player.getShot().getTexture(), this.player.getShot().getXPos(), this.player.getShot().getYPos(), this);
        }

        g.drawString("Lives : ", 850, 715);
        for(int i = 0; i < player.getHealthPoints(); ++i)
        {
            g.drawImage(playerHealth, 900 + (playerHealth.getWidth() + 10) * i, 710 - playerHealth.getHeight() / 2, this);
        }
    }

    /**
     * Draws the enemies and their projectiles.
     * @param g Graphics object on which this method will paint.
     */
    private void drawEnemies(Graphics g)
    {
        for(Enemy tmp : this.enemies)
        {
            if (!tmp.isDestroyed())
                g.drawImage(tmp.getTexture(), tmp.getXPos(), tmp.getYPos(), this);
            if (tmp.getShot().isVisible())
                g.drawImage(tmp.getShot().getTexture(), tmp.getShot().getXPos(), tmp.getShot().getYPos(), this);
        }
    }

    /**
     * Checks for collision between the player's projectiles and enemies.
     * <p>
     *     When a collision is detected, both the player's projectile and the alien are destroyed and nbKill and score are incremented.
     *     Otherwise the player's shot will continue to move.
     * </p>
     */
    private void checkCollisions()
    {
        Iterator<Enemy> enemyIt;
        if(player.getShot().isVisible())
        {
            enemyIt = enemies.iterator();

            while(enemyIt.hasNext())
            {
                Enemy tmp = enemyIt.next();
                if(tmp.getBounds().intersects(player.getShot().getBounds()))
                {
                    tmp.setDestroyed(true);
                    tmp.setVisible(false);
                    player.getShot().setDestroyed(true);
                    SoundEffects.ENEMY_EXPLOSION.play();
                    nbKill++;
                    score += GameConstants.ENEMY_SCORE;
                }
            }
            player.getShot().move(true);
        }
    }

    /**
     * Moves the enemies
     * <p>
     *     When the sides of the canvas are reached, the direction boolean is changed.
     *     Enemies will move faster each time they complete a round trip until they reach twice their base speed.
     *     If an enemy reaches the ground, then the game is finished.
     * </p>.
     */
    private void moveEnemies()
    {
        Iterator<Enemy> enemyIt = enemies.iterator();
        while(enemyIt.hasNext())
        {
            Enemy tmp2 = enemyIt.next();
            int x = tmp2.getXPos();

            if(x >= GameConstants.WINDOW_WIDTH - 2 * player.getWidth() && !alienDirection)
            {
                alienDirection = true;
                for(Enemy tmp3 : enemies)
                {
                    tmp3.moveY();
                    if(tmp3.getSpeed() <= 2 * GameConstants.ENEMY_BASE_X_SPEED)
                        tmp3.accelerate(1.5);
                }
            }

            if(x <= player.getWidth() && alienDirection)
            {
                alienDirection = false;
                enemies.forEach(Enemy::moveY);
            }
        }

        enemyIt = enemies.iterator();
        while(enemyIt.hasNext())
        {
            Enemy toMove = enemyIt.next();
            if(!toMove.isDestroyed())
            {
                int y = toMove.getYPos();

                if(y > GameConstants.GROUND_HEIGHT - toMove.getHeight())
                {
                    inGame = false;
                }
                toMove.moveX(alienDirection);
            }
        }
    }

    /**
     * Computes the enemy bombing.
     * <p>
     *     Each game turn, one enemy has a one in GameConstants.ENEMY_BOMB_CHANCE to fire a shot.
     *     If a shot touches the player, the player's lives will be decremented by one and when they reach zero, the game is ended.
     *     The shot will otherwise continue to move until it hits the ground and is destroyed.
     * </p>
     */
    private void enemyBombing()
    {
        for(Enemy tmp : enemies)
        {
            rand = (int) (Math.random() * GameConstants.ENEMY_BOMB_CHANCE);
            if(!tmp.isVisible() && rand == 42)
            {
                tmp.shoot();
            }

            if(tmp.getShot().isVisible())
            {
                if(player.getBounds().intersects(tmp.getShot().getBounds()))
                {
                    tmp.getShot().setDestroyed(true);
                    player.setHealthPoints(player.getHealthPoints() - 1);
                    if(player.getHealthPoints() == 0)
                    {
                        SoundEffects.PLAYER_EXPLOSION.play();
                        inGame = false;
                    }
                }
                else if (tmp.getShot().getYPos() >= GameConstants.GROUND_HEIGHT)
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
        g.drawLine(0, GameConstants.GROUND_HEIGHT, GameConstants.WINDOW_WIDTH, GameConstants.GROUND_HEIGHT);
        g.drawString("Score : " + score, 10, 715);

        drawPlayer(g);
        drawEnemies(g);

        Toolkit.getDefaultToolkit().sync();
        g.dispose();
    }

    /**
     * Calls the different game methods in order.
     */
    public void animationCycle()
    {
        if (nbKill == GameConstants.ENEMY_NB)
        {
            nbKill = 0;
            resetEnemies();
        }
        player.move();

        checkCollisions();

        moveEnemies();

        enemyBombing();
    }


    /**
     * Sends event to all registered GameOverListeners.
     * @param event GameOverEvent to be sent.
     */
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

        while(inGame)
        {
            repaint();
            animationCycle();

            timeDiff = System.currentTimeMillis() - beforeTime;
            sleep = GameConstants.DELAY - timeDiff;

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
        processGameOverEvent(new GameOverEvent(this, AWTEvent.RESERVED_ID_MAX + 1, score));
    }

    /**
     * Adds a GameOverListener.
     * @param listener GameOverListener to be added.
     */
    public synchronized void addGameOverListener(GameOverListener listener)
    {
        if(!gameOverListeners.contains(listener))
        {
            gameOverListeners.add(listener);
        }
    }
}
