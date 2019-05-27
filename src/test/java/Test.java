import com.warm.utils.HttpClientUtil;
import com.warm.utils.WebConst;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Test {
    //        String temp = "0/2";
//        String[] split = temp.split("/");
//        System.err.println(split.length);
//        System.err.println(split[0] + split[1]);
//        String redirect_uri_encode = "";
//        String cmd2 = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx8e17aa77af6c4ae3&redirect_uri=http://www.jiazhang111.xyz/taskCallback&response_type=code&scope=snsapi_userinfo&state=0&connect_redirect=1#wechat_redirect";
//        try {
//            redirect_uri_encode = URLEncoder.encode(cmd2, "UTF-8");
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
//        String s = HttpClientUtil.sendGet("https://12i.cn/api.ashx?format=txt&userId=4590&key=AF5A09C5C41AF4625087B47224381808&url="+redirect_uri_encode);
//        System.err.println(s);
//        Double div = WebConst.div(1.0, 3.0, 4);
//        System.err.println(div*100);
//        System.err.println(div);
//        System.err.println(div.toString().length());
//        String substring = div.toString().substring(2, 6);
//        System.err.println(substring);
//        System.err.println(substring.toString().substring(0,1)+"."+substring.toString().substring(2,3));
//        System.err.println(div.toString().length()>5?Double.parseDouble(div.toString().substring(0,5))*100:div*100);
//    lock锁
//        private ArrayList<Integer> arrayList = new ArrayList<Integer>();
//        private Lock lock = new ReentrantLock();    //注意这个地方
//        public static void main(String[] args)  {
//            final Test test = new Test();
//
//            new Thread(){
//                public void run() {
//                    test.insert(Thread.currentThread());
//                };
//            }.start();
//
//            new Thread(){
//                public void run() {
//                    test.insert(Thread.currentThread());
//                };
//            }.start();
//        }
//
//        public void insert(Thread thread) {
//            if(lock.tryLock()) {
//                try {
//                    System.out.println(thread.getName()+"得到了锁");
//                    for(int i=0;i<5;i++) {
//                        arrayList.add(i);
//                    }
//                } catch (Exception e) {
//                    // TODO: handle exception
//                }finally {
//                    System.out.println(thread.getName()+"释放了锁");
//                    lock.unlock();
//                }
//            } else {
//                System.out.println(thread.getName()+"获取锁失败");
//            }
//        }
    private static int num = 0;

    public static void add() {
        num++;
    }

    public static void main(String[] args) {
        int threadNum = 0;
        Thread[] myThread = new Thread[10];
        for (Thread thread : myThread) {
            thread = new Thread() {
                @Override
                public void run() {
                    for (int i = 0; i < 1000; i++) {
                        add();
                    }
                }
            };
            thread.start();
        }
        while (Thread.activeCount() > 1) {
            if (threadNum != Thread.activeCount()) {
                threadNum = Thread.activeCount();
                System.err.println(Thread.activeCount());
            }
            Thread.yield();
        }
        System.err.println(Thread.activeCount());
        System.out.println(num);
    }


}
