package pool;

import java.util.Calendar;
import java.util.Date;

/**
 * @Auther: 69013
 * @Date: 2018/11/16 14:52
 * @Description:
 */
public class TestPerformance implements Runnable{
//    Calendar calendar = Calendar.getInstance();
//    int second = calendar.get(Calendar.MILLISECOND);
//    int i=1;
    @Override
    public void run() {
//        i++;
//        System.out.println("当前第"+i+"次，当前时间是"+second);
        System.out.println("测试线程池");
    }
}
