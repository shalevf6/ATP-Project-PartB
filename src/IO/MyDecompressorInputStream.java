package IO;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;


/**
 * in charge of decompressing the compressed maze into a decompressed array of bytes.
 */

public class MyDecompressorInputStream extends InputStream {
    private InputStream in;

    public MyDecompressorInputStream(InputStream in)
    {
        this.in = in;
    }

    @Override
    public int read() throws IOException
    {
        return 0;
    }

    @Override
    public int read(byte[] b) throws IOException {
        byte[] arrCompressedB = new byte[b.length];
        in.read(arrCompressedB);
        ArrayList<Byte> compressedB = new ArrayList<>();
        for (byte element: arrCompressedB) {
            compressedB.add(element);
        }
        int size = b.length;
        int indexIn = 0;
        int indexCom = 0;
        int initialDataCount = 6;
        byte numOfCells;
        // goes through all the initial data, before the maze itself (its size and the start and goal positions)
        //and copying it to the given array.
        while (initialDataCount > 0)
        {
            numOfCells = compressedB.get(indexCom);
            b[indexIn] = numOfCells;
            indexCom++;
            indexIn++;
            while (numOfCells > 0)
            {
                b[indexIn] = compressedB.get(indexCom);
                indexCom++;
                indexIn++;
                numOfCells--;
            }
            initialDataCount--;
        }
        // start to decompress the maze itself from the compressed array
        byte currByteToWrite = 0;  //we start the counting as agreed that the first cell is for 0.
        while (indexCom < compressedB.size() && indexIn < size)
        {
            numOfCells = compressedB.get(indexCom);
            while (numOfCells > 0)
            {
                b[indexIn] = currByteToWrite;
                numOfCells--;
                indexIn++;
            }
            if (currByteToWrite == 0)
                currByteToWrite = 1;
            else
                currByteToWrite = 0;
            indexCom++;
        }

        // if the length of the given array was too big
        if(indexIn < size) {
            int newSize = b.length - (size - indexIn);
            byte[] newB = new byte[newSize];
            indexIn = 0;
            while (indexIn < newSize) {
                newB[indexIn] = b[indexIn];
                indexIn++;
            }
            b = newB;
        }
        return 0;
    }

    public InputStream getIn()
    {
        return in;
    }
}
