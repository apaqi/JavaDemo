package java8;

import com.google.common.collect.Lists;

import java.util.List;
import java.util.stream.Stream;

/**
 * User: yangkuan@jd.com
 * Date: 18-7-26
 * Time: 上午10:36
 */
public class SteamTest {
    public static void main(String[] args){
        List<Integer> nums = Lists.newArrayList(1,1,null,2,3,4,null,5,6,7,8,9,10);
        System.out.println("sum is:"+nums.stream().filter(num -> num != null).
                distinct().mapToInt(num -> num * 2).
                peek(System.out::println).skip(2).limit(4).sum());

    }
}
