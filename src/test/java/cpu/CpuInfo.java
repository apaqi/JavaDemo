package cpu;

/**
 * User: yangkuan@jd.com
 * Date: 18-6-6
 * Time: 下午5:45
 */
public class CpuInfo {
    public static void main(String[] args){
        int availProcessors = Runtime.getRuntime().availableProcessors();
        System.out.println("avail processors count: " + availProcessors);
    }

}
