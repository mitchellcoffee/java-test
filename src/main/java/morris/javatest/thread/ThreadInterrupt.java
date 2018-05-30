package morris.javatest.thread;

public class ThreadInterrupt {

    public static void noEffect() throws InterruptedException {
        Thread t = new Thread() {
            @Override
            public void run() {
                while (true) {
                    Thread.yield();
                }
            }
        };
        t.start();
        Thread.sleep(2000);
        t.interrupt();
    }

    public static void conditionStop() throws InterruptedException {
        Thread t = new Thread() {
            @Override
            public void run() {
                while (true) {
                    if (Thread.currentThread().isInterrupted()) {
                        System.out.println("Interrupted!");
                        break;
                    }
                    Thread.yield();
                }
            }
        };
        t.start();
        Thread.sleep(2000);
        t.interrupt();
    }

    public static void sleepInterrupt() throws InterruptedException {
        Thread t = new Thread() {
            @Override
            public void run() {
                while (true) {
                    if (Thread.currentThread().isInterrupted()) {
                        System.out.println("Interrupted!");
                        break;
                    }
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        System.out.println("Interrupted When Sleep");
                        Thread.currentThread().interrupt();
                    }
                    Thread.yield();
                }
            }
        };
        t.start();
        Thread.sleep(2000);
        t.interrupt();

    }
}
