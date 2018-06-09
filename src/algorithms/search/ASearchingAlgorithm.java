package algorithms.search;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Stack;

/**
 * An abstract class that represents a searching algorithm
 */
public abstract class ASearchingAlgorithm implements ISearchingAlgorithm
{
    protected int numberOfNodesEvaluated = 0;

    protected HashSet<AState> closedList;

    /**
     * solves a given searchable problem with a searching algorithm and returns a solution
     * @param iSearchable - a given searchable problem
     * @return - a solution to the searchable problem
     */
    public abstract Solution solve(ISearchable iSearchable);

    /**
     * gets the name of the searching algorithm
     * @return - the name of the searching algorithm
     */
    public abstract String getName();

    /**
     * adds a given state to the closed list
     * @param state - a given state
     */
    protected void addToClosedList(AState state) {
        closedList.add(state);
    }

    /**
     * checks whether a given state is in the closed list
     * @param state - a given state
     * @return - true if the state was in the list, false if not
     */
    protected boolean isStateInList(AState state){
        return closedList.contains(state);
    }

    @Override
    public String getNumberOfNodesEvaluated() {
        return Integer.toString(numberOfNodesEvaluated);
    }

    /**
     * gets the solution of the searchable problem
     * @param goal - the final state of the solution
     * @return - the solution of the searchable problem
     */
    protected Solution getSolution(AState goal){
        ArrayList<AState> solution = new ArrayList<>();
        Stack<AState> tempStack = new Stack<>();
        AState currNode = goal;
        AState previousNode = currNode.getCameFrom();
        tempStack.push(currNode);
        // tracking back the states until we get to the start state
        while(previousNode != null) {
            currNode = previousNode;
            previousNode = currNode.getCameFrom();
            tempStack.push(currNode);
        }
        while(!tempStack.isEmpty())
            solution.add(tempStack.pop());
        return (new Solution(solution));
    }
}

