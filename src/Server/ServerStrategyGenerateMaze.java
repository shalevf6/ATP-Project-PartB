package Server;

import IO.MyCompressorOutputStream;
import algorithms.mazeGenerators.IMazeGenerator;
import algorithms.mazeGenerators.Maze;
import java.io.*;
import java.io.IOException;
import java.io.InputStream;

/**
 * this class in charge of generating maze by request.
 */
public class ServerStrategyGenerateMaze implements IServerStrategy{

    @Override
    public synchronized void applyStrategy(InputStream inputStream, OutputStream outputStream) {
        try {
            ObjectInputStream fromClient = new ObjectInputStream(inputStream);
            ObjectOutputStream toClient = new ObjectOutputStream(outputStream);

            ByteArrayOutputStream toClientBytes = new ByteArrayOutputStream();
            int[] mazeSize = (int[])fromClient.readObject();
            toClient.flush();
            IMazeGenerator mg = Configurations.getMazeGenerator();
            Maze maze = mg.generate(mazeSize[0], mazeSize[1]);
            MyCompressorOutputStream toClientCompressed = new MyCompressorOutputStream(toClientBytes);
            toClientCompressed.write(maze.toByteArray());
            toClient.writeObject(toClientBytes.toByteArray());
            toClient.flush();
        }
        catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
