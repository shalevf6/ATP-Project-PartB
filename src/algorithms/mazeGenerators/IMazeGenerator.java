package algorithms.mazeGenerators;

/**
 * An interface for maze generator algorithms
 */
public  interface IMazeGenerator
{
    /**
     * Generates a new maze from given proportions
     * @param rowNum - the number of rows in the maze
     * @param colNum - the number of columns in the maze
     * @return - a new maze
     */
    public Maze generate(int rowNum, int colNum) ;

    /**
     * returns the amount of time it takes to create a new maze
     * @param rowNum - the number of rows in the maze
     * @param colNum - the number of columns in the maze
     * @return - the amount of time it takes to create a new maze
     */
    public long measureAlgorithmTimeMillis(int rowNum, int colNum);
}

