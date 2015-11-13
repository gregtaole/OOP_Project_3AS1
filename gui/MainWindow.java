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

import java.awt.*;
import java.awt.Font;
import java.io.IOException;
import java.io.InputStream;
import javax.swing.*;
import resources.Reference;


public class MainWindow extends JFrame
{
    public MainWindow()
    {
        initUI();
    }
    
    private void initUI()
    {
        setTitle("Projet POO");
        setLocationRelativeTo(null);
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        CustomButton quitButton = new CustomButton("Quit", "buttonGreen.png");
        
        quitButton.addActionListener(e -> quitButtonClick( ));
        
        createLayout(quitButton);
        
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
    
    private void createLayout(JComponent... arg)
    {
        Container pane = getContentPane();
        GroupLayout gl = new GroupLayout(pane);
        pane.setLayout(gl);
        
        gl.setAutoCreateContainerGaps(true);
        
        gl.setHorizontalGroup(gl.createSequentialGroup().addComponent(arg[0]));

        gl.setVerticalGroup(gl.createSequentialGroup().addComponent(arg[0]));
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
