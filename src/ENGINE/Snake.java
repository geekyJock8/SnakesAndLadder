package ENGINE;

/**
 * Created by VIKAS RAJPUT on 28-10-2016.
 */
public class Snake
{
    final Tile head;
    final Tile tail;

    Snake(Tile head, Tile tail)
    {
        this.head = head;
        this.tail = tail;
    }

    public Tile getHead()
    {
        return head;
    }

    public Tile getTail()
    {
        return tail;
    }
}
