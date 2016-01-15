package gui;

import java.io.IOException;
import java.net.URL;
import javax.sound.sampled.*;

/**
 * Enumeration containing the different sound effects for the game.
 */
public enum SoundEffects
{
    /**
     * Enemy explosion sound effect.
     */
    ENEMY_EXPLOSION(ResourceReference.SFX_ENEMY_EXPLOSION),

    /**
     * Enemy shooting sound effect.
     */
    ENEMY_LASER(ResourceReference.SFX_ENEMY_LASER),

    /**
     * Player explosion sound effect.
     */
    PLAYER_EXPLOSION(ResourceReference.SFX_PLAYER_EXPLOSION),

    /**
     * Player laser sound effect.
     */
    PLAYER_LASER(ResourceReference.SFX_PLAYER_LASER);

    /**
     * Clip containing the sound effect.
     */
    private Clip clip;

    /**
     * Class constructor.
     * @param fileName Path to the sound effect file.
     */
    SoundEffects(String fileName)
    {
        try
        {
            URL url = this.getClass().getResource(fileName);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(url);
            clip = AudioSystem.getClip();
            clip.open(audioStream);
        }
        catch(LineUnavailableException | IOException | UnsupportedAudioFileException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Plays the clip.
     * <p>
     *     If the clip is already running, stops it, sets it back to 0 and starts it again.
     * </p>
     */
    public void play()
    {
        if(clip.isRunning())
            clip.stop();
        clip.setFramePosition(0);
        clip.start();
    }

    /**
     * Getter for clip.
     * @return clip;
     */
    public Clip getClip()
    {
        return clip;
    }
}
