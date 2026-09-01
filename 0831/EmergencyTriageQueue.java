import java.util.PriorityQueue;

public class EmergencyTriageQueue {

    static class Patient implements Comparable<Patient> {
        int triageLevel;
        int arrivalOrder;
        String medicalRecordNumber;

        public Patient(int triageLevel, int arrivalOrder, String medicalRecordNumber) {
            this.triageLevel = triageLevel;
            this.arrivalOrder = arrivalOrder;
            this.medicalRecordNumber = medicalRecordNumber;
        }

        @Override
        public int compareTo(Patient other) {
            if (this.triageLevel != other.triageLevel) {
                return Integer.compare(this.triageLevel, other.triageLevel);
            }
            if (this.arrivalOrder != other.arrivalOrder) {
                return Integer.compare(this.arrivalOrder, other.arrivalOrder);
            }
            return this.medicalRecordNumber.compareTo(other.medicalRecordNumber);
        }

        @Override
        public String toString() {
            return String.format("[病歷號: %s | 危急等級: %d | 到院序號: %d]", 
                    medicalRecordNumber, triageLevel, arrivalOrder);
        }
    }

    private final PriorityQueue<Patient> queue = new PriorityQueue<>();

    public void checkIn(int triageLevel, int arrivalOrder, String mrn) {
        queue.offer(new Patient(triageLevel, arrivalOrder, mrn));
    }

    public Patient peekNext() {
        return queue.peek();
    }

    public Patient callNext() {
        return queue.poll();
    }

    public int size() {
        return queue.size();
    }

    public boolean isEmpty() {
        return queue.isEmpty();
    }

    public static void main(String[] args) {
        EmergencyTriageQueue triage = new EmergencyTriageQueue();

        System.out.println("=== 病患陸續報到 ===");
        triage.checkIn(3, 1, "M001");
        triage.checkIn(1, 2, "M002");
        triage.checkIn(2, 3, "M003");
        triage.checkIn(1, 4, "M004");
        triage.checkIn(2, 5, "M005");

        System.out.println("目前候診人數: " + triage.size());
        System.out.println("下一位候診病患: " + triage.peekNext());

        System.out.println("\n=== 開始叫號看診 ===");
        while (!triage.isEmpty()) {
            Patient p = triage.callNext();
            System.out.println("叫號看診 -> " + p);
        }

        System.out.println("\n=== 測試空佇列叫號 ===");
        Patient emptyResult = triage.callNext();
        if (emptyResult == null) {
            System.out.println("目前候診佇列為空，無病患可叫號。");
        }
    }
}