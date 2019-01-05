/*Kristian Romero
A15752722
keromero@ucsd.edu
Maze Solver
*/
import java.util.ArrayList;

public class Explorer {

    // The exploreList to hold the search as it proceeds.
    private ExploreList exploreList;
    // The Sea to solve.
    private Sea sea = new Sea();
    // The path to the Nemo, if any
    private String path="Found Nemo!";
    private Square temp = new Square();

    private boolean gameOver = false; // game over, exit has(n't) been found
    private boolean foundNemo = false; // exit has been found

    public int getTotalVisited(){
        return  this.getSea().countVisited();
    }


    public void setGameOver() {
        gameOver = true;
    }

    public void setFoundNemo() {
        foundNemo = true;
    }

    public boolean gameOver() {
        return gameOver || foundNemo;
    }

    public boolean isFoundNemo() {
        return foundNemo;
    }

    public void makeEmpty() {
        // remove Squares until empty
        while (!exploreList.isEmpty()) {
            exploreList.getNext();
        }
    }

    public ExploreList getExploreList() {
        return this.exploreList;
    }

    /** isEmpty
    * @return true if the exploreList is empty, false otherwise
    */
    public boolean isEmpty() {
        return this.exploreList.isEmpty();
    }

    /** size of the exploreList
    * @return The number of elements in the exploreList
    */
    public int size() {
        return this.exploreList.size();
    }

    /** Make a new Solver with a given Sea and Worklist
    * @param theSea The Sea to solve
    * @param theExploreList The exploreList to use
    */
    Explorer(Sea theSea, ExploreList theExploreList){
        this.sea = theSea;
        this.exploreList = theExploreList;
    }

    /**
    * Get the Sea object
    * @return the sea
    */
    public Sea getSea() {
        return this.sea;
    }

    /**
     * Solve the sea, if possible.
     * If a solution is found, set the path variable and the
     * foundNemo variable appropriately.
     */
    public void solve() {
        Square tempSq;              //Instantiating temporary working variables.
        ArrayList<Square> tal;
        exploreList.add(sea.getStart());              //Lines until before the while loop covers the initial case where
        sea.getStart().setVisited();                  // start has no previous square.
        //Put starting square on work list
        tal = sea.getAdjacentArea(sea.getStart());
        for(int i = 0; i < tal.size(); i++)           //Loads the workingList with the adjacent area of start point.
        {
            tal.get(i).setPrevious(sea.getStart());
            exploreList.add(tal.get(i));
        }
        //While ExploreList is not empty
        while(!exploreList.isEmpty())
        {
            tempSq = step();
            if (tempSq.getType() == 3)
            {
                setFoundNemo();
                setPath(tempSq);
                break;
            }
        }
    }

    /** Take the next step toward the goal
    * PRECONDITION: The exploreList is not empty
    * @return The next Square that has just been visited.
    */
    public Square step() {
        /*
        Remove the next Square from ExploreList and mark it as “visited”
        This action is called “exploring a square”
        We’ll call this the “current square”
        */
        temp = exploreList.getNext();
        while (temp.isVisited())
        {
            if(size() == 0)
                return temp;
            temp = exploreList.getNext();
        }
        sea.getSea()[temp.getRow()][temp.getCol()].setVisited();
        ArrayList<Square> tal = sea.getAdjacentArea(sea.getSea()[temp.getRow()][temp.getCol()]);
        for(int i = 0; i < tal.size(); i++)
        {
            if(tal.get(i).getPrevious() == null)
                tal.get(i).setPrevious(temp);

            exploreList.add(tal.get(i));
        }
        return temp;
    }

    // Set the squares in the path appropriately and set the path
    // from start to finish.
    public void setPath(Square finish) {
        ArrayList<Square> fPath = new ArrayList<>();
        Square holder;
        holder = finish.getPrevious();
        finish.setFinalPath();
        holder.setFinalPath();
        fPath.add(finish);
        fPath.add(0, holder);
        while(holder != sea.getStart())
        {
            holder = holder.getPrevious();
            holder.setFinalPath();
            fPath.add(0, holder);
        }
        path += "\nPath from start to finish: ";
        for (int i = 0; i < fPath.size(); i++)
            path += "["+fPath.get(i).getRow() + ","+ fPath.get(i).getCol() + "] ";
    }

    /**
     * Get the number of elements that are left on the exploreList
     * @return The size of the exploreList
     */
    public int getExploreListSize() {
        return exploreList.size();
    }

    /**
     * Get the path from start to exit, if any.
     * @return Path from S to E as a list of coordinates [row,col]
     * If not solvable, the path is a message
     */
    public String getPath() {
        if (foundNemo) {
            return path;
        } else {
            path = "Uh Oh!! Could not find Nemo!!";
            return path;
        }
    }


    /** A program to solve a sea using either BFS or DFS */
    public static void main(String[] args) {
        Sea sea;
        Explorer dory, marlin;
        int doryBetter=0, marlinBetter=0;
        for (int j=0; j<1; j++) {
            for (int i = 5; i < 105; i += 5) {
                sea = new Sea(i, i);
                dory = new Explorer(sea, new QueueExploreList());
                dory.solve();
                int d, m;
                d = dory.getTotalVisited();
                sea.clearMaze();
                marlin = new Explorer(sea, new StackExploreList());
                marlin.solve();
                m = marlin.getTotalVisited();
                if (d>m) doryBetter++;
                else marlinBetter ++;
            }
        }
        System.out.println("Number of times Dory found Nemo faster: "+doryBetter+"\nNumber of times Marlin found Nemo Faster: "+marlinBetter);
    }
}
