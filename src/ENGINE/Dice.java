package ENGINE;

/**
 * Created by VIKAS RAJPUT on 28-10-2016.
 */
public class Dice
{
    int rollValue;

    public Dice()
    {
        rollValue = 0;
    }

    public int getRollValue()
    {
        return rollValue;
    }

    public void rollDice()
    {
        rollValue = (int) (Math.random()*6) + 1;
    }
}
