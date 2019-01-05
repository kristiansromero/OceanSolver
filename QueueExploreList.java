/*Kristian Romero
A15752722
keromero@ucsd.edu
* */
import java.util.LinkedList;

public class QueueExploreList implements ExploreList{
    /** Add a Square to the worklist, as appropriate
     * @param "The Square to add"
     */
    LinkedList<Square> queue = new LinkedList();


    public void add(Square s)
    {
        queue.addLast(s);
    }

    /** Removes and returns the next Square to be explored
     * @return The next Square to explore */
    public Square getNext()
    {
        return(queue.remove());
    }

    /** isEmpty
     * @return true if the worklist is empty, false otherwise
     */
    public boolean isEmpty()
    {return(queue.size() == 0);}

    /** size of the worklist
     * @return The number of elements in the worklist
     */
    public int size()
    {return(queue.size());}
}
