import java.util.Objects;
import java.util.PriorityQueue;

public class EventSimulationQueue {

    static class Event implements Comparable<Event> {
        int timestamp;
        String type;
        int sequence;

        public Event(int timestamp, String type, int sequence) {
            this.timestamp = timestamp;
            this.type = type;
            this.sequence = sequence;
        }

        @Override
        public int compareTo(Event other) {
            if (this.timestamp != other.timestamp) {
                return Integer.compare(this.timestamp, other.timestamp);
            }
            return Integer.compare(this.sequence, other.sequence);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Event event = (Event) o;
            return timestamp == event.timestamp &&
                   sequence == event.sequence &&
                   Objects.equals(type, event.type);
        }

        @Override
        public int hashCode() {
            return Objects.hash(timestamp, type, sequence);
        }

        @Override
        public String toString() {
            return String.format("[時間: %02d | Sequence: %d | 類型: %s]", timestamp, sequence, type);
        }
    }

    private final PriorityQueue<Event> eventQueue = new PriorityQueue<>();

    public void scheduleEvent(int timestamp, String type, int sequence) {
        eventQueue.offer(new Event(timestamp, type, sequence));
    }

    public boolean cancelEvent(int timestamp, String type, int sequence) {
        return eventQueue.remove(new Event(timestamp, type, sequence));
    }

    public void runSimulation() {
        System.out.println("=== 開始執行事件模擬 ===");
        if (eventQueue.isEmpty()) {
            System.out.println("無排定事件可執行。");
            return;
        }

        while (!eventQueue.isEmpty()) {
            Event currentEvent = eventQueue.poll();
            System.out.println("執行事件 -> " + currentEvent);
        }
        System.out.println("=== 模擬結束 ===");
    }

    public static void main(String[] args) {
        EventSimulationQueue simulator = new EventSimulationQueue();

        simulator.scheduleEvent(10, "LOG_BACKUP", 1);
        simulator.scheduleEvent(5, "USER_LOGIN", 2);
        simulator.scheduleEvent(5, "SEND_NOTIFICATION", 3); // 同樣時間 5，依 sequence 順序
        simulator.scheduleEvent(20, "SYSTEM_SHUTDOWN", 4);
        simulator.scheduleEvent(15, "DATA_SYNC", 5);

        System.out.println("=== 取消事件測試 ===");
        boolean isCancelled = simulator.cancelEvent(10, "LOG_BACKUP", 1);
        System.out.println("取消 LOG_BACKUP (時間 10, seq 1): " + (isCancelled ? "成功" : "失敗"));

        boolean isFailedCancel = simulator.cancelEvent(99, "NON_EXIST", 99);
        System.out.println("取消不存在的事件: " + (isFailedCancel ? "成功" : "失敗 (不存在)") + "\n");

        simulator.runSimulation();
    }
}