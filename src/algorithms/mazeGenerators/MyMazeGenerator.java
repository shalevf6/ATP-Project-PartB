package algorithms.mazeGenerators;
import java.util.ArrayList;
import java.util.Random;

/**
 * This class represents a maze creator using the randomized Prim's algorithm
 */

public class MyMazeGenerator extends AMazeGenerator
{
	/**
	 * An empty constructor
	 */
	public MyMazeGenerator(){}

	/**
	 *Generates (and returns) a new maze from given row and column sizes by using the randomized Prim's algorithm
	 * @param rowNum - the given number of rows in the maze to be generated
	 * @param colNum - the given number of columns in the maze to be generated
	 * @return - a new maze
	 */
	public Maze generate(int rowNum, int colNum) {
		if (rowNum <= 0 || colNum <= 0 || (rowNum == 1 && colNum == 1)) {
			rowNum = 10;
			colNum = 10;
		}
		int[][] maze = getOnlyWallsMaze(rowNum,colNum);
		ArrayList<Position> wallList = new ArrayList<>();
		Position startCell = getRandomStartPosition(maze);
		maze[startCell.getRowIndex()][startCell.getColumnIndex()] = 0;
		addToWallList(maze,wallList,startCell,true);
		while (!wallList.isEmpty()) {
			Position randomWall = getRandomWall(wallList);
			if( checkIfTheWallNeedsToChange(maze,randomWall)){
				maze[randomWall.getRowIndex()][randomWall.getColumnIndex()] = 0;
				addToWallList(maze,wallList,randomWall,false);
			}
		}
		return (new Maze(maze,startCell,getRandomGoalPosition(maze,startCell)));
	}

	/**
	 * Creates a maze which only has walls in it
	 * @param rowNum - the given number of rows in the maze
	 * @param colNum - the given number of columns in the maze
	 * @return - a maze which has only walls in it (all cells have the value - 1)
	 */
	private int[][] getOnlyWallsMaze(int rowNum, int colNum){
		int[][] maze = new int[rowNum][colNum];
		for (int i = 0; i < maze.length; i++)
			for (int j = 0; j < maze[0].length; j++)
				maze[i][j] = 1;
		return maze;
	}

	/**
	 * Adds more walls, if necessary, to the wall list
	 * @param maze - a maze to check if a cell is a wall or not
	 * @param wallList - an ArrayList of Positions that are walls (have value of 1 in the maze)
	 * @param cell - the cell from which we need to check if its surrounding cells can be added to the walls list
	 * @param isFirstWalls - a boolean parameter that tells whether these are the first walls that are added to the wall list or not
	 */
	private void addToWallList(int[][] maze,ArrayList<Position> wallList, Position cell, boolean isFirstWalls) {
		boolean notAlreadyInList = true;
		int wallRow = cell.getRowIndex();
		int wallCol = cell.getColumnIndex();
		//Checking if the position to the right of the cell is out of the maze's bounds
		if(wallCol + 1 <= maze[0].length - 1) {
			if(maze[wallRow][wallCol + 1] == 1) {
				if(!isFirstWalls && isWallInList(wallList,new Position(wallRow,wallCol + 1)))
				{
					notAlreadyInList = false;
				}
				if(notAlreadyInList) {
					Position newP = new Position(wallRow, wallCol + 1);
					wallList.add(newP);
				}
			}
		}
		notAlreadyInList = true;
		//Checking if the position to the left of the cell is out of the maze's bounds
		if (wallCol - 1 >= 0){
			if(maze[wallRow][wallCol - 1] == 1) {
				if(!isFirstWalls && isWallInList(wallList,new Position(wallRow,wallCol - 1)))
				{
					notAlreadyInList = false;
				}
				if(notAlreadyInList) {
					Position newP = new Position(wallRow, wallCol - 1);
					wallList.add(newP);
				}
			}
		}
		notAlreadyInList = true;
		//Checking if the position up from the cell is out of the maze's bounds
		if (wallRow + 1 <= maze.length - 1){
			if(maze[wallRow + 1][wallCol] == 1) {
				if (!isFirstWalls && isWallInList(wallList, new Position(wallRow + 1, wallCol))) {
					notAlreadyInList = false;
				}
				if (notAlreadyInList) {
					Position newP = new Position(wallRow + 1, wallCol);
					wallList.add(newP);
				}
			}
		}
		notAlreadyInList = true;
		//Checking if the position down from the cell is out of the maze's bounds
		if (wallRow - 1 >= 0){
			if(maze[wallRow - 1][wallCol] == 1) {
				if (!isFirstWalls && isWallInList(wallList, new Position(wallRow - 1, wallCol))) {
					notAlreadyInList = false;
				}
				if (notAlreadyInList) {
					Position newP = new Position(wallRow - 1, wallCol);
					wallList.add(newP);
				}
			}
		}
	}

	/**
	 * Finds a random wall out of the wall list and removes it from the list
	 * @param wallList - an ArrayList of Positions which are all walls
	 * @return - a Position which is a wall from the wall list
	 */
	private Position getRandomWall(ArrayList<Position> wallList) {
		Random random = new Random();
		int randomWallIndex = random.nextInt(wallList.size());
		return wallList.remove(randomWallIndex);
	}

	/**
	 * Checks whether at least 2 of the cells adjacent to the wall are not walls. If so, than the wall shall not be changed (return false)
	 * @param maze - a maze to check if a cell is a wall or not
	 * @param wall - the Position of the wall which its adjacent cells need to be checked
	 * @return - false if there are at least 2 cells adjacent to the wall which are not walls, true otherwise
	 */
	private boolean checkIfTheWallNeedsToChange(int[][] maze, Position wall) {
		int wallRow = wall.getRowIndex();
		int wallCol = wall.getColumnIndex();
		boolean condition = true;
		//Checking if the position to the right of the wall is out of the maze's bounds
		if(wallCol + 1 <= maze[0].length - 1) {
			int rightPosition = maze[wallRow][wallCol + 1];
			if (rightPosition == 0)
				condition = false;
		}
		//Checking if the position to the left of the wall is out of the maze's bounds
		if (wallCol - 1 >= 0) {
			int leftPosition = maze[wallRow][wallCol - 1];
			if (leftPosition == 0) {
				if (!condition)
					return false;
				else
					condition = false;
			}
		}
		//Checking if the position up from the wall is out of the maze's bounds
		if (wallRow - 1 >= 0){
			int upPosition = maze[wallRow - 1][wallCol];
			if (upPosition == 0) {
				if (!condition)
					return false;
				else
					condition = false;
			}
		}
		//Checking if the position down from the wall is out of the maze's bounds
		if (wallRow + 1 <= maze.length - 1){
			int downPosition = maze[wallRow + 1][wallCol];
			if (downPosition == 0) {
				return condition;
			}
		}
		return true;
	}

	/**
	 * Checks whether a given wall's Position is in the wall list
	 * @param wallList - the wall list
	 * @param wall - a given position of a wall
	 * @return - true if the wall's Position is in the list, false otherwise
	 */
	private boolean isWallInList(ArrayList<Position> wallList, Position wall) {
		for (Position p:wallList)
		{
			if(wall.equals(p))
				return true;
		}
		return false;
	}

	/**
	 * Finds and returns a random start position for a given maze
	 * @param maze - a given maze
	 * @return - a random start position for the maze
	 */
	private Position getRandomStartPosition(int[][] maze) {
		Position[] frameOfMaze = new Position[maze[0].length * 2 + (maze.length * 2) - 4];
		int counter = 0;
		for (int i = 0; i < maze[0].length; i++) {
			frameOfMaze[counter] = new Position(0,i);
			counter++;
			frameOfMaze[counter] = new Position(maze.length - 1,i);
			counter++;
		}
		for (int i = 1; i < maze.length - 1; i++) {
			frameOfMaze[counter] = new Position(i,0);
			counter++;
			frameOfMaze[counter] = new Position(i,maze[0].length - 1);
			counter++;
		}
		Random random = new Random();
		int randFramePosition = random.nextInt(frameOfMaze.length);
		return frameOfMaze[randFramePosition];
	}

	/**
	 * Finds and returns a random goal position for a given maze
	 * @param maze - a given maze
	 * @param startPosition - the start position of the maze, not to be repeated as the goal position
	 * @return - a random goal position for the maze
	 */
	private Position getRandomGoalPosition(int[][] maze, Position startPosition) {
		Position[] frameOfMaze = new Position[maze[0].length * 2 + (maze.length * 2) - 5];
		int counter = 0;
		Position p = null;
		int potentialGoals = 0;
		for (int i = 0; i < maze[0].length; i++) {
			p = new Position(0, i);
			if (!p.equals(startPosition) && maze[p.getRowIndex()][p.getColumnIndex()] == 0) {
				frameOfMaze[counter] = p;
				counter++;
				potentialGoals++;
			}
			p = new Position(maze.length - 1, i);
			if (!p.equals(startPosition) && maze[p.getRowIndex()][p.getColumnIndex()] == 0) {
				frameOfMaze[counter] = p;
				counter++;
				potentialGoals++;
			}
		}
		for (int i = 1; i < maze.length - 1; i++) {
			p = new Position(i,0);
			if(!p.equals(startPosition) && maze[p.getRowIndex()][p.getColumnIndex()] == 0) {
				frameOfMaze[counter] = p;
				counter++;
				potentialGoals++;
			}
			p = new Position(i,maze[0].length - 1);
			if(!p.equals(startPosition) && maze[p.getRowIndex()][p.getColumnIndex()] == 0) {
				frameOfMaze[counter] = p;
				counter++;
				potentialGoals++;
			}
		}
		Random random = new Random();
		int randomFramePosition = random.nextInt(potentialGoals);
		return frameOfMaze[randomFramePosition];
	}
}