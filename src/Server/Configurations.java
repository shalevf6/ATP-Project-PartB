package Server;

import algorithms.mazeGenerators.IMazeGenerator;
import algorithms.mazeGenerators.MyMazeGenerator;
import algorithms.mazeGenerators.SimpleMazeGenerator;
import algorithms.search.BestFirstSearch;
import algorithms.search.BreadthFirstSearch;
import algorithms.search.DepthFirstSearch;
import algorithms.search.ISearchingAlgorithm;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * A class that represents the configurations for the project
 */
public class Configurations {

    private static Properties properties = new Properties();

    /**
     * gets the necessary configuration file into the "properties" field
     */
    public static void start(){

        InputStream input = null;

        try {
            String file = "./Resources/config.properties";
            input = new FileInputStream(file);
            properties.load(input);

        } catch (IOException ex) {
            ex.printStackTrace();
        } finally{
            if(input!=null){
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * gets the configured searching algorithm (can be changed from Resources\config.properties)
     * @return - the configured searching algorithm. If there is no searching algorithm in the file, returns DFS
     */
    public static ISearchingAlgorithm getSearchingAlgorithm() {
        switch (properties.getProperty("searchingAlgorithm")){
            case "BFS":
                return new BreadthFirstSearch();
            case "BestFirstSearch":
                return new BestFirstSearch();
            case "DFS":
                return new DepthFirstSearch();
        }
        return new DepthFirstSearch();
    }

    /**
     * gets the configured maze generator (can be changed from Resources\config.properties)
     * @return - the configured maze generator. If there is no maze generator in the file, returns MyMazeGenerator
     */
    public static IMazeGenerator getMazeGenerator() {
        switch (properties.getProperty("mazeGenerator")){
            case "simpleMazeGenerator":
                return new SimpleMazeGenerator();
            case "myMazeGenerator":
                return new MyMazeGenerator();
        }
        return new MyMazeGenerator();
    }

    /**
     * gets the configured thread pool size (can be changed from Resources\config.properties)
     * @return - the configured thread pool size. If its not a number, returns a default of 4
     */
    public static int getThreadPoolSize() {
        try{
            boolean isNum = true;
            try {
                Double.parseDouble("threadPoolSize");
            }
            catch(NumberFormatException nfe)
            {
                isNum = false;
            }
            if (isNum) {
                int temp = Integer.parseInt(properties.getProperty("server_threadPoolSize"));
                if (temp > 0)
                    return temp;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return 4;
    }
}
