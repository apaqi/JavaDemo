package equals;

import org.junit.Test;

/**
 * User: yangkuan@jd.com
 * Date: 18-6-28
 * Time: 下午8:15
 */
public class EqualsTest {
    @Test
    public void basicType(){
        Integer a = new Integer(5);
        Integer b = new Integer(5);
        System.out.println(a==b);
        System.out.println(a.equals(b));
       String s1 = "abc";
        String s2 = new String("abc");
        String s3 = "abc";
        String s4 = new String("abc");
        System.out.println(s1==s2);
        System.out.println(s1.equals(s2));
        System.out.println(s1==s3);
        System.out.println(s1.equals(s3));

        System.out.println(s2==s4);
        System.out.println(s2.equals(s4));

    }
}
