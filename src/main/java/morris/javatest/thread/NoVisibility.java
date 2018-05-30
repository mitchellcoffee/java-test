package morris.javatest.thread;

public class NoVisibility {

    private static boolean ready;
    private static int number;

    private static class ReaderThread extends Thread {
        @Override
        public void run() {
            System.out.println(System.currentTimeMillis());
            while (!ready) {
                System.out.println(number);
            }
            System.out.println(System.currentTimeMillis());
        }
    }

    public static void main(String[] args) throws InterruptedException {
        new ReaderThread().start();
        Thread.sleep(1000);
        number = 42;
        ready = true;
        Thread.sleep(10000);
    }

}
