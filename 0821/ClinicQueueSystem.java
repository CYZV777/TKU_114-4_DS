import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Queue;

class Patient {
    private String id;
    private String name;

    public Patient(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "[" + id + "] " + name;
    }
}

public class ClinicQueueSystem {
    private Queue<Patient> waitingQueue = new ArrayDeque<>();
    private List<Patient> completedList = new ArrayList<>();

    public void register(String id, String name) {
        Patient patient = new Patient(id, name);
        waitingQueue.offer(patient);
        System.out.println("掛號成功: " + patient);
    }

    public void cancel(String id) {
        boolean found = false;
        Iterator<Patient> iterator = waitingQueue.iterator();
        while (iterator.hasNext()) {
            Patient p = iterator.next();
            if (p.getId().equals(id)) {
                iterator.remove();
                System.out.println("已取消掛號: " + p);
                found = true;
                break;
            }
        }
        if (!found) {
            System.out.println("取消失敗: 找不到病歷號 " + id);
        }
    }

    public void callNext() {
        if (waitingQueue.isEmpty()) {
            System.out.println("目前沒有候診病患！");
            return;
        }
        Patient nextPatient = waitingQueue.poll();
        completedList.add(nextPatient);
        System.out.println("叫號看診: " + nextPatient);
    }

    public void peekNext() {
        if (waitingQueue.isEmpty()) {
            System.out.println("目前無候診病患。");
        } else {
            System.out.println("下一位候診病患: " + waitingQueue.peek());
        }
    }

    public void printStatus() {
        System.out.println("\n--- 診所當前狀態 ---");
        System.out.println("候診佇列 (" + waitingQueue.size() + " 人): " + waitingQueue);
        System.out.println("已完成看診 (" + completedList.size() + " 人): " + completedList);
        System.out.println("--------------------\n");
    }

    public static void main(String[] args) {
        ClinicQueueSystem clinic = new ClinicQueueSystem();

        clinic.register("P001", "王小明");
        clinic.register("P002", "李小美");
        clinic.register("P003", "張大華");
        clinic.register("P004", "陳小玉");

        clinic.printStatus();

        clinic.peekNext();

        clinic.callNext();

        clinic.cancel("P003");

        clinic.callNext();

        clinic.printStatus();
    }
}