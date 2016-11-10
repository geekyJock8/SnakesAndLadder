package ENGINE;

/**
 * Created by VIKAS RAJPUT on 28-10-2016.
 */
public class Board
{
    Tile[] tiles;
    Snake[] snakes;
    Ladder[] ladder;

    public Board()
    {
        tiles = new Tile[100];

        for(int i = 0; i < 100; i++)
        {
            tiles[i] = new Tile(i);
        }

        setSnakes();
        setLadder();
    }

    void setSnakes()
    {
        snakes = new Snake[5];

        snakes[0] = new Snake(tiles[25], tiles[8]);
        snakes[1] = new Snake(tiles[67], tiles[23]);
        snakes[2] = new Snake(tiles[84], tiles[48]);
        snakes[3] = new Snake(tiles[98], tiles[71]);
        snakes[4] = new Snake(tiles[96], tiles[69]);
    }

    void setLadder()
    {
        ladder = new Ladder[5];

        ladder[0] = new Ladder(tiles[20], tiles[9]);
        ladder[1] = new Ladder(tiles[43], tiles[27]);
        ladder[2] = new Ladder(tiles[61], tiles[46]);
        ladder[3] = new Ladder(tiles[82], tiles[70]);
        ladder[4] = new Ladder(tiles[80], tiles[67]);
    }

    public boolean isSnakePresent(int tileID)
    {
        for(int i = 0; i < 5; i++)
            if(snakes[i].getHead().getTileIndex() == tileID)
                return true;

        return false;
    }

    public boolean isSnakeTailPresent(int tileID)
    {
        for(int i = 0; i < 5; i++)
            if(snakes[i].getTail().getTileIndex() == tileID)
                return true;

        return false;
    }

    public boolean isLadderTopPresent(int tileID)
    {
        for(int i = 0; i < 5; i++)
            if(ladder[i].getTop().getTileIndex() == tileID)
                return true;

        return false;
    }

    public boolean isLadderPresent(int tileID)
    {
        for(int i = 0; i < 5; i++)
            if(ladder[i].getBottom().getTileIndex() == tileID)
                return true;

        return false;
    }

    public int getSnakeTail(int tileID)
    {
        for(int i = 0; i < 5; i++)
            if(snakes[i].getHead().getTileIndex() == tileID)
                return snakes[i].getTail().getTileIndex();

        return -1;
    }

    public int getLadderTop(int tileID)
    {
        for(int i = 0; i < 5; i++)
            if(ladder[i].getBottom().getTileIndex() == tileID)
                return ladder[i].getTop().getTileIndex();

        return -1;
    }
}
