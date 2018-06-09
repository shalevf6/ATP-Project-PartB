import algorithms.mazeGenerators.MyMazeGenerator;
import algorithms.search.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JUnitTestBestFirstSearch {

    private BFSandBestFS bestFS = new BestFirstSearch();
    private ISearchable sMaze = new SearchableMaze((new MyMazeGenerator()).generate(-1,30));

    @Test
    void getName() {
        assertEquals("BestFirstSearch",bestFS.getName());
    }

    @Test
    void nullInput() {
        Solution solution = bestFS.solve(null);
        assertEquals(null,solution);
    }

    @Test
    void wrongMazeInput() {
        Solution solution = bestFS.solve(sMaze);
        boolean result = true;
        if (solution == null)
            result = false;
        assertTrue(result);
    }
}