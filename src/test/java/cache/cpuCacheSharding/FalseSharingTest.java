package cache.cpuCacheSharding;



/**
 * 由于CPU的速度远远大于内存速度，所以CPU设计者们就给CPU加上了缓存(CPU Cache)。 以免运算被内存速度拖累。
 * （就像我们写代码把共享数据做Cache不想被DB存取速度拖累一样），CPU Cache分成了三个级别：L1，L2，L3。级别越小越接近CPU, 所以速度也更快, 同时也代表着容量越小。
 CPU获取数据回依次从L1，L2，L3中查找，如果都找不到则会直接向内存查找。

 由于共享变量在CPU缓存中的存储是以缓存行为单位，一个缓存行可以存储多个变量（存满当前缓存行的字节数）；而CPU对缓存的修改又是以缓存行为最小单位的，那么就会出现上诉的伪共享问题。

 Cache Line可以简单的理解为CPU Cache中的最小缓存单位，今天的CPU不再是按字节访问内存，而是以64字节为单位的块(chunk)拿取，
 称为一个缓存行(cache line)。当你读一个特定的内存地址，整个缓存行将从主存换入缓存，并且访问同一个缓存行内的其它值的开销是很小的。

 *  *ValuePadding类的原理: 通过填充一些无用的字段p1,p2,p3,p4,p5,p6，再考虑到对象头也占用8bit, 刚好把对象占用的内存扩展到刚好占64bytes（或者64bytes的整数倍）。
 *  这样就避免了一个缓存行中加载多个对象。
 * cpu的高速缓存系统中是以缓存行（cache line）为单位存储的。缓存行是2的整数幂个连续字节，一般为32-256个字节。
 * jvm的内存设置小一些，应该更明显吧
 * User: yangkuan@jd.com
 * Date: 18-1-10
 * Time: 下午3:03
 */
public class FalseSharingTest implements Runnable{
    public final static long ITERATIONS = 500L * 1000L * 100L;
    private int arrayIndex = 0;

    private static ValuePadding[] longs;
    public FalseSharingTest(final int arrayIndex) {
        this.arrayIndex = arrayIndex;
    }

    public static void main(final String[] args) throws Exception {
        for(int i=1;i<10;i++){
            System.gc();
            final long start = System.currentTimeMillis();
            runTest(i);
            System.out.println("Thread num "+i+" duration = " + (System.currentTimeMillis() - start));
        }

    }

    private static void runTest(int NUM_THREADS) throws InterruptedException {
        Thread[] threads = new Thread[NUM_THREADS];
        longs = new ValuePadding[NUM_THREADS];
        for (int i = 0; i < longs.length; i++) {
            longs[i] = new ValuePadding();
        }
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(new FalseSharingTest(i));
        }

        for (Thread t : threads) {
            t.start();
        }

        for (Thread t : threads) {
            t.join();
        }
    }

    public void run() {
        long i = ITERATIONS + 1;
        while (0 != --i) {
            longs[arrayIndex].value = 0L;
        }
    }

}

/**
 *注意：
 * Contended这个注解在jdk1.8才有
 * 且执行时，必须加上虚拟机参数-XX:-RestrictContended，@Contended注释才会生效。很多文章把这个漏掉了，那样的话实际上就没有起作用。
 */
//@Contended
  final  class ValuePadding {
   protected long p1, p2, p3, p4, p5, p6, p7;
    protected volatile long value = 0L;
    protected long p9, p10, p11, p12, p13, p14, p15;
}

final   class ValueNoPadding {
  // protected long p1, p2, p3, p4, p5, p6, p7;
  protected volatile long value = 0L;
  // protected long p9, p10, p11, p12, p13, p14, p15;
}