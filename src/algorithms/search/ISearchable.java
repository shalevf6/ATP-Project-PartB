package algorithms.search;

import java.util.ArrayList;

/**
 * An interface for searchable problems
 */
public interface ISearchable
{
    /**
     * Gets the start state of the maze
     * @return - the start state of the maze
     */
    AState getStartState();

    /**
     * Gets the goal state of the maze
     * @return - the goal state of the maze
     */
    AState getGoalState();

    /**
     * A method that creats and returns an ArrayList of all the successor states of a given state
     * @param state - a given state
     * @return - an ArrayList of all the successor states of a given state
     */
    ArrayList<AState> getSuccessors(AState state);
}

