package ENGINE;

import PLAYER.Player;

/**
 * Created by VIKAS RAJPUT on 31-10-2016.
 */
public class Game
{
    public Board board;
    public Player[] p;
    public Dice dice;
    int chance;

    public Game()
    {
        board = new Board();
        p = new Player[2];
        dice = new Dice();
        chance = 0;

        p[0] = new Player(1);
        p[1] = new Player(2);
    }

    public void makeMove()
    {
        dice.rollDice();
        int diceValue = dice.getRollValue();
        int playerId = chance % 2;

        if (p[playerId].isFirstChance())
        {
            if (diceValue == 6) {
                p[playerId].firstChanceOver();
            }
        }

        else
            {
                p[playerId].movePlayer(diceValue);
                snakeCheck();
                ladderCheck();
            }

    }

    public boolean isGameOver()
    {
        int playerId = chance % 2;

        if(p[playerId].getPlayerPos() == 99)
            return true;
        else return false;
    }

    public void nextChance()
    {
        chance++;
    }

    public int getChance()
    {
        return (chance % 2);
    }

    public void snakeCheck()
    {
        int playerId = chance % 2;
        if(board.isSnakePresent(p[playerId].getPlayerPos()))
        {
            p[playerId].setPlayerPos(board.getSnakeTail(p[playerId].getPlayerPos()));
        }
    }

    public void ladderCheck()
    {
        int playerId = chance % 2;
        if(board.isLadderPresent(p[playerId].getPlayerPos()))
        {
            p[playerId].setPlayerPos(board.getLadderTop(p[playerId].getPlayerPos()));
        }

    }

}
