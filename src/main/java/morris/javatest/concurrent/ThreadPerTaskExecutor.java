package morris.javatest.concurrent;

import java.util.concurrent.Executor;

public class ThreadPerTaskExecutor implements Executor {
    public void execute(Runnable command) {
        new Thread(command).run();
    }
}
