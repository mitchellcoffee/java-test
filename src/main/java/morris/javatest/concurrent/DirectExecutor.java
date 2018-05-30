package morris.javatest.concurrent;

import java.util.concurrent.Executor;

// 线程池同步执行每一个任务
public class DirectExecutor implements Executor {
    public void execute(Runnable command) {
        command.run();
    }
}
