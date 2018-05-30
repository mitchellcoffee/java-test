package morris.javatest.thread;



public class StopThreadUnsafe {

    public static User u = new User();

    public static class User {
        private int id;
        private String name;

        public User() {
            id = 0;
            name = "0";
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return "User{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    '}';
        }
    }

    public static class ChangeObjectThread extends Thread {

        protected void doSth() {
            synchronized (u) {
                int v = (int) (System.currentTimeMillis() / 1000);
                u.setId(v);
                // do sth. else
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                u.setName(String.valueOf(v));
            }
            Thread.yield();
        }

        @Override
        public void run() {
            while (true) {
                doSth();
            }
        }
    }

    public static class ChangeObjectThreadSafeWithStop extends ChangeObjectThread {
        volatile boolean stopMe = false;

        public void stopMe() {
            stopMe = true;
        }

        @Override
        public void run() {
            while (true) {
                if (stopMe) {
                    System.out.println("exit by stop me");
                    break;
                } else {
                    doSth();
                }
            }
        }
    }

    public static class ReadObjectThread extends Thread {
        @Override
        public void run() {
            while (true) {
                synchronized (u) {
                    if (u.getId() != Integer.parseInt(u.getName())) {
                        System.out.println(u.toString());
                    }
                }
                Thread.yield();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        new ReadObjectThread().start();
        while (true) {
            //Thread t = new ChangeObjectThread();
            ChangeObjectThreadSafeWithStop t = new ChangeObjectThreadSafeWithStop();
            t.start();
            Thread.sleep(150);
            t.stopMe();
        }
    }

}
