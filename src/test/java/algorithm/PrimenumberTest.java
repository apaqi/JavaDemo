package algorithm;

import org.junit.Test;

/**
 * User: yangkuan@jd.com
 * Date: 18-6-28
 * Time: 下午7:18
 */
public class PrimenumberTest {
    @Test
    public void FindNextPrime(){
        FindNextPrime(211);
    }
    public int FindNextPrime( int  i) {
        int nextPrime = i+1;
        while (nextPrime > i) {
            for(int j = 2; j < nextPrime;j++){
                if (nextPrime % j == 0) {
                    break;
                }
                if (j == nextPrime-1) {
                    System.out.println("大于"+i+"的最近素数:"+nextPrime);
                    return nextPrime;
                }
            }
            nextPrime++;
        }
        return nextPrime;
    }
}
