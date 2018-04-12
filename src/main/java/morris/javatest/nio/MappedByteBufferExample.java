package morris.javatest.nio;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

public class MappedByteBufferExample {

    public static void byteBufferRead() {
        RandomAccessFile aFile = null;
        FileChannel fc = null;

        try {
            aFile = new RandomAccessFile("src/1.ppt", "rw");
            fc = aFile.getChannel();
            long timeBegin = System.currentTimeMillis();
            ByteBuffer buffer = ByteBuffer.allocate((int)aFile.length());
            buffer.clear();
            fc.read(buffer);
            long timeEnd = System.currentTimeMillis();
            System.out.println("ReadTime: " + (timeEnd - timeBegin) + "ms");
        } catch (java.io.IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (aFile != null) {
                    aFile.close();
                }
                if (fc != null) {
                    fc.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void mappedByteBufferRead() {
        RandomAccessFile aFile = null;
        FileChannel fc = null;

        try {
            aFile = new RandomAccessFile("src/1.ppt", "rw");
            fc = aFile.getChannel();
            long timeBegin = System.currentTimeMillis();
            MappedByteBuffer mbb = fc.map(FileChannel.MapMode.READ_ONLY, 0, aFile.length());
            long timeEnd = System.currentTimeMillis();
            System.out.println("ReadTime: " + (timeEnd - timeBegin) + "ms");
        } catch (java.io.IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (aFile != null) {
                    aFile.close();
                }
                if (fc != null) {
                    fc.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        mappedByteBufferRead();
        System.out.println("================");
        byteBufferRead();
    }

}
