package utils.wzutils.common.thread;

import android.support.annotation.NonNull;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import utils.wzutils.common.LogTool;

/**
 * Created by coder on 16/2/24.
 */
public class ThreadTool {
    private static ExecutorService executorInOneThread = Executors.newFixedThreadPool(1);
    private static ExecutorService executorPriority = newPriorityThreadExecutor();

    public static void execute(Runnable runnable) {
       execute(runnable,0);
    }
    public static void executeInOneThread(Runnable runnable) {
        try {
            executorInOneThread.execute(runnable);
        } catch (Exception e) {
            LogTool.ex(e);
        }
    }
    /***
     *
     * @param runnable
     * @param priority  优先级 ， 越大越先执行
     */
    public static void execute(final Runnable runnable, int priority) {
        try {
            executorPriority.execute(new PriorityRunable(priority) {
                @Override
                public void run() {
                    System.out.println(priority);
                    runnable.run();
                }
            });
        } catch (Exception e) {
            LogTool.ex(e);
        }
    }

    /***
     * 创建 一个  栈线程
     *
     * @return
     */
    public static ThreadPoolExecutor newStackThreadExecutor() {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(1, 1, 0, TimeUnit.MILLISECONDS, new LinkedBlockingDeque<Runnable>() {
            @Override
            public boolean offer(Runnable runnable) {
                return super.offerFirst(runnable);
            }
        });
        return executor;
    }

    /***
     * 有优先级的线程池
     * @return
     */
    public static ThreadPoolExecutor newPriorityThreadExecutor() {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(5, 10, 5, TimeUnit.SECONDS, new PriorityBlockingQueue<Runnable>());
        return executor;
    }


    public static abstract class PriorityRunable implements Runnable, Comparable<PriorityRunable> {
        public int priority;

        public PriorityRunable(int priority) {
            this.priority = priority;
        }

        @Override
        public int compareTo(@NonNull PriorityRunable o) {
            if (this.priority < o.priority) {
                return 1;
            }
            if (this.priority > o.priority) {
                return -1;
            }
            return 0;
        }
    }
}
