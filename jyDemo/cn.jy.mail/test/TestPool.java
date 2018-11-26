package test;

import utils.ExecutorsUtil;
import utils.MailManagerJava;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;

/**
 * @Auther: 69013
 * @Date: 2018/11/16 11:25
 * @Description:
 */
public class TestPool {
//    public static void test(String word){
//        System.out.println(word);
//    }
    public static void main(String[] args) {
////        Mail mail = new Mail("690139592@qq.com", "你好，这是一封测试邮件，无需回复。", "测试文件邮件");
//        MailManager mailManager = new MailManager("13210277555@163.com", "你好，这是一封测试邮件，无需回复。", "测试文件邮件");

        //自测线程池
//        MailManager mailManager = new MailManager("690139592@qq.com", "你好，这是一封测试邮件，无需回复。", "测试文件邮件");
//        TestPerformance testPerformance = new TestPerformance();
//        TransferPool transferPool = new TransferPool(10);
//        long begin = System.currentTimeMillis();
//        for(int i=0;i<1;i++){
//            transferPool.addTask(mailManager,true);
//        }


//        System.out.println(transferPool.isTeminated());
//        if(transferPool.isTeminated()){
//            long end = System.currentTimeMillis();
//            long time = end - begin;
//            System.out.println("线程池共耗时"+time);
//        }
//        TransferPool.addTask(mailManager,false);
          //java自带线程
        int count = 20;//设置并发数量
                //线程池准备
                CyclicBarrier cyclicBarrier = new CyclicBarrier(count);
                ExecutorService executorService = ExecutorsUtil.newFixedThreadPool(count, "mailCore");
//        ExecutorService executorService = Executors.newFixedThreadPool(count);
                long now = System.currentTimeMillis();//开始时间
                for (int i = 0; i < count; i++)
                    executorService.execute(new TestPool().new Task(cyclicBarrier,i));
                    executorService.shutdown();
                while (!executorService.isTerminated()) {
                    try {
                        Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        long end = System.currentTimeMillis();//结束时间
        System.out.println("总共耗时!---------"+(end-now));//总共耗时
    }


    public class Task implements Runnable {
        private int failCount = 1;
        private CyclicBarrier cyclicBarrier;
        private int count;
        public Task(CyclicBarrier cyclicBarrier,int count) {
            this.cyclicBarrier = cyclicBarrier;
            this.count = count;
        }


        @Override
        public void run() {
            try {
                // 等待所有任务准备就绪
                cyclicBarrier.await();
                //测试路径
//              System.out.println("测试");
                boolean b = MailManagerJava.sendMail("13210277555@163.com", "你好，这是一封测试邮件，无需回复。", "测试文件邮件");
                if(!b){
                    failCount++;
                }
//                String href = "http://localhost:8080/App/Se?id="+count;
//                System.err.println(count);
            } catch (Exception e) {
                System.out.println("出现超时的线程"+count);
                e.printStackTrace();
            }
        }

    }

}
