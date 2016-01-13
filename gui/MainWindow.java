/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

/**
 *
 * @author dinervoid
 */

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.io.IOException;
import java.io.InputStream;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.BoxLayout;

import gameEngine.GameOverEvent;
import gameEngine.GameOverListener;
import resources.TextureReference;
import gameEngine.Canvas;

public class MainWindow extends JFrame implements TextureReference
{
    private CustomButton playButton;
    private CustomButton optionButton;
    private CustomButton quitButton;
    
    private Canvas gameCanvas;

    private Font font;

    public MainWindow()
    {
        super();
        initUI();
    }
    
    private void mainMenu()
    {  
        playButton = new CustomButton("New Game", BLUE_BUTTON);
        optionButton = new CustomButton("Options",  BLUE_BUTTON);
        quitButton = new CustomButton("Quit", BLUE_BUTTON);
        
        playButton.addActionListener(e->playButtonClick());
        optionButton.addActionListener(e->optionButtonClick());
        quitButton.addActionListener(e->quitButtonClick());

        this.getContentPane().setLayout(new BoxLayout(this.getContentPane(), BoxLayout.PAGE_AXIS));
        this.add(playButton);
        this.add(optionButton);
        this.add(quitButton);

        try
        {
            InputStream fontStream = getClass().getResourceAsStream(TextureReference.FONT);
            font = Font.createFont(Font.TRUETYPE_FONT, fontStream);
            font = font.deriveFont(Font.PLAIN, 14);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(font);
            changeFont(this, font);
        }
        catch(IOException|FontFormatException e)
        {
            e.printStackTrace(System.err);
        }
    }
    
    private void initUI()
    {
        setTitle("Projet POO");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        mainMenu();
        //this.setResizable(false);
        this.pack();
    }
    
    public static void changeFont(Component component, Font font)
    {
        component.setFont(font);
        if (component instanceof Container)
        {
            for (Component child : ((Container)component).getComponents())
            {
                changeFont (child, font);
            }
        }
    }
    
    private void play()
    {
        this.getContentPane().removeAll();
        this.getContentPane().repaint();
        
        gameCanvas = new Canvas();
        this.getContentPane().add(gameCanvas);
        this.getContentPane().setPreferredSize(gameCanvas.getSize());
        this.pack();
        this.gameCanvas.setFocusable(true);
        this.gameCanvas.requestFocusInWindow();

        this.gameCanvas.addGameOverListener(new GameOverListener()
        {
            @Override
            public void gameOverReceived(GameOverEvent event)
            {
                System.out.println("GameOverEvent received!");
                getContentPane().removeAll();
                JPanel gameOverPanel = gameOverPanel(event.getScore());
                getContentPane().add(gameOverPanel);
                getContentPane().setPreferredSize(gameOverPanel.getPreferredSize());
                getContentPane().repaint();
                pack();
            }
        });
    }

    private JPanel gameOverPanel(int score)
    {
        JPanel panel = new JPanel();
        JLabel label = new JLabel("Game Over! You have scored");
        JLabel scoreLabel = new JLabel(Integer.toString(score));
        JLabel label2 = new JLabel("points.");
        CustomButton menuButton = new CustomButton("Back to main menu", BLUE_BUTTON);

        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        scoreLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        label2.setAlignmentX(Component.CENTER_ALIGNMENT);
        menuButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.setBackground(Color.black);
        label.setForeground(Color.green);
        scoreLabel.setForeground(Color.green);
        label2.setForeground(Color.green);
        menuButton.addActionListener(e->backToMenuClick());

        panel.add(label);
        panel.add(scoreLabel);
        panel.add(label2);
        panel.add(menuButton);

        changeFont(panel, font);

        return panel;
    }
    
    private void playButtonClick()
    {
        System.out.println("Play");
        play();
    }
    
    private void optionButtonClick()
    {
        System.out.println("Options");
        //options();
    }
    
    private void quitButtonClick()
    {
        System.out.println("Quit");
        System.exit(0);
    }

    private void backToMenuClick()
    {
        this.getContentPane().removeAll();
        this.mainMenu();
    }

    public static void main(String[] args)
    {
        EventQueue.invokeLater(
                () -> { 
                    MainWindow main = new MainWindow();
                    main.setVisible(true);
                });
    }
}
