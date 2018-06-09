package algorithms.search;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Stack;

/**
 * this class represents a Depth First Search algorithm
 */
public class DepthFirstSearch extends ASearchingAlgorithm{

    /**
     * A constructor for DepthFirstSearch
     */
    public DepthFirstSearch ()
    {
        closedList = new HashSet<AState>();
    }

    public Solution solve(ISearchable iSearchable)
    {
        //going in reverse from goal to start.
        AState startSt = iSearchable.getStartState();
        AState finishSt = iSearchable.getGoalState();
        Stack <AState> stack = new Stack<>();
        stack.push(startSt);
        numberOfNodesEvaluated++;
        boolean goalFound = false;
        AState tmp = startSt;
        while (!stack.isEmpty() && !goalFound)
        {
            tmp = stack.pop();
            ArrayList<AState> successors = iSearchable.getSuccessors(tmp);
            //getting all neighbors, set their values and push to the stack.
            for (AState neighbor:successors)
            {
                if (!(isStateInList(neighbor) || stack.contains(neighbor)))
                {
                    neighbor.setCameFrom(tmp);
                    stack.push(neighbor);
                    numberOfNodesEvaluated++;
                    if (neighbor.equals(finishSt))
                    {
                        finishSt.setCameFrom(tmp);
                        goalFound = true;
                        break;
                    }
                }
            }
            closedList.add(tmp);
        }
        return getSolution(finishSt);
    }


    @Override
    public String getName() {
        return "DepthFirstSearch";
    }



}
