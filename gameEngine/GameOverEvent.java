package gameEngine;

import java.awt.AWTEvent;

/**
 * Event fired when the game is over.
 */

@SuppressWarnings("serial")
public class GameOverEvent extends AWTEvent
{
    /**
     * Score reached by the player when the game ended.
     */
    private int score;

    /**
     * Class constructor
     * @param o The object where the event originated.
     * @param i The event type.
     * @param score Score reached by the player when the game ended.
     */
    public GameOverEvent(Object o, int i, int score)
    {
        super(o, i);
        this.score = score;
    }

    /**
     * Score getter.
     * @return Player's score at the end of the game.
     */
    public int getScore()
    {
        return score;
    }
}
