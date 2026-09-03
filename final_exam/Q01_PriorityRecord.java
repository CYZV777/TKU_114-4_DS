import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

public class Q01_PriorityRecord {

    public record Job(String id, int priority, long sequence) {}

    public static List<String> processOrder(List<Job> jobs) {
        if (jobs == null || jobs.isEmpty()) {
            return new ArrayList<>();
        }

        PriorityQueue<Job> pq = new PriorityQueue<>((a, b) -> {
            if (a.priority() != b.priority()) {
                return Integer.compare(a.priority(), b.priority());
            }
            if (a.sequence() != b.sequence()) {
                return Long.compare(a.sequence(), b.sequence());
            }
            return a.id().compareTo(b.id());
        });

        for (Job job : jobs) {
            if (job != null) {
                pq.offer(job);
            }
        }

        List<String> result = new ArrayList<>();
        while (!pq.isEmpty()) {
            result.add(pq.poll().id());
        }
        return result;
    }

    public static void main(String[] args) {
        List<Job> jobs = List.of(
            new Job("JobC", 2, 100L),
            new Job("JobB", 1, 200L),
            new Job("JobA", 1, 100L)
        );

        List<String> order = processOrder(jobs);
        System.out.println("執行排序結果：" + order);
    }
}