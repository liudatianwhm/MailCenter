package utils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * @Auther: xiaofeixia
 * @Date: 2018/11/16 10:50
 * TODO 邮件请求处理线程池
 * @Description:
 */
public class TransferPool {
    //线程池大小
    private int size;
    //默认线程池大小
    private final static int Default_SIZE = 10;
    //堵塞任务队列
    private final static LinkedBlockingDeque<Runnable> blookingQue = new LinkedBlockingDeque<>();
    private final static String THREAD_PREFIX = "TRANSGER_THREAD_POOL";
    private static volatile int seq = 0;
    private static  final List<Thread> TASK_EXECUTORS = new ArrayList<>();
    private static ThreadGroup threadGroup = new ThreadGroup("TRANSGER_THREAD_GROUP");

    public TransferPool() {
        this(Default_SIZE);
    }
    public TransferPool(int size){
        this.size = size;
        init();
    }
    //任务状态
    enum TaskStaus{
        FREE, RUNNING, BLOCKED, DEAD
    }
    private void init() {
        for (int i = 0; i < size; i++) {
            TaskExecutor taskExecutor = createExecutor();
            TASK_EXECUTORS.add(taskExecutor);
            taskExecutor.start();
        }

    }

    public static boolean addTask(Runnable runnable,boolean flag) {
        synchronized (blookingQue) {
            blookingQue.addLast(runnable);
            blookingQue.notifyAll();
        }
        return flag;
    }

    private TaskExecutor createExecutor() {
        TaskExecutor executor = new TaskExecutor(threadGroup, THREAD_PREFIX + (seq++));
        return executor;
    }
    private class TaskExecutor extends Thread{
        private TaskStaus taskStaus = TaskStaus.FREE;
        private TaskExecutor(ThreadGroup threadGroup,String name){
               super(threadGroup,name);
        }

        @Override
        public void run() {
            OUTER:
            while (taskStaus != taskStaus.DEAD) {
                Runnable runnable;
                synchronized (blookingQue) {
                    while (blookingQue.isEmpty()) {
                        try {
                            taskStaus = taskStaus.BLOCKED;
                            blookingQue.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                            break OUTER;
                        }
                    }
                    runnable = blookingQue.removeFirst();
                }
                taskStaus = taskStaus.RUNNING;
                runnable.run();
                taskStaus = taskStaus.FREE;

            }

        }

        public void shutdown() {
            taskStaus = taskStaus.DEAD;
        }
    }
    public boolean isTeminated(){
        for(int i =0;i<size;i++){
            if(TASK_EXECUTORS.get(i).isAlive()){
                return false;
            }
        }
        return true;
    }
}
