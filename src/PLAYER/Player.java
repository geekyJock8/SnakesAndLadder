package PLAYER;

import java.awt.*;

/**
 * Created by VIKAS RAJPUT on 31-10-2016.
 */
public class Player
{
    String name;
    int playerPos;
    Color pieceColor;
    boolean firstChance;

    public Player(int x)
    {
        switch(x)
        {
            case 1: name = "WHITE";
                    pieceColor = new Color(255,255,255);
                break;
            case 2: name = "BLACK";
                    pieceColor = new Color(0, 0, 0);
                break;
        }

        playerPos = 0;
        firstChance = true;
    }

    Player(String name)
    {
        this.name = name;
        playerPos = 0;
    }

    public void movePlayer(int diceValue)
    {
        if(playerPos + diceValue < 100)
        playerPos += diceValue;
    }

    public int getPlayerPos()
    {
        return playerPos;
    }

    public String getName()
    {
        return name;
    }

    public void setPlayerPos(int playerPos)
    {
        this.playerPos = playerPos;
    }

    public Color getPieceColor()
    {
        return pieceColor;
    }

    public boolean isFirstChance()
    {
        return firstChance;
    }

    public void firstChanceOver()
    {
        firstChance = false;
    }
}
