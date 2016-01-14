package gameEngine;

import java.awt.AWTEvent;

/**
 * Created by dinervoid on 1/13/16.
 */

@SuppressWarnings("serial")
public class GameOverEvent extends AWTEvent
{
    private int score;

    public GameOverEvent(Object o, int i, int score)
    {
        super(o, i);
        this.score = score;
    }

    public int getScore()
    {
        return score;
    }
}
