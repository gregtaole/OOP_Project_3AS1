package resources;

/**
 * Created by dinervoid on 1/14/16.
 */

import java.io.IOException;
import java.net.URL;
import javax.sound.sampled.*;

public enum SoundEffects
{
    ENEMY_EXPLOSION(ResourceReference.SFX_ENEMY_EXPLOSION),
    ENEMY_LASER(ResourceReference.SFX_ENEMY_LASER),
    PLAYER_EXPLOSION(ResourceReference.SFX_PLAYER_EXPLOSION),
    PLAYER_LASER(ResourceReference.SFX_PLAYER_LASER);

    private Clip clip;

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

    public void play()
    {
        if(clip.isRunning())
            clip.stop();
        clip.setFramePosition(0);
        clip.start();
    }

    public Clip getClip()
    {
        return clip;
    }
}
