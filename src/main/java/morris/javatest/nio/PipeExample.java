package morris.javatest.nio;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.Pipe;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class PipeExample {

    // 数据写到sink通道
    // 从source通道读取
    public static void pipeExample() {
        Pipe pipe = null;
        ExecutorService exec = Executors.newFixedThreadPool(2);
        try {
            pipe = Pipe.open();
            final Pipe pipeTemp = pipe;
            exec.submit(new Callable<Object>() {
                @Override
                public Object call() throws Exception {
                    Pipe.SinkChannel sinkChannel = pipeTemp.sink();
                    while (true) {
                        TimeUnit.SECONDS.sleep(1);
                        String newData = "Pipe Test At Time " + System.currentTimeMillis();
                        ByteBuffer buffer = ByteBuffer.allocate(1024);
                        buffer.clear();
                        buffer.put(newData.getBytes());
                        buffer.flip();
                        while (buffer.hasRemaining()) {
                            System.out.println(buffer);
                            sinkChannel.write(buffer);
                        }
                    }
                }
            });
            exec.submit(new Callable<Object>() {
                @Override
                public Object call() throws Exception {
                    Pipe.SourceChannel sourceChannel = pipeTemp.source();
                    while (true) {
                        TimeUnit.SECONDS.sleep(1);
                        ByteBuffer buffer = ByteBuffer.allocate(1024);
                        buffer.clear();
                        int bytesRead = sourceChannel.read(buffer);
                        System.out.println("bytesRead=" + bytesRead);
                        while (bytesRead > 0) {
                            buffer.flip();
                            byte[] b = new byte[bytesRead];
                            int i = 0;
                            while (buffer.hasRemaining()) {
                                b[i] = buffer.get();
                                System.out.printf("%X", b[i]);
                                i++;
                            }
                            System.out.println();
                            String s = new String(b);
                            System.out.println("==============||" + s);
                            bytesRead = sourceChannel.read(buffer);
                        }
                    }
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            exec.shutdown();
        }
    }

    public static void main(String[] args) {
        pipeExample();
    }

}
