package Server;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * an interface of the strategies of the server.
 */
public interface IServerStrategy  {
    void applyStrategy(InputStream inputStream, OutputStream outputStream);
}
