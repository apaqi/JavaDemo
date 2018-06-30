package staticTest;

/**
 * User: yangkuan@jd.com
 * Date: 18-6-28
 * Time: 下午8:40
 */
public class StaticInstance {
    public static void main(String[] args) {
        A a1 = new A();
        System.out.println(a1.i);
        System.out.println(a2.i);
    }
    static A a2 = new A();
}
class A {
    A() {
        i = (j++ != 0) ? ++j : --j;
    }
    public  int i;
    public  static int j =1;
}

