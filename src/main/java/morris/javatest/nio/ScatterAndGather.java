package morris.javatest.nio;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class ScatterAndGather {

    public static void gather() {
        ByteBuffer header = ByteBuffer.allocate(10);
        ByteBuffer body = ByteBuffer.allocate(10);

        byte[] b1 = {'0', '1'};
        byte[] b2 = {'2', '3'};

        header.put(b1);
        body.put(b2);
        ByteBuffer[] buffs = {header, body};

        try {
            FileOutputStream os = new FileOutputStream("src/scatter&gather.txt");
            FileChannel channel = os.getChannel();
            channel.write(buffs);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        gather();
    }

}
