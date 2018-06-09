package algorithms.search;

import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Comparator;

/**
 * this class represents a Best First Search algorithm
 */
public class BestFirstSearch extends BFSandBestFS {

    private PriorityQueue<AState> openList;

    /**
     * A constructor for BestFirstSearch
     */
    public BestFirstSearch() {
        Comparator<Object> comparator = new AstateComparator();
        openList = new PriorityQueue<>(comparator);
        closedList = new HashSet<>();

    }

    protected AState popOpenList() {
        return openList.poll();
    }

    /**
     * A comparator class to be used with the Priority Queue that represents the open list
     */
    public class AstateComparator implements Comparator<Object>
    {
        @Override
        public int compare(Object x, Object y)
        {
            AState x1= (AState) x;
            AState y1 =(AState) y;

            if (x1.getCost() < y1.getCost())
            {
                return -1;
            }
            if (x1.getCost() > y1.getCost())
            {
                return 1;
            }
            return 0;
        }
    }
    protected void addToList(AState state) {
        numberOfNodesEvaluated++;
        openList.add(state);
    }

    protected boolean isListEmpty(){
        return openList.isEmpty();
    }

    @Override
    public String getName() {
        return "BestFirstSearch";
    }
}
