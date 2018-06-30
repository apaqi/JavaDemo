package NoLockQueue;

/**
 * 生产者和消费者分别是单线程
 * User: yangkuan@jd.com
 * Time: 下午7:00
 */
public class RingBuffer {
    private int bufferSize = 1024;
    private String[] buffer = new String[bufferSize];
    private int head = 0;
    private int tail = 0;
    RingBuffer(int size){
        this.bufferSize = size;
        buffer = new String[size];
    }
    private Boolean empty() {
        return head == tail;
    }
    private Boolean full() {
        return (tail + 1) % bufferSize == head;
    }
    public Boolean put(String v) {
        if (full()) {
            return false;
        }
        buffer[tail] = v;
        tail = (tail + 1) % bufferSize;
        return true;
    }
    public String get() {
        if (empty()) {
            return null;
        }
        String result = buffer[head];
        head = (head + 1) % bufferSize;
        return result;

    }
}
