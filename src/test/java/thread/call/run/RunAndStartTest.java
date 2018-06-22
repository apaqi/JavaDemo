package thread.call.run;

public class RunAndStartTest {
    public static void main(String[] args) {
        RunThread run1 = new RunThread("线程1");
      //  run1.run();
        RunThread run2 = new RunThread("线程2");
       //  run2.run();
        Thread thread1 = new Thread(run1);
        thread1.start();
        Thread thread2 = new Thread(run2);
        thread2.start();
        thread1.stop();
    }
}
