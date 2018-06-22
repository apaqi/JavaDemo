package NoLockQueue;



/**
 * User: yangkuan@jd.com
 * Date: 18-5-22
 * Time: 下午7:01
 */
public class RBufferTest {
    RingBuffer ringBuffer = new RingBuffer(10);

    public  void test( ) throws InterruptedException {

        ringBuffer.put("1");
        String data = ringBuffer.get();
        data = ringBuffer.get();
        System.out.println("读取数据:"+data);

        Thread pThread = new Thread(new Runnable() {
            Integer index = 0;
            @Override
            public void run() {
                while (true){
                    ringBuffer.put(""+index);
                    System.out.println("写入数据:" + index);
                    index++;
                  /*  try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }*/
                }
            }
        });
        Thread cThread = new Thread(new Runnable() {
            Integer index = 0;
            @Override
            public void run() {
                while (true){
                    String data = ringBuffer.get();
                    if(data==null){
                       //System.out.println("读取数据为空:"+data);
                   /*     try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }*/
                    }else{
                        System.out.println("读取数据正常:"+data);
                    }
                }
            }
        });

        pThread.start();
        cThread.start();
        pThread.join();
        cThread.join();
    }

}
