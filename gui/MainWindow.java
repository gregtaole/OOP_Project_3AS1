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

import java.awt.Component;
import java.awt.Container;
import java.awt.EventQueue;
import java.awt.GraphicsEnvironment;
import java.awt.FontFormatException;
import java.awt.Font;
import java.io.IOException;
import java.io.InputStream;
import javax.swing.JFrame;
import javax.swing.BoxLayout;
import resources.Reference;
import gameEngine.Canvas;

public class MainWindow extends JFrame
{
    private CustomButton playButton;
    private CustomButton optionButton;
    private CustomButton quitButton;
    
    private Canvas gameCanvas;
    
    public MainWindow()
    {
        super();
        initUI();
    }
    
    private void mainMenu()
    {  
        playButton = new CustomButton("New Game", "buttonBlue.png");
        optionButton = new CustomButton("Options",  "buttonBlue.png");
        quitButton = new CustomButton("Quit", "buttonBlue.png");
        
        playButton.addActionListener(e->playButtonClick());
        optionButton.addActionListener(e->optionButtonClick());
        quitButton.addActionListener(e->quitButtonClick());

        this.getContentPane().setLayout(new BoxLayout(this.getContentPane(), BoxLayout.PAGE_AXIS));
        this.add(playButton);
        this.add(optionButton);
        this.add(quitButton);       
    }
    
    private void initUI()
    {
        setTitle("Projet POO");
        setLocationRelativeTo(null);
        //setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        mainMenu();
        this.pack();
        
        try
        {
            InputStream fontStream = getClass().getResourceAsStream(Reference.FONT);
            Font font = Font.createFont(Font.TRUETYPE_FONT, fontStream);
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
        this.setSize(gameCanvas.getSize());
        this.getContentPane().add(gameCanvas);
        this.gameCanvas.setFocusable(true);
        this.gameCanvas.requestFocusInWindow();
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

    public static void main(String[] args)
    {
        EventQueue.invokeLater(
                () -> { 
                    MainWindow main = new MainWindow();
                    main.setVisible(true);
                });
    }
}
