package algorithms.mazeGenerators;
import  java.util.Random;

/**
 * this class represents a simple maze making with making sure that there is a path from the beginning to the end positions.
 */
public class SimpleMazeGenerator extends AMazeGenerator
{
	public SimpleMazeGenerator(){}

	/**
	 * constructor of a simple maze
	 * @param rowNum - the number of rows of the maze.
	 * @param colNum - the number of columns of the maze.
	 * @return - the created maze .
	 */
	public Maze generate(int rowNum, int colNum) {
		if (rowNum <= 0 || colNum <= 0 || (rowNum == 1 && colNum == 1)) {
			rowNum = 10;
			colNum = 10;
		}
		int[][] simpleMaze = new int[rowNum][colNum];
		Position startPo = getRandomFreeFramePosition(simpleMaze);		//getting the start position randomly.
		Position endPo = getRandomFreeFramePosition(simpleMaze);		////getting the start position randomly.
		generateSimpleM(simpleMaze);
		boolean samePo = false;
		while (!samePo)		//making sure the positions are not the same and not on the same edge.
		{
			if (startPo.getColumnIndex() == endPo.getColumnIndex() || startPo.getRowIndex() == endPo.getRowIndex())
				endPo = getRandomFreeFramePosition(simpleMaze);
			else
				samePo = true;
		}
		generatePath(simpleMaze,startPo,endPo);
		return new Maze(simpleMaze,startPo,endPo);
	}

	/**
	 * the function fills the cells of the maze with paths - 0 or walls - 1 randomly.
	 * @param maze - the given maze that should be filled with walls and paths.
	 */
	private void generateSimpleM (int[][] maze)
	{
		double rnd = 0;
		double half = 0.5;
		for (int i = 0; i < maze.length; i++)
		{
			for (int j = 0; j < maze[0].length; j++)
			{
				rnd = Math.random();
				if (rnd >= half)
					maze[i][j] = 1;
				else
					maze[i][j] = 0;
			}
		}
	}

	/**
	 * this function create path in the given maze with given start and end positions.
	 * @param maze - the given maze.
	 * @param startP - the given start position.
	 * @param endP - the given end position.
	 */
	private void generatePath (int[][] maze,Position startP, Position endP)
	{
		int startX = startP.getRowIndex();
		int startY = startP.getColumnIndex();
		int endX = endP.getRowIndex();
		int endY = endP.getColumnIndex();
		char startEdge;		//to indicate in which edge the start is.
		char endEdge;		////to indicate in which edge the end is.
		//the start and the end can not be on the same edge using U for up and down and L for left and right.
		if (startX == 0 || startX == maze.length - 1)		//start point is up edge or down edge
		{
			startEdge = 'U';
			for (int i = 0; i < maze.length; i++)       //need to fill the col from up to down
				maze[i][startY] = 0;
		}
		else		// start point is left of right edge.
		{
			startEdge = 'L';
			for (int i = 0; i < maze[0].length; i++)		//fill the row from left to right.
				maze[startX][i] = 0;
		}

		if (endX == 0 || endX == maze.length - 1)		//end point is up edge or down edge
		{
			endEdge = 'U';
			for (int i = 0; i < maze.length; i++)
				maze[i][endY] = 0;

		}
		else		//end point is left of right edge.
		{
			endEdge = 'L';
			for (int i = 0; i < maze[0].length; i++)		//fill the row from left to right.
				maze[endX][i] = 0;
		}

		if (startEdge == endEdge && startEdge == 'U')		//one is up and one is down, need to make a row filled with 0.
		{
			int midRow = ((maze.length -1)/2);
			for (int i = 0; i < maze[0].length; i++)
				maze[midRow][i] = 0;
		}

		if (startEdge == endEdge && startEdge == 'L')	//one on the left and one on the right, need to fill midcol with 0.
		{
			int midCol = ((maze[0].length-1)/2);
			for (int i = 0; i < maze.length; i++)
				maze[i][midCol] = 0;
		}
	}

	/**
	 * the function choose randomly between all position on the edges and check that its not a wall.
	 * @param maze - the given maze.
	 * @return - a position on one of the edges which is not a wall.
	 */
	private Position getRandomFreeFramePosition(int[][] maze) {
		Position[] frameOfMaze = new Position[maze[0].length * 2 + (maze.length * 2) - 4];
		int counter = 0;
		for (int i = 0; i < maze[0].length; i++) {		//adding both rows to the new array.
			frameOfMaze[counter] = new Position(0,i);
			counter++;
			frameOfMaze[counter] = new Position(maze.length - 1,i);
			counter++;
		}
		for (int i = 1; i < maze.length - 1; i++) {		//adding both colomns to the new array.
			frameOfMaze[counter] = new Position(i,0);
			counter++;
			frameOfMaze[counter] = new Position(i,maze[0].length - 1);
			counter++;
		}
		Random random = new Random();
		int randFramePosition = random.nextInt(frameOfMaze.length);
		Position ans = frameOfMaze[randFramePosition];		//getting random cell of the array.
		boolean isWall = false;
		while (!isWall)		//making sure that the position is not a wall.
		{
			if (maze[ans.getRowIndex()][ans.getColumnIndex()] == 1 || areCorner(ans, maze.length - 1 , maze[0].length - 1) )	//its a wall, need new cell.
			{
				random = new Random();
				randFramePosition = random.nextInt(frameOfMaze.length);
				ans = frameOfMaze[randFramePosition];
			}
			else
				isWall = true;
		}
		return ans;
	}

	public boolean areCorner (Position currPo, int numOfRows, int numOfCol)
	{
		boolean upLeft = currPo.getColumnIndex() == 0 && currPo.getRowIndex() == 0;
		boolean upRight = currPo.getRowIndex() == 0 && currPo.getColumnIndex() == numOfRows;
		boolean downLeft = currPo.getRowIndex() == numOfRows && currPo.getColumnIndex() == 0;
		boolean downRight = currPo.getRowIndex() == numOfRows && currPo.getColumnIndex() == numOfCol;
		return (upLeft || upRight || downLeft || downRight);
	}
}