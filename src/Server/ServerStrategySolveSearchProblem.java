package Server;

import algorithms.mazeGenerators.Maze;
import algorithms.search.*;
import java.io.*;
import java.util.Arrays;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * this class in charge of solving a given maze by request.
 */
public class ServerStrategySolveSearchProblem implements IServerStrategy {

    @Override
    public synchronized void applyStrategy(InputStream inputStream, OutputStream outputStream) {
        try {
            String tempDirectoryPath = System.getProperty("java.io.tmpdir"); // the temp file's folder
            ObjectInputStream fromClient = new ObjectInputStream(inputStream); // to read from the client
            ObjectOutputStream toClient = new ObjectOutputStream(outputStream); // to write back to the client
            Solution solution;
            Maze maze = (Maze) fromClient.readObject();
            int hMaze = Arrays.hashCode(maze.toByteArray());
            File tempFile = new File(tempDirectoryPath, hMaze + ".ser");
            boolean fileExisted = tempFile.exists();
            // gets the relevant solution
            if (fileExisted) {
                ObjectInputStream readFromTempFile = new ObjectInputStream(new FileInputStream(tempFile)); // to read from the temp file
                solution = (Solution) readFromTempFile.readObject();
            } else {
                ObjectOutputStream writeToTempFile = new ObjectOutputStream(new FileOutputStream(tempFile)); // to write to the temp file
                ISearchable searchableMaze = new SearchableMaze(maze);
                ISearchingAlgorithm searcher = Configurations.getSearchingAlgorithm();
                System.out.println(searcher.getName());
                solution = searcher.solve(searchableMaze);
                writeToTempFile.writeObject(solution);
                writeToTempFile.flush();
            }
            toClient.writeObject(solution);
            toClient.flush();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}