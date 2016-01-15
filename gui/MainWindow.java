/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import javax.sound.sampled.*;
import javax.swing.*;

import gameEngine.Canvas;

/**
 * Main window of the application
 */
@SuppressWarnings("serial")
public class MainWindow extends JFrame
{
    /**
     * Font used to display all text.
     */
    private Font font;

    /**
     * Background music for the game.
     */
    private Clip bgmClip;

    /**
     * Dimension of the main menu
     */
    private Dimension startDimension;

    /**
     * Class constructor
     */
    public MainWindow()
    {
        super();
        this.initUI();
    }

    /**
     * Initializes the main menu.
     */
    private void mainMenu()
    {
        CustomLabel titleLabel = new CustomLabel("Welcome to SPACE INVADERS!");

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.PAGE_AXIS));
        CustomButton playButton = new CustomButton("New Game", ResourceReference.BLUE_BUTTON);
        playButton.addActionListener(actionEvent -> play());
        playButton.setMnemonic(KeyEvent.VK_N);
        CustomButton rulesButton = new CustomButton("Rules", ResourceReference.BLUE_BUTTON);
        rulesButton.addActionListener(actionEvent -> rulesButtonClick());
        rulesButton.setMnemonic(KeyEvent.VK_R);
        CustomButton optionButton = new CustomButton("Settings", ResourceReference.BLUE_BUTTON);
        optionButton.addActionListener(actionEvent -> options());
        optionButton.setMnemonic(KeyEvent.VK_S);
        CustomButton quitButton = new CustomButton("Quit", ResourceReference.BLUE_BUTTON);
        quitButton.addActionListener(actionEvent -> System.exit(0));
        quitButton.setMnemonic(KeyEvent.VK_Q);
        centerPanel.add(Box.createRigidArea(new Dimension(0, 30)));
        centerPanel.add(playButton);
        centerPanel.add(rulesButton);
        centerPanel.add(optionButton);
        centerPanel.add(quitButton);
        centerPanel.add(Box.createRigidArea(new Dimension(0, 30)));
        centerPanel.setBackground(Color.black);


        this.add(titleLabel, BorderLayout.PAGE_START);
        this.add(centerPanel, BorderLayout.CENTER);

        this.getContentPane().setBackground(Color.black);
        this.getContentPane().setForeground(Color.green);

        //Load the custom font
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

    }

    /**
     * Sets up the main window properties.
     */
    private void initUI()
    {
        setTitle("Projet POO");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        mainMenu();
        //Load the background music
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

        this.setResizable(false);
        this.pack();
        startDimension = new Dimension(this.getSize());
    }

    /**
     * Changes the font of the component passed as the first parameter and all of its sub-components' as well.
     * @param component The component of which the font is to be changed.
     * @param font The font to be applied to component.
     */
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

    /**
     * Invokes a Canvas object to start the game
     * <p>
     *     Clears the window of its current content and replaces it by the game canvas.
     *     When the GameOverEvent is received, the game canvas is cleared and the game over screen is displayed.
     *
     */
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

    /**
     * Displays the settings menu
     * <p>
     *     If the volume control is not supported by the system, displays a message telling the user so.
     * </p>
     */
    private void options()
    {
        JPanel optionPanel = new JPanel();
        optionPanel.setBackground(Color.black);
        optionPanel.setForeground(Color.green);
        optionPanel.setLayout(new BoxLayout(optionPanel, BoxLayout.PAGE_AXIS));

        CustomLabel optionLabel = new CustomLabel("SETTINGS");
        optionPanel.add(optionLabel);
        optionPanel.add(Box.createRigidArea(new Dimension(0, 30)));

        CustomButton menuButton = new CustomButton("Back to main menu", ResourceReference.BLUE_BUTTON);
        menuButton.addActionListener(e->backToMenuClick());
        menuButton.setMnemonic(KeyEvent.VK_B);

        //
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

        JCheckBox muteCheckBox = new JCheckBox("Mute BGM");
        muteCheckBox.addItemListener(itemEvent -> {
            if(itemEvent.getStateChange() == ItemEvent.SELECTED)
                bgmClip.stop();
            else
                bgmClip.start();
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

    /**
     * <p>
     *     Creates the panel used to display the game over message.
     * </p>
     * @param score Player score at the end of the game.
     * @return JPanel displaying score and a back to menu button.
     */
    private JPanel gameOverPanel(int score)
    {
        JPanel panel = new JPanel();
        CustomLabel label = new CustomLabel("Game Over! You have scored");
        CustomLabel scoreLabel = new CustomLabel(Integer.toString(score));
        CustomLabel label2 = new CustomLabel("points.");
        CustomButton menuButton = new CustomButton("Back to main menu", ResourceReference.BLUE_BUTTON);

        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));

        panel.setBackground(Color.black);
        menuButton.addActionListener(actionEvent -> backToMenuClick());
        menuButton.setMnemonic(KeyEvent.VK_B);

        panel.add(label);
        panel.add(scoreLabel);
        panel.add(label2);
        panel.add(menuButton);

        changeFont(panel, font);

        return panel;
    }

    /**
     * <p>Displays the rules of the game.</p>
     * <p>actionEvent handler called when the "Rules" button is clicked in the main menu.</p>
     */
    private void rulesButtonClick()
    {
        JPanel rulesPanel = new JPanel();
        rulesPanel.setLayout(new BoxLayout(rulesPanel, BoxLayout.PAGE_AXIS));
        rulesPanel.setBackground(Color.black);
        JLabel rulesLabel = new JLabel(
                "<html><h1>Space Invaders Rules</h1><p>The goal of the game is to survive as long as possible against waves of approaching aliens.</p><p>The aliens must be destroyed by shooting at them, using the space bar.</p><p>You can move along the horizontal axis by pressing the left and right arrow keys.</p><p>The enemies must not be allowed to reach the ground or the game will be lost.</p><p>They are also able to shoot, so care must be taken to avoid the projectiles.</p></html>");
        rulesLabel.setForeground(Color.green);
        rulesLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        CustomButton backButton = new CustomButton("Back", ResourceReference.BLUE_BUTTON);
        backButton.addActionListener(actionEvent -> backToMenuClick());
        backButton.setMnemonic(KeyEvent.VK_B);

        rulesPanel.add(rulesLabel);
        rulesPanel.add(backButton);

        this.changeFont(rulesPanel, font);

        this.getContentPane().removeAll();
        this.getContentPane().add(rulesPanel);
        this.getContentPane().setPreferredSize(rulesPanel.getPreferredSize());
        this.repaint();
        this.pack();
    }

    /**
     * <p>Displays the main menu</p>
     * <p>actionEvent handler called when the back to menu button is clicked from the different screens.</p>
     */
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
