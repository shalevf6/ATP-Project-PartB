package algorithms.search;

/**
 * An interface for searching algorithms
 */
public interface ISearchingAlgorithm {

    /**
     * solves the searching problem given in ISearchable
     * @param iSearchable - a searching problem
     * @return - a Solution to the searching problem
     */
    public Solution solve(ISearchable iSearchable);

    /**
     * gets the name of the searching algorithm used
     * @return - the name of the searching algorithm used
     */
    public String getName();

    /**
     * gets the number of nodes evaluated during the algorithm's run
     * @return - the number of nodes evaluated during the algorithm's run
     */
    public String getNumberOfNodesEvaluated();
}
