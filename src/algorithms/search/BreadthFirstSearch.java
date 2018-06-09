package algorithms.search;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;

/**
 * this class represents a Breadth First Search algorithm
 */
public class BreadthFirstSearch extends BFSandBestFS {

    private Queue<AState> openList;

    /**
     * A constructor for BreadthFirstSearch
     */
    public BreadthFirstSearch() {
        openList = new LinkedList<>();
        closedList = new HashSet<>();
    }

    protected AState popOpenList() {
        return openList.poll();
    }

    protected boolean isListEmpty() {
        return openList.isEmpty();
    }

    protected void addToList(AState s) {
        numberOfNodesEvaluated++;
        openList.add(s);
    }

    @Override
    public String getName() {
        return "BreadthFirstSearch";
    }
}
