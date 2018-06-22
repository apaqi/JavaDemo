package cache;

/**
 * 注意当步长在1到16范围内，循环运行时间几乎不变。但从16开始，每次步长加倍，运行时间减半。

 背后的原因是今天的CPU不再是按字节访问内存，而是以64字节为单位的块(chunk)拿取，称为一个缓存行(cache line)。当你读一个特定的内存地址，整个缓存行将从主存换入缓存，并且访问同一个缓存行内的其它值的开销是很小的。

 由于16个整型数占用64字节（一个缓存行），for循环步长在1到16之间必定接触到相同数目的缓存行：即数组中所有的缓存行。当步长为32，我们只有大约每两个缓存行接触一次，当步长为64，只有每四个接触一次。

 理解缓存行对某些类型的程序优化而言可能很重要。比如，数据字节对齐可能决定一次操作接触1个还是2个缓存行。那上面的例子来说，很显然操作不对齐的数据将损失一半性能。
 https://coolshell.cn/articles/10249.html  7个示例科普CPU CACHE
 * User: yangkuan@jd.com
 * Date: 18-1-10
 * Time: 下午4:34
 */
public class CacheAccess {

    public static void main(String[] args) {
        int[] arr = new int[64 * 1024 * 1024];
        long start = System.nanoTime();
        for (int i = 0; i < arr.length; i++) {
            arr[i] *= 3;
            //System.out.println(arr[i]);
        }
        System.out.println((System.nanoTime() - start)/1000/1000);

        long start2 = System.nanoTime();
        for (int i = 0; i < arr.length; i += 16) {
            arr[i] *= 3;
        }
        System.out.println((System.nanoTime() - start2)/1000/1000);
    }
}
