package algorithms.search;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * this class represents a solution of a problem
 */
public class Solution implements Serializable
{
	private ArrayList<AState> solutionPath;

	/**
	 * a constructor for Solution
	 * @param solutionPath - the solution's path
	 */
	public Solution(ArrayList<AState> solutionPath){
		this.solutionPath = solutionPath;
	}

	/**
	 * gets the Solution's path
	 * @return - the Solution's path
	 */
	public ArrayList<AState> getSolutionPath() {
		return solutionPath;
	}

	/*
	@Override
	public String toString() {
		String ans = "";
		for (int i = 0; i < solutionPath.size(); i++) {
			ans = ans + i + ". " + solutionPath.get(i).toString() + " ";
		}
		return ans;
	}
	*/
}