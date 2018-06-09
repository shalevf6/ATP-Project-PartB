package algorithms.search;

import java.io.Serializable;

/**
 * An abstract class that represents a state
 */
public abstract class AState implements Serializable
{
	private double cost = 0;
	private AState cameFrom = null;

	public AState(){
		super();
	}

	/**
	 * gets the cost of the state
	 * @return - the cost of the state
	 */
	public double getCost() {
		return cost;
	}

	/**
	 * sets the cost of the state
	 * @param cost - the cost of the state
	 */
	public void setCost(double cost) {
		this.cost = cost;
	}

	/**
	 * gets the predecessor state of the current state
	 * @return - the predecessor state of the current state
	 */
	public AState getCameFrom() {
		return cameFrom;
	}

	/**
	 * sets the predecessor state of the current state
	 * @param cameFrom - the predecessor state of the current state
	 */
	public void setCameFrom(AState cameFrom) {
		this.cameFrom = cameFrom;
	}

	@Override
	public abstract boolean equals(Object obj);

	@Override
	public abstract int hashCode();

	@Override
	public abstract String toString();
}