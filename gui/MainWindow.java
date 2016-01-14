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
import java.awt.event.ItemEvent;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import javax.sound.sampled.*;
import javax.swing.*;

import gameEngine.Canvas;
import resources.LoadImageResource;
import resources.ResourceReference;
import resources.SoundEffects;

@SuppressWarnings("serial")
public class MainWindow extends JFrame
{
    private Font font;
    private Clip bgmClip;
    private Dimension startDimension;

    public MainWindow()
    {
        super();
        this.initUI();
    }
    
    private void mainMenu()
    {
        CustomLabel titleLabel = new CustomLabel("Welcome to SPACE INVADERS!");

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.PAGE_AXIS));
        CustomButton playButton = new CustomButton("New Game", ResourceReference.BLUE_BUTTON);
        CustomButton optionButton = new CustomButton("Options", ResourceReference.BLUE_BUTTON);
        CustomButton quitButton = new CustomButton("Quit", ResourceReference.BLUE_BUTTON);
        centerPanel.add(Box.createRigidArea(new Dimension(0, 30)));
        centerPanel.add(playButton);
        centerPanel.add(optionButton);
        centerPanel.add(quitButton);
        centerPanel.add(Box.createRigidArea(new Dimension(0, 30)));
        centerPanel.setBackground(Color.black);

        JPanel bottomPanel = new JPanel(new FlowLayout());
        CustomButton rulesButton = new CustomButton("Rules", ResourceReference.BLUE_BUTTON);
        CustomButton leaderButton = new CustomButton("Leaderboard", ResourceReference.BLUE_BUTTON);
        bottomPanel.add(rulesButton);
        bottomPanel.add(Box.createRigidArea(new Dimension(5, 0)));
        bottomPanel.add(leaderButton);
        bottomPanel.setBackground(Color.black);

        playButton.addActionListener(e->playButtonClick());
        optionButton.addActionListener(e->optionButtonClick());
        quitButton.addActionListener(e->quitButtonClick());

        //this.getContentPane().setLayout(new BoxLayout(this.getContentPane(), BoxLayout.PAGE_AXIS));
        this.add(titleLabel, BorderLayout.PAGE_START);
        this.add(centerPanel, BorderLayout.CENTER);
        this.add(bottomPanel, BorderLayout.PAGE_END);

        this.getContentPane().setBackground(Color.black);
        this.getContentPane().setForeground(Color.green);

        try
        {
            InputStream fontStream = getClass().getResourceAsStream(ResourceReference.FONT);
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

        try
        {
            URL url = this.getClass().getResource(ResourceReference.BGM_FILE);
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(url);
            bgmClip = AudioSystem.getClip();
            bgmClip.open(audioInputStream);

        }
        catch(LineUnavailableException | IOException | UnsupportedAudioFileException e)
        {
            e.printStackTrace();
        }

        bgmClip.loop(Clip.LOOP_CONTINUOUSLY);
    }
    
    private void initUI()
    {
        setTitle("Projet POO");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        mainMenu();
        this.setResizable(false);
        this.pack();
        startDimension = new Dimension(this.getSize());
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
        
        Canvas gameCanvas = new Canvas();
        this.getContentPane().add(gameCanvas);
        this.getContentPane().setPreferredSize(gameCanvas.getSize());
        this.pack();
        gameCanvas.setFocusable(true);
        gameCanvas.requestFocusInWindow();

        gameCanvas.addGameOverListener(event -> {
            this.getContentPane().removeAll();
            JPanel gameOverPanel = gameOverPanel(event.getScore());
            this.getContentPane().add(gameOverPanel);
            this.getContentPane().setPreferredSize(gameOverPanel.getPreferredSize());
            this.getContentPane().repaint();
            pack();
        });
    }

    private void options()
    {
        JPanel optionPanel = new JPanel();
        optionPanel.setBackground(Color.black);
        optionPanel.setForeground(Color.green);
        optionPanel.setLayout(new BoxLayout(optionPanel, BoxLayout.PAGE_AXIS));

        CustomLabel optionLabel = new CustomLabel("OPTIONS");
        optionPanel.add(optionLabel);
        optionPanel.add(Box.createRigidArea(new Dimension(0, 30)));

        CustomButton menuButton = new CustomButton("Back to main menu", ResourceReference.BLUE_BUTTON);
        menuButton.addActionListener(e->backToMenuClick());

        if(!bgmClip.isControlSupported(FloatControl.Type.VOLUME) || !SoundEffects.ENEMY_EXPLOSION.getClip().isControlSupported(FloatControl.Type.VOLUME))
        {
            CustomLabel volumeNotSupportedLabel = new CustomLabel("Volume adjustment is not supported on your computer");
            optionPanel.add(volumeNotSupportedLabel);
            optionPanel.add(Box.createRigidArea(new Dimension(0, 10)));

            /*JSlider placeHolderSlider = new JSlider(JSlider.HORIZONTAL, 0, 100, 50);
            placeHolderSlider.setBackground(Color.black);
            placeHolderSlider.setForeground(Color.green);

            CustomLabel placeHolderLabel = new CustomLabel("Placeholder : ");

            optionPanel.add(placeHolderLabel);
            optionPanel.add(placeHolderSlider);*/
        }
        else
        {
            final FloatControl BGMControl = (FloatControl) bgmClip.getControl(FloatControl.Type.VOLUME);
            final FloatControl[] SFXControl = new FloatControl[4];
            for(int i = 0 ; i < 4 ; ++i)
            {
                SFXControl[i] = (FloatControl) SoundEffects.values()[i].getClip().getControl(FloatControl.Type.VOLUME);
            }
            JSlider BGMVolumeSlider = new JSlider(JSlider.HORIZONTAL, (int) BGMControl.getMinimum(), (int) BGMControl.getMaximum(), (int) BGMControl.getValue());
            JSlider SFXVolumeSlider = new JSlider(JSlider.HORIZONTAL, (int) SFXControl[0].getMinimum(), (int) SFXControl[0].getMaximum(), (int) SFXControl[0].getValue());

            BGMVolumeSlider.addChangeListener(changeEvent -> BGMControl.setValue(BGMVolumeSlider.getValue()));
            SFXVolumeSlider.addChangeListener(changeEvent -> {
                for(int i = 0 ; i < 4 ; ++i)
                    SFXControl[i].setValue(SFXVolumeSlider.getValue());
            });

            CustomLabel BGMVolumeLabel = new CustomLabel("BGM Volume : ");
            CustomLabel SFXVolumeLabel = new CustomLabel("SFX Volume : ");

            optionPanel.add(BGMVolumeLabel);
            optionPanel.add(BGMVolumeSlider);
            optionPanel.add(Box.createRigidArea(new Dimension(0, 10)));
            optionPanel.add(SFXVolumeLabel);
            optionPanel.add(SFXVolumeSlider);
        }

        JCheckBox muteCheckBox = new JCheckBox("Mute sound");
        muteCheckBox.addItemListener(itemEvent -> {
            if(itemEvent.getStateChange() == ItemEvent.SELECTED)
            {
                bgmClip.stop();
            }
            else
            {
                bgmClip.start();
            }
        });
        muteCheckBox.setAlignmentX(Component.CENTER_ALIGNMENT);
        muteCheckBox.setBackground(Color.black);
        muteCheckBox.setForeground(Color.green);

        CustomLabel playerSkinLabel = new CustomLabel("Choose player texture :");
        JComboBox playerSkinComboBox = new JComboBox(ResourceReference.PLAYER_SKIN_LIST);
        JLabel showSkinLabel = new JLabel(new ImageIcon(LoadImageResource.getTexture((String) playerSkinComboBox.getSelectedItem())));
        playerSkinComboBox.addActionListener(actionEvent -> {
            JComboBox cb = (JComboBox) actionEvent.getSource();
            ResourceReference.PLAYER_TEXTURE = (String )cb.getSelectedItem();
            ResourceReference.PLAYER_HEALTH_TEXTURE = ResourceReference.PLAYER_HEALTH_LIST[cb.getSelectedIndex()];
            showSkinLabel.setIcon(new ImageIcon(LoadImageResource.getTexture(ResourceReference.PLAYER_TEXTURE)));
        });
        playerSkinComboBox.setBackground(Color.black);
        playerSkinComboBox.setForeground(Color.green);
        showSkinLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        optionPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        optionPanel.add(muteCheckBox);
        optionPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        optionPanel.add(playerSkinLabel);
        optionPanel.add(playerSkinComboBox);
        optionPanel.add(showSkinLabel);
        optionPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        optionPanel.add(menuButton);

        changeFont(optionPanel, this.font);

        this.getContentPane().removeAll();
        this.getContentPane().add(optionPanel);
        this.getContentPane().setPreferredSize(optionPanel.getPreferredSize());
        this.getContentPane().repaint();
        this.pack();
    }

    private JPanel gameOverPanel(int score)
    {
        JPanel panel = new JPanel();
        CustomLabel label = new CustomLabel("Game Over! You have scored");
        CustomLabel scoreLabel = new CustomLabel(Integer.toString(score));
        CustomLabel label2 = new CustomLabel("points.");
        CustomButton menuButton = new CustomButton("Back to main menu", ResourceReference.BLUE_BUTTON);

        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));

        panel.setBackground(Color.black);
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
        options();
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
        this.setSize(startDimension);
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
