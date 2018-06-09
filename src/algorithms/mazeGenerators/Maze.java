package algorithms.mazeGenerators;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * this class represents a Maze with start and end positions.
 */

public class Maze implements Serializable {
    private int[][] m_maze;
    private Position m_startP;
    private Position m_endP;

    /**
     * constructor of the class.
     *
     * @param Maze   - the given maze
     * @param startP - the starting position in the maze.
     * @param endP   - the ending position in the maze.
     */
    public Maze(int[][] Maze, Position startP, Position endP) {
        m_maze = Maze;
        m_startP = startP;
        m_endP = endP;
    }

    /**
     * constroctor of the class
     *
     * @param byte_array is a compressed byte array that from him we will build the maze
     */
    public Maze(byte[] byte_array) {
        int index = (int)byte_array[0] + 2;
        m_startP = new Position(get_the_int_val(byte_array, 1, (int) byte_array[0]), get_the_int_val(byte_array, index, byte_array[index - 1] + index - 1));
        if (byte_array[index - 1] < 0)
            index = index + (byte_array[index - 1] & 0xFF) + 1;
        else
            index = index + (int) byte_array[index - 1] + 1;
        int row_num = get_the_int_val(byte_array, index, byte_array[index - 1] + index - 1);
        if (byte_array[index - 1] < 0)
            index = index + (byte_array[index - 1] & 0xFF) + 1;
        else
            index = index + (int) byte_array[index - 1] + 1;
        int col_num = get_the_int_val(byte_array, index, byte_array[index - 1] + index - 1);
        m_endP = new Position(row_num, col_num);
        if (byte_array[index - 1] < 0)
            index = index + (byte_array[index - 1] & 0xFF) + 1;
        else
            index = index + (int) byte_array[index - 1] + 1;
        int rows = get_the_int_val(byte_array, index, (int) byte_array[index - 1] + index - 1);
        if (byte_array[index - 1] < 0)
            index = index + (byte_array[index - 1] & 0xFF) + 1;
        else
            index = index + (int) byte_array[index - 1] + 1;
        int cols = get_the_int_val(byte_array, index, (int) byte_array[index - 1] + index - 1);
        if (byte_array[index - 1] < 0)
            index = index + (byte_array[index - 1] & 0xFF);
        else
            index = index + (int) byte_array[index - 1];
        m_maze = new int[rows][cols];
        for (int row = 0; row < m_maze.length; row++)
            for (int col = 0; col < m_maze[0].length; col++) {
                m_maze[row][col] = (int) byte_array[index];
                index++;
            }
    }

    /**
     * @return - return the start position of the maze.
     */
    public Position getStartPosition() {
        return m_startP;
    }

    /**
     * @return - return the end position of the maze.
     */
    public Position getGoalPosition() {
        return m_endP;
    }

    /**
     * prints the whole maze row by row.
     */
    public void print() {
        for (int i = 0; i < m_maze.length; i++) {
            for (int j = 0; j < m_maze[0].length; j++)
                if (m_startP.getRowIndex() == i && m_startP.getColumnIndex() == j)
                    System.out.print("S ");
                else if (m_endP.getRowIndex() == i && m_endP.getColumnIndex() == j)
                    System.out.print("G ");
                else
                    System.out.print(m_maze[i][j] + " ");
            System.out.println();
        }
    }

    /**
     * this help function returns the value of the byte array as a int type
     *
     * @param array       the byte array that we take the information from
     * @param start_index the start index that we are going to take the info from
     * @param end_index   the end index of the information
     * @return the information given in the range in int type
     */
    private int get_the_int_val(byte[] array, int start_index, int end_index) {
        int ans = 0;
        for (int i = start_index; i <= end_index; i++) {
            byte b = array[i];
            if (b < 0)
                ans = ans + (b & 0xFF);
            else
                ans = ans + (int) array[i];
        }
        return ans;
    }

    public ArrayList<Position> getReachableNeighbors(Position p) {
        if (isPositionInMaze(p)) {
            ArrayList<Position> neighbors = new ArrayList<>();
            int currRow = p.getRowIndex();
            int currCol = p.getColumnIndex();
            Position leftN = new Position(currRow, currCol - 1);
            Position rightN = new Position(currRow, currCol + 1);
            Position downerN = new Position(currRow + 1, currCol);
            Position upperN = new Position(currRow - 1, currCol);
            // Check the neighbor to the left
            if (isPositionInMaze(leftN) && !isPositionAWall(leftN))
                neighbors.add(leftN);
            // Check the neighbor to the right
            if (isPositionInMaze(rightN) && !isPositionAWall(rightN))
                neighbors.add(rightN);
            // Check the neighbor below
            if (isPositionInMaze(downerN) && !isPositionAWall(downerN))
                neighbors.add(downerN);
            // Check the neighbor above
            if (isPositionInMaze(upperN) && !isPositionAWall(upperN))
                neighbors.add(upperN);
            return neighbors;
        } else
            return null;
    }

    /**
     * this help function inset the smaller byte arrat to the bigger one
     *
     * @param to_insert   is the byte array that we want to insert to
     * @param array       is the small array
     * @param start_index the starting index of the insertion
     */
    private void insert_byte(byte[] array, byte[] to_insert, int start_index, int real_val) {
        to_insert[start_index] = (byte) int_to_byte_count(real_val);
        for (int i = 1; i < array.length + 1; i++) {
            to_insert[start_index + i] = array[i - 1];
        }
    }

    /**
     * Turns the Maze instance into a byte array
     * @return - a byte array that represents the Maze instance
     */
    public byte[] toByteArray() {
        int length = 6 + int_to_byte_count(m_maze[0].length) + int_to_byte_count(m_maze.length) + (m_maze[0].length * m_maze.length)
                + int_to_byte_count(m_startP.getRowIndex()) + int_to_byte_count(m_startP.getColumnIndex()) + int_to_byte_count(m_endP.getRowIndex())
                + int_to_byte_count(m_endP.getColumnIndex());
        int byte_array_index = 0;
        byte[] Byte_Array = new byte[length];
        //insert the Start position
        byte[] temp_byte = int_to_byte(m_startP.getRowIndex());//insert row_position
        insert_byte(temp_byte, Byte_Array, byte_array_index, m_startP.getRowIndex());
        byte_array_index = byte_array_index + temp_byte.length + 1;
        byte[] temp_byte1 = int_to_byte(m_startP.getColumnIndex());//insert col_position
        insert_byte(temp_byte1, Byte_Array, byte_array_index, m_startP.getColumnIndex());
        byte_array_index = byte_array_index + temp_byte1.length + 1;
        //insert the End_position
        byte[] temp_byte2 = int_to_byte(m_endP.getRowIndex());//insert row_position
        insert_byte(temp_byte2, Byte_Array, byte_array_index, m_endP.getRowIndex());
        byte_array_index = byte_array_index + temp_byte2.length + 1;
        byte[] temp_byte3 = int_to_byte(m_endP.getColumnIndex());//insert col_position
        insert_byte(temp_byte3, Byte_Array, byte_array_index, m_endP.getColumnIndex());
        byte_array_index = byte_array_index + temp_byte3.length + 1;
        //insert maze num of rows
        byte[] temp_byte_row_length = int_to_byte(m_maze.length);
        insert_byte(temp_byte_row_length, Byte_Array, byte_array_index, m_maze.length);
        byte_array_index = byte_array_index + temp_byte_row_length.length + 1;
        //insert the maze num of col
        byte[] temp_byte_col_length = int_to_byte(m_maze[0].length);
        insert_byte(temp_byte_col_length, Byte_Array, byte_array_index, m_maze[0].length);
        byte_array_index = byte_array_index + temp_byte_col_length.length + 1;
        //insert the maze himself
        for (int row = 0; row < m_maze.length; row++)
            for (int col = 0; col < m_maze[0].length; col++) {
                Byte_Array[byte_array_index] = (byte) m_maze[row][col];
                byte_array_index++;
            }
        return Byte_Array;
    }

    /**
     * this help function returns an array of bytes after representing one int number in a byte array.
     *
     * @param num the int number that we want to represent in byte array
     * @return the int number in a byte array
     */
    private byte[] int_to_byte(int num) {
        byte[] array;
        int length = 0;
        if (num <= 255) {
            array = new byte[1];
            array[0] = (byte) num;
            return array;
        } else {
            boolean equalsZero = false;
            while (num > 0) {
                length++;
                num = num - 255;
                if (num == 0)
                    equalsZero = true;
            }
            if (!equalsZero) {
                array = new byte[length];
                for (int i = 0; i < length - 1; i++) {
                    array[i] = (byte) 255;
                }
                array[length - 1] = (byte) (num + 255);
                return array;
            } else {
                array = new byte[length];
                for (int i = 0; i < length; i++) {
                    array[i] = (byte) 255;
                }
                return array;
            }
        }
    }


    /**
     * this function returns the number of numbers that we need to represent int number in byte formation
     *
     * @param num the int number that we need to change
     * @return the number of bytes we need to represent the number given
     */
    private int int_to_byte_count(int num) {
        int Return_val = 0;
        if (num < 0)
            return -1;
        if (num <= 255)
            return 1;
        else {
            while (num > 0) {
                Return_val++;
                num = num - 255;
            }
            return Return_val;
        }
    }

    /**
     * checks whether a given position is a wall or not
     * @param currPo - a given position
     * @return - true if the given position is a wall, else - false
     */
    public boolean isPositionAWall(Position currPo) {
        return (m_maze[currPo.getRowIndex()][currPo.getColumnIndex()] == 1);
    }

    /**
     * check whether a given position is in the maze
     * @param nextPo - a given position
     * @return - true if the given position is in the maze, else - false
     */
    public boolean isPositionInMaze(Position nextPo) {
        if (nextPo.getRowIndex() >= 0 && nextPo.getRowIndex() <= m_maze.length - 1)
            return nextPo.getColumnIndex() >= 0 && nextPo.getColumnIndex() <= m_maze[0].length - 1;
        return false;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Maze) {
            Maze m = (Maze) obj;
            if (m.m_maze.length == m_maze.length && m.m_maze[0].length == m_maze[0].length && m_startP.getRowIndex() == m.m_startP.getRowIndex()
                    && m_startP.getColumnIndex() == m.m_startP.getColumnIndex() && m_endP.getRowIndex() == m.m_endP.getRowIndex()
                    && m_endP.getColumnIndex() == m.m_endP.getColumnIndex())
                for (int i = 0; i < m_maze.length; i++)
                    for (int j = 0; j < m_maze[0].length; j++)
                        if (m.m_maze[i][j] != m_maze[i][j])
                            return false;
            return true;
        }
        return false;
    }

    @Override
    public int hashCode() {
        int result = 23;
        int c = 0;
        c = c + m_startP.hashCode() + m_endP.hashCode();
        for (int[] aM_maze : m_maze)
            for (int j = 0; j < m_maze[0].length; j++)
                c = c + aM_maze[j];
        return 37 * result + c;
    }

    @Override
    public String toString() {
        String ans = "";
        for (int i = 0; i < m_maze.length; i++) {
            for (int j = 0; j < m_maze[0].length; j++)
                if (m_startP.getRowIndex() == i && m_startP.getColumnIndex() == j)
                    ans = ans + "S ";
                else if (m_endP.getRowIndex() == i && m_endP.getColumnIndex() == j) {
                    ans = ans + "G ";
                }
                else
                    ans = ans + m_maze[i][j] + " ";
        }
        return ans;
    }
}