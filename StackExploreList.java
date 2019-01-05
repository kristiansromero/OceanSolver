/*Kristian Romero
A15752722
keromero@ucsd.edu
* */
import java.util.LinkedList;

public class StackExploreList implements ExploreList{
    /** Add a Square to the worklist, as appropriate
     * @param "The Square to add"
     */
    LinkedList<Square> stack = new LinkedList();
    /*
    Adds the element to the top of the list.
    */
    public void add(Square s)
    {
        stack.add(0,s);
    }

    /** Removes and returns the next Square to be explored
     * @return The next Square to explore */
    public Square getNext()
    {
          return(stack.remove());
    }
    /** isEmpty
     * @return true if the worklist is empty, false otherwise
     */
    public boolean isEmpty()
    {return(stack.size() == 0);}

    /** size of the worklist
     * @return The number of elements in the worklist
     */
    public int size()
    {
        return stack.size();
    }
}
