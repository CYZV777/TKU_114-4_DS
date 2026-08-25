class Task {
    private String id;
    private String title;

    public Task(String id, String title) {
        this.id = id;
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public String toString() {
        return "[" + id + ": " + title + "]";
    }
}

class TaskNode {
    Task task;
    TaskNode next;

    public TaskNode(Task task) {
        this.task = task;
        this.next = null;
    }
}

class TaskLinkedList {
    private TaskNode head;
    private int size;

    public TaskLinkedList() {
        this.head = null;
        this.size = 0;
    }

    public TaskNode findById(String id) {
        TaskNode current = head;
        while (current != null) {
            if (current.task.getId().equals(id)) {
                return current;
            }
            current = current.next;
        }
        return null;
    }

    public boolean addFirst(Task task) {
        if (findById(task.getId()) != null) {
            System.out.println("新增失敗: ID " + task.getId() + " 已存在！");
            return false;
        }
        TaskNode newNode = new TaskNode(task);
        newNode.next = head;
        head = newNode;
        size++;
        return true;
    }

    public boolean addLast(Task task) {
        if (findById(task.getId()) != null) {
            System.out.println("新增失敗: ID " + task.getId() + " 已存在！");
            return false;
        }
        TaskNode newNode = new TaskNode(task);
        if (head == null) {
            head = newNode;
        } else {
            TaskNode current = head;
            while (current.next != null) {
                current = current.next;
            }
            current.next = newNode;
        }
        size++;
        return true;
    }

    public boolean insertAfter(String existingId, Task task) {
        if (findById(task.getId()) != null) {
            System.out.println("新增失敗: ID " + task.getId() + " 已存在！");
            return false;
        }
        TaskNode targetNode = findById(existingId);
        if (targetNode == null) {
            System.out.println("插入失敗: 找不到目標 ID " + existingId);
            return false;
        }
        TaskNode newNode = new TaskNode(task);
        newNode.next = targetNode.next;
        targetNode.next = newNode;
        size++;
        return true;
    }

    public boolean removeById(String id) {
        if (head == null) {
            System.out.println("刪除失敗: 串列為空！");
            return false;
        }

        if (head.task.getId().equals(id)) {
            System.out.println("成功刪除 (Head): " + head.task);
            head = head.next;
            size--;
            return true;
        }

        TaskNode current = head;
        while (current.next != null && !current.next.task.getId().equals(id)) {
            current = current.next;
        }

        if (current.next != null) {
            System.out.println("成功刪除: " + current.next.task);
            current.next = current.next.next;
            size--;
            return true;
        }

        System.out.println("刪除失敗: 找不到 ID " + id);
        return false;
    }

    public int size() {
        return size;
    }

    public void printAll() {
        System.out.print("目前清單 (size=" + size + "): ");
        if (head == null) {
            System.out.println("Empty");
            return;
        }
        TaskNode current = head;
        while (current != null) {
            System.out.print(current.task);
            if (current.next != null) {
                System.out.print(" -> ");
            }
            current = current.next;
        }
        System.out.println(" -> null");
    }
}

public class LinkedTaskListSystem {
    public static void main(String[] args) {
        TaskLinkedList list = new TaskLinkedList();

        System.out.println("--- 測試 1: 空 List 狀態 ---");
        list.printAll();
        list.removeById("T01");

        System.out.println("\n--- 測試 2: 新增任務 (addFirst, addLast, insertAfter) ---");
        list.addLast(new Task("T02", "寫作業"));
        list.addFirst(new Task("T01", "吃早餐"));
        list.addLast(new Task("T04", "睡覺"));
        list.insertAfter("T02", new Task("T03", "看書"));
        list.printAll();

        System.out.println("\n--- 測試 3: 防重複 ID ---");
        list.addLast(new Task("T02", "重複任務測試"));

        System.out.println("\n--- 測試 4: 查詢 findById ---");
        TaskNode found = list.findById("T03");
        System.out.println("查詢 T03: " + (found != null ? found.task : "找不到"));

        System.out.println("\n--- 測試 5: 刪除測試 ---");
        
        list.removeById("T01");
        list.printAll();

        list.removeById("T03");
        list.printAll();

        list.removeById("T04");
        list.printAll();

        list.removeById("T99");
        list.printAll();
    }
}