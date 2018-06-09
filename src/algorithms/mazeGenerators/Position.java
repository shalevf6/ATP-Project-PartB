package algorithms.mazeGenerators;

import java.io.Serializable;

/**
 * This class represents a position in a maze
 * row - the row number in the maze, starting from 0
 * column - the column number in the maze, starting from 0
 */

public class Position implements Serializable
{
	private int row;
	private int column;

	/**
	 * A constructor for the Position
	 * @param rowNum - the row number of the position
	 * @param colNum - the column number of the position
	 */
	public Position(int rowNum, int colNum){
		row = rowNum;
		column = colNum;
	}

	/**
	 * Gets the row number of the position
	 */
	public int getRowIndex() {
		return row;
	}

	/**
	 * Gets the column number of the position
	 */
	public int getColumnIndex() {
		return column;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Position){
			Position p = (Position)obj;
			return (p.getRowIndex() == row && p.getColumnIndex() == column);
		}
		return false;
	}

	@Override
	public String toString() {
		return ("{" + row + "," + column + "}");
	}

	@Override
	public int hashCode(){
		return 1117 * row + column;
	}
}

