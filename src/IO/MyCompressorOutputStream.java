package IO;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;


/**
 * in charge of compressing the maze into an array of bytes.
 */
public class MyCompressorOutputStream extends OutputStream {

    private OutputStream out;

    public MyCompressorOutputStream(OutputStream out) {
        this.out = out;
    }

    @Override
    public void write(int b) throws IOException {
    }

    @Override
    public void write(byte[] byteArray) throws IOException {
        ArrayList<Byte> bytes = new ArrayList<Byte>();
        int size = byteArray.length;
        int counter = 0;
        int initialDataCount = 6;
        byte numOfAppearances = 0;
        byte lastByte = 0;
        int internalDataCount;
        byte b;
        // goes through all the initial data, before the maze itself (its size and the start and goal positions)
        while (counter < size && initialDataCount > 0) {
            b = byteArray[counter];
            internalDataCount = b;
            bytes.add(b);
            counter++;
            while (counter < size && internalDataCount > 0) {
                b = byteArray[counter];
                bytes.add(b);
                internalDataCount--;
                counter++;
            }
            initialDataCount--;
        }
        // inserts the first cell of the maze and initializes the lastByte variable
        if (counter < size) {
            lastByte = byteArray[counter];
            if (lastByte == 0)
                numOfAppearances++;
            else {
                bytes.add(numOfAppearances);
                lastByte = 1;
                numOfAppearances++;
            }
            counter++;
        }
        boolean biggerThanCapacity = false;
        // goes through all the maze and writes the number of appearances of 1's and 0's
        while (counter < size) {
            b = byteArray[counter];
            if (b == lastByte) {
                numOfAppearances++;
                if (biggerThanCapacity){
                    biggerThanCapacity = false;
                    bytes.add((byte) 0);
                }
                if (numOfAppearances == (byte) 255 ) {
                    bytes.add(numOfAppearances);
                    numOfAppearances = 0;
                    biggerThanCapacity = true;
                }
            }
            else {
                lastByte = b;
                if (biggerThanCapacity)
                    biggerThanCapacity = false;
                else
                    bytes.add(numOfAppearances);
                numOfAppearances = 1;
            }
            counter++;
        }
        bytes.add(numOfAppearances);
        int length = bytes.size();
        byte[] compressed = new byte[length];
        int compressedCount = 0;
        // copies all the data to a compressed byte array
        while (length > 0) {
            compressed[compressedCount] = bytes.remove(0);
            compressedCount++;
            length--;
        }
        out.write(compressed);
        out.flush();
        out.close();
    }

    public OutputStream getOut() {
        return out;
    }
}
