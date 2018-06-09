package algorithms.search;

import java.util.ArrayList;

/**
 * an abstract class that represents a Best First Search algorithm or a Breadth First Search algorithm
 */
public abstract class BFSandBestFS extends ASearchingAlgorithm{

    public Solution solve(ISearchable iSearchable) {
        if(iSearchable == null)
            return null;
        AState s = iSearchable.getStartState();
        AState g = iSearchable.getGoalState();
        boolean goalFound = false;
        addToClosedList(s);
        addToList(s);
        while (!isListEmpty() && !goalFound) {
            AState u = popOpenList();
            ArrayList<AState> successors = iSearchable.getSuccessors(u);
            for (AState v : successors) {
                if (!isStateInList(v)) {
                    if (v.equals(g)) {
                        goalFound = true;
                        addToClosedList(g);
                        g.setCameFrom(u);
                        break;
                    }
                    addToClosedList(v);
                    v.setCameFrom(u);
                    if(this instanceof BestFirstSearch)
                        v.setCost(v.getCost() + u.getCost());
                    addToList(v);
                }
            }
        }
        return getSolution(g);
    }

    /**
     * gets an AState from the open list
     * @return - an AState from the open list
     */
    protected abstract AState popOpenList();

    /**
     * checks whether the open list is empty
     * @return - true if the open list is empty, false otherwise
     */
    protected abstract boolean isListEmpty();

    /**
     * adds an AState to the open list
     * @param s - an AState to be added to the open list
     */
    protected abstract void addToList(AState s);
}
