package algorithms.search;
import algorithms.mazeGenerators.Position;

import java.io.Serializable;

/**
 * this class represents a state in a maze
 */
public class MazeState extends AState implements Serializable
{
	private Position position;

    /**
     * a constructor for MazeState
     * @param p - a given position
     */
	MazeState(Position p){
		position = p;
	}

    /**
     * gets a deep copt of the MazeState's position
     * @return - the MazeState's position
     */
	public Position getPosition() {
		return new Position(position.getRowIndex(),position.getColumnIndex());
	}

	@Override
	public boolean equals(Object obj) {
		if(obj instanceof MazeState) {
			MazeState mazeState = (MazeState)obj;
			Position sPosition = mazeState.getPosition();
			return (sPosition.getRowIndex() == position.getRowIndex() && sPosition.getColumnIndex() == position.getColumnIndex());
		}
		return false;
	}

	@Override
	public int hashCode() {
		return position.hashCode();
	}

	@Override
	public String toString() {
		return position.toString();
	}
}

