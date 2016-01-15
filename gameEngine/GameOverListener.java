package gameEngine;

import java.util.EventListener;

/**
 * Listener for GameOverEvent.
 */
public interface GameOverListener extends EventListener
{
    void gameOverReceived(GameOverEvent event);
}
