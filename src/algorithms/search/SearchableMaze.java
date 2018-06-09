package algorithms.search;

import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.Position;
import java.util.ArrayList;

/**
 * This class represents a maze with a searching problem
 */

public class SearchableMaze implements ISearchable {

    private Maze maze;
    private MazeState startState;
    private MazeState goalState;

    /**
     * A constructor for SearchableMaze
     * @param maze - a given maze
     */
    public SearchableMaze(Maze maze) {
        this.maze = maze;
        startState = new MazeState(maze.getStartPosition());
        goalState = new MazeState(maze.getGoalPosition());
    }

    /**
     * Gets the start state of the maze
     * @return - the start state of the maze
     */
    @Override
    public AState getStartState() {
        return startState;
    }

    /**
     * Gets the goal state of the maze
     * @return - the goal state of the maze
     */
    @Override
    public AState getGoalState() {
        return goalState;
    }

    /**
     * A method that creats and returns an ArrayList of all the neighboring states of a given state which are not walls
     * @param state - a given state
     * @return - an ArrayList of all the neighboring positions of a given state which are not walls
     */
    @Override
    public ArrayList<AState> getSuccessors(AState state) {
        MazeState currState = (MazeState)state;
        ArrayList<AState> successors = new ArrayList<>();
        if(maze.isPositionInMaze(currState.getPosition())) {
            ArrayList<Position> successorsPositions = maze.getReachableNeighbors(currState.getPosition());
            int currRow = currState.getPosition().getRowIndex();
            int currCol = currState.getPosition().getColumnIndex();
            Position leftN = new Position(currRow, currCol - 1);
            Position rightN = new Position(currRow, currCol + 1);
            Position downerN = new Position(currRow + 1, currCol);
            Position upperN = new Position(currRow - 1, currCol);
            for (Position p:successorsPositions) {
                MazeState neighbor = new MazeState(p);
                neighbor.setCost(1);
                successors.add(neighbor);
                // Check the cross neighbors to the left
                if (p.equals(leftN)) {
                    Position crossDown = new Position(currRow + 1, currCol - 1);
                    Position crossUp = new Position(currRow - 1, currCol - 1);
                    // Check the neighbor in the downer-left cross
                    if(maze.isPositionInMaze(crossDown) && !maze.isPositionAWall(crossDown)) {
                        MazeState crossD = new MazeState(crossDown);
                        crossD.setCost(1.5);
                        successors.add(crossD);
                    }
                    // Check the neighbor in the upper-left cross
                    if(maze.isPositionInMaze(crossUp) && !maze.isPositionAWall(crossUp)) {
                        MazeState crossU = new MazeState(crossUp);
                        crossU.setCost(1.5);
                        successors.add(crossU);
                    }

                }
                // Check the cross neighbors to the right
                if (p.equals(rightN)) {
                    Position crossDown = new Position(currRow + 1, currCol + 1);
                    Position crossUp = new Position(currRow - 1, currCol + 1);
                    // Check the neighbor in the downer-right cross
                    if(maze.isPositionInMaze(crossDown) && !maze.isPositionAWall(crossDown)) {
                        MazeState crossD = new MazeState(crossDown);
                        crossD.setCost(1.5);
                        successors.add(crossD);
                    }
                    // Check the neighbor in the upper-right cross
                    if(maze.isPositionInMaze(crossUp) && !maze.isPositionAWall(crossUp)) {
                        MazeState crossU = new MazeState(crossUp);
                        crossU.setCost(1.5);
                        successors.add(crossU);
                    }
                }
                // Check the cross neighbors above
                if (p.equals(upperN)) {
                    Position crossLeft = new Position(currRow - 1, currCol + 1);
                    Position crossRight = new Position(currRow - 1, currCol - 1);
                    // Check the neighbor in the downer-right cross
                    if(maze.isPositionInMaze(crossRight) && !maze.isPositionAWall(crossRight)) {
                        MazeState crossR = new MazeState(crossRight);
                        crossR.setCost(1.5);
                        successors.add(crossR);
                    }
                    // Check the neighbor in the downer-left cross
                    if(maze.isPositionInMaze(crossLeft) && !maze.isPositionAWall(crossLeft)) {
                        MazeState crossL = new MazeState(crossLeft);
                        crossL.setCost(1.5);
                        successors.add(crossL);
                    }
                }
                // Check the cross neighbors below
                if (p.equals(downerN)) {
                    Position crossLeft = new Position(currRow + 1, currCol + 1);
                    Position crossRight = new Position(currRow + 1, currCol - 1);
                    // Check the neighbor in the upper-right cross
                    if(maze.isPositionInMaze(crossRight) && !maze.isPositionAWall(crossRight)) {
                        MazeState crossR = new MazeState(crossRight);
                        crossR.setCost(1.5);
                        successors.add(crossR);
                    }
                    // Check the neighbor in the upper-left cross
                    if(maze.isPositionInMaze(crossLeft) && !maze.isPositionAWall(crossLeft)) {
                        MazeState crossL = new MazeState(crossLeft);
                        crossL.setCost(1.5);
                        successors.add(crossL);
                    }
                }
            }
            return successors;
        }
        else
            return successors;
    }
}