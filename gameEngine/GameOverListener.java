package gameEngine;

import java.util.EventListener;

/**
 * Created by dinervoid on 1/13/16.
 */
public interface GameOverListener extends EventListener
{
    void gameOverReceived(GameOverEvent event);
}
