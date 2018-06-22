package thread.call.run;

public class RunThread implements Runnable { // 实现了Runnable接口，jdk就知道这个类是一个线程
    String name="";
    public RunThread(String name ){
        this.name = name;
    }
    public void run() {
        for (int i = 0; i < 100; i++) {
            System.out.println(name+"进入Runner运行状态——————————" + i);
            try {
                Thread.sleep(1000);
            } catch (Exception e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }

    }}
