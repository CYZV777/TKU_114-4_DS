import java.util.PriorityQueue;

public class SupportTicketQueue {

    static class Ticket implements Comparable<Ticket> {
        String id;
        int severity;
        int createdOrder;

        public Ticket(String id, int severity, int createdOrder) {
            this.id = id;
            this.severity = severity;
            this.createdOrder = createdOrder;
        }

        @Override
        public int compareTo(Ticket other) {
            if (this.severity != other.severity) {
                return Integer.compare(other.severity, this.severity);
            }
            return Integer.compare(this.createdOrder, other.createdOrder);
        }

        @Override
        public String toString() {
            return id + "|" + severity + "|" + createdOrder;
        }
    }

    public static void main(String[] args) {
        PriorityQueue<Ticket> pq = new PriorityQueue<>();

        pq.add(new Ticket("T101", 3, 1));
        pq.add(new Ticket("T102", 5, 2));
        pq.add(new Ticket("T103", 5, 3));
        pq.add(new Ticket("T104", 1, 4));
        pq.add(new Ticket("T105", 3, 5));

        System.out.println("=== 依優先度處理 Ticket ===");
        while (!pq.isEmpty()) {
            Ticket ticket = pq.poll();
            System.out.println(ticket);
        }
    }
}