package morris.javatest.nio;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class FileChannelExample {

    public static void ioMethod() {
        InputStream in = null;
        try {
            in = new BufferedInputStream(new FileInputStream("src/normal_io.txt"));
            byte[] buf = new byte[1024];
            int byteRead = in.read(buf);
            while (byteRead != -1) {
                for (int i = 0; i < byteRead; ++i) {
                    System.out.print((char)buf[i]);
                }
                byteRead = in.read(buf);
            }
            System.out.println("");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void nioMethod() {
        RandomAccessFile aFile = null;
        try {
            aFile = new RandomAccessFile("src/file_nio.txt", "rw");
            FileChannel fileChannel = aFile.getChannel();
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            int byteRead = fileChannel.read(buffer);
            while (byteRead != -1) {
                buffer.flip();
                while (buffer.hasRemaining()) {
                    System.out.print((char)buffer.get());
                }
                buffer.compact();
                byteRead = fileChannel.read(buffer);
            }
            System.out.println("");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (aFile != null) {
                    aFile.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public static void main(String[] args) {
        System.out.println("io: ");
        ioMethod();
        System.out.println("nio: ");
        nioMethod();
    }

}
