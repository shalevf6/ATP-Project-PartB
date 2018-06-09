package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * this class represent the server.
 */
public class Server {
    private int port;
    private int listeningInterval;
    private IServerStrategy serverStrategy;
    private volatile boolean stop;
    private ThreadPoolExecutor threadPoolExecutor;

    public Server(int port, int listeningInterval, IServerStrategy serverStrategy) {
        Configurations.start();
        threadPoolExecutor = (ThreadPoolExecutor) Executors.newCachedThreadPool();
        threadPoolExecutor.setCorePoolSize(Runtime.getRuntime().availableProcessors() * Configurations.getThreadPoolSize());
        this.port = port;
        this.listeningInterval = listeningInterval;
        this.serverStrategy = serverStrategy;
    }

    /**
     * starts the server.
     */
    public void start() {
        new Thread(() -> {
            runServer();
        }).start();
    }

    /**
     * in charge of running the server and wait for client requests
     */
    private void runServer() {
        try {
            ServerSocket server = new ServerSocket(port);
            server.setSoTimeout(listeningInterval);
            System.out.println(String.format("Server started! (port: %s)", port));
            while (!stop) {
                try {
                    Socket clientSocket = server.accept(); // blocking call
                    System.out.println(String.format("Client accepted: %s", clientSocket.toString()));
                    threadPoolExecutor.execute(()->handleClient(clientSocket));
                } catch (SocketTimeoutException e) {
                    System.out.println("SocketTimeout - No clients pending!");
                }
            }
            server.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * handle the request of the client and connecting the sockets of the input and output streams.
     * @param clientSocket - the socket of the client.
     */
    private void handleClient(Socket clientSocket) {
        try {
            System.out.println("Client accepted!");
            System.out.println(String.format("Handling client with socket: %s", clientSocket.toString()));
            serverStrategy.applyStrategy(clientSocket.getInputStream(), clientSocket.getOutputStream());
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * terminating the server.
     */
    public void stop() {
        System.out.println("Stopping server..");
        threadPoolExecutor.shutdown();
        stop = true;
    }
}