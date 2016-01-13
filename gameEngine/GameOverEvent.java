package gameEngine;

import java.awt.AWTEvent;

/**
 * Created by dinervoid on 1/13/16.
 */

public class GameOverEvent extends AWTEvent
{
    private boolean gameOver;
    private int score;

    public GameOverEvent(Object o, int i, boolean gameOver, int score)
    {
        super(o, i);
        this.gameOver = gameOver;
        this.score = score;
    }

    public boolean isGameOver()
    {
        return gameOver;
    }

    public int getScore()
    {
        return score;
    }
}
