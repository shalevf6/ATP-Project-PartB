package algorithms.mazeGenerators;

/**
 * the class represents a single maze generator.
 */
public abstract class AMazeGenerator implements IMazeGenerator
{
	public AMazeGenerator(){}

	public abstract Maze generate(int rowNum, int colNum) ;	//implemented in the heirs.

	/**
	 * the function check the time that takes to generate a maze.
	 * @param rowNum - the number of the row of the generated maze.
	 * @param colNum - the number of the column of the generated maze.
	 * @return - the time that took to generate the maze.
	 */
	public long measureAlgorithmTimeMillis(int rowNum, int colNum) {
		long timeBefore = System.currentTimeMillis();
		generate(rowNum,colNum);
		long timeAfter = System.currentTimeMillis();
		return timeAfter - timeBefore;
	}
}