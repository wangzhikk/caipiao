package utils.wzutils.common.thread;


import java.util.List;

import utils.wzutils.common.LogTool;
import utils.wzutils.common.MapListTool;


/**
 * 用于统一的线程管理
 *
 * @author Administrator
 *         使用方式，
 *         <p>
 *         ThreadContro.init(command.group);
 *         while (ThreadContro.check()&&(count = is.read(bs)) != -1) {
 *         <p>
 *         <p>
 *         这样就可以再其他地方操作这个了
 */
public class ThreadContro {
    static MapListTool<Thread> oneToMoreData = new MapListTool<Thread>();
    static int key = 10000;
    private static String s_stop = "stop";
    private static String s_pause = "pause";//线程需要暂停
    private static String s_resume = "resume";
    private static String s_curr_inpause = "s_curr_inpause";//当前线程正在暂停状态中，可以调用 interrupt 来恢复

    /*******
     * 可以通过组和tag(对应一个线程) 控制线程的状态
     ***/
    public static void init(String group) {
        Thread.currentThread().setName("thread: " + (key++));
        oneToMoreData.putOne(group, Thread.currentThread());
    }

    /**********
     * 在while 里面调用这个方法检测线程状态
     ***/
    public static boolean check() {
        Thread thread = Thread.currentThread();
        if (thread != null && thread.getName().equals(s_stop)) {
            throw new RuntimeException("ThreadContro.check()发现线程需要停止");
        } else if (thread != null && thread.getName().equals(s_pause))//暂停
        {
            try {
                thread.setName(s_curr_inpause);
                LogTool.s("线程暂停了");
                Thread.sleep(999999999);
            } catch (Exception e) {
                LogTool.s("线程恢复了");
            }
        }
        return true;
    }

    public static void stop(Object group) {
        List<Thread> threads = oneToMoreData.getList(group);
        if (threads != null) {
            for (Thread thread : threads) {
                stopThread(thread);
            }
        }
    }

    public static void pause(Object group) {
        List<Thread> threads = oneToMoreData.getList(group);
        LogTool.s("threads_pause:" + threads);
        if (threads != null) {
            for (Thread thread : threads) {
                pauseThread(thread);
            }
        }
    }

    public static void resume(Object group) {
        List<Thread> threads = oneToMoreData.getList(group);
        if (threads != null) {
            for (Thread thread : threads) {
                resumeThread(thread);
            }
        }
    }

    public static void resumeAll() {
        resume(MapListTool.allGroup);
    }

    public static void pauseAll() {
        pause(MapListTool.allGroup);
    }

    public static void stopAll() {
        stop(MapListTool.allGroup);
    }

    /*****
     * 停止一个线程
     ***/
    public static void stopThread(Thread thread) {
        thread.setName(s_stop);
    }

    /*****
     * 暂停一个线程
     ***/
    public static void pauseThread(Thread thread) {
        if (!thread.getName().equals(s_curr_inpause))
            thread.setName(s_pause);
    }

    /*****
     * 恢复一个线程
     ***/
    public static void resumeThread(Thread thread) {
        if (thread.getName().equals(s_curr_inpause))
            thread.interrupt();
        thread.setName(s_resume);
    }
}
