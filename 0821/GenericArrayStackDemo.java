class ArrayStack<T> {
    private Object[] data;
    private int top;
    private int capacity;

    public ArrayStack(int capacity) {
        this.capacity = capacity;
        this.data = new Object[capacity];
        this.top = -1;
    }

    public boolean isFull() {
        return top == capacity - 1;
    }

    public boolean isEmpty() {
        return top == -1;
    }

    public int size() {
        return top + 1;
    }

    public boolean push(T item) {
        if (isFull()) {
            System.out.println("Push 失敗: 堆疊已滿 (Full)");
            return false;
        }
        data[++top] = item;
        return true;
    }

    @SuppressWarnings("unchecked")
    public T pop() {
        if (isEmpty()) {
            System.out.println("Pop 失敗: 堆疊為空 (Empty)");
            return null;
        }
        T item = (T) data[top];
        data[top--] = null;
        return item;
    }

    @SuppressWarnings("unchecked")
    public T peek() {
        if (isEmpty()) {
            System.out.println("Peek 失敗: 堆疊為空 (Empty)");
            return null;
        }
        return (T) data[top];
    }

    public void printStack() {
        System.out.print("Stack (size=" + size() + "/" + capacity + "): [");
        for (int i = 0; i <= top; i++) {
            System.out.print(data[i]);
            if (i < top) {
                System.out.print(", ");
            }
        }
        System.out.println("] (Top: " + (isEmpty() ? "none" : data[top]) + ")");
    }
}

public class GenericArrayStackDemo {
    public static void main(String[] args) {
        System.out.println("=== 測試 1: ArrayStack<String> (容量: 3) ===");
        ArrayStack<String> stringStack = new ArrayStack<>(3);

        System.out.println("初始 isEmpty: " + stringStack.isEmpty());
        stringStack.push("Java");
        stringStack.push("Data Structure");
        stringStack.push("Stack");
        stringStack.printStack();

        System.out.println("isFull: " + stringStack.isFull());
        stringStack.push("Overflow");

        System.out.println("Peek 頂端: " + stringStack.peek());
        System.out.println("Pop 彈出: " + stringStack.pop());
        stringStack.printStack();
        System.out.println();

        System.out.println("=== 測試 2: ArrayStack<Integer> (容量: 4) ===");
        ArrayStack<Integer> intStack = new ArrayStack<>(4);

        intStack.push(10);
        intStack.push(20);
        intStack.push(30);
        intStack.push(40);
        intStack.printStack();

        System.out.println("Pop 彈出: " + intStack.pop());
        System.out.println("Pop 彈出: " + intStack.pop());
        intStack.printStack();

        System.out.println("Peek 頂端: " + intStack.peek());
        System.out.println("目前 size: " + intStack.size());

        intStack.pop();
        intStack.pop();
        intStack.pop();
        System.out.println("清空後 isEmpty: " + intStack.isEmpty());
    }
}