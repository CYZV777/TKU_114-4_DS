import java.util.Arrays;

class CircularQueue<T> {
    private Object[] data;
    private int front;
    private int rear;
    private int size;
    private int capacity;

    public CircularQueue(int capacity) {
        this.capacity = capacity;
        this.data = new Object[capacity];
        this.front = 0;
        this.rear = 0;
        this.size = 0;
    }

    public boolean isFull() {
        return size == capacity;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public boolean enqueue(T value) {
        if (isFull()) {
            System.out.println("Enqueue 失敗: 佇列已滿 (Full)，無法加入 " + value);
            printStatus();
            return false;
        }
        data[rear] = value;
        rear = (rear + 1) % capacity;
        size++;
        System.out.println("執行 enqueue(\"" + value + "\")");
        printStatus();
        return true;
    }

    @SuppressWarnings("unchecked")
    public T dequeue() {
        if (isEmpty()) {
            System.out.println("Dequeue 失敗: 佇列為空 (Empty)");
            printStatus();
            return null;
        }
        T removedValue = (T) data[front];
        data[front] = null;
        front = (front + 1) % capacity;
        size--;
        System.out.println("執行 dequeue() -> 取出: \"" + removedValue + "\"");
        printStatus();
        return removedValue;
    }

    public void printStatus() {
        System.out.println("  Array: " + Arrays.toString(data) + 
                           " | front=" + front + 
                           " | rear=" + rear + 
                           " | size=" + size);
        System.out.println("-----------------------------------------------------------------");
    }
}

public class CircularQueuePractice {
    public static void main(String[] args) {
        CircularQueue<String> cq = new CircularQueue<>(4);

        System.out.println("=== 初始狀態 (容量 4) ===");
        cq.printStatus();

        cq.enqueue("A");
        cq.enqueue("B");
        cq.enqueue("C");

        cq.dequeue();
        cq.dequeue();

        cq.enqueue("D");
        cq.enqueue("E");
        cq.enqueue("F");

        cq.dequeue();
        cq.enqueue("G");

        System.out.println("\n=== 依 FIFO 順序取出所有剩餘元素 ===");
        while (!cq.isEmpty()) {
            cq.dequeue();
        }
    }
}