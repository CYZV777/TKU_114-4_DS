import java.util.*;

public class DirectedReachability {

    public static boolean isReachable(Map<String, List<String>> graph, String from, String to) {
        if (graph == null || from == null || to == null) return false;
        if (!graph.containsKey(from) || !graph.containsKey(to)) return false;
        if (from.equals(to)) return true;

        Queue<String> queue = new LinkedList<>();
        Set<String> visited = new HashSet<>();

        queue.offer(from);
        visited.add(from);

        while (!queue.isEmpty()) {
            String curr = queue.poll();
            if (curr.equals(to)) return true;

            for (String neighbor : graph.getOrDefault(curr, Collections.emptyList())) {
                if (!visited.contains(neighbor)) {
                    visited.add(neighbor);
                    queue.offer(neighbor);
                }
            }
        }
        return false;
    }

    public static void main(String[] args) {
        Map<String, List<String>> digraph = new HashMap<>();
        digraph.put("1", Arrays.asList("2"));
        digraph.put("2", Arrays.asList("3"));
        digraph.put("3", Arrays.asList("4"));
        digraph.put("4", Collections.emptyList());
        digraph.put("5", Arrays.asList("2"));

        System.out.println("--- 多組查詢測試 ---");
        String[][] queries = {
            {"1", "4"},
            {"4", "1"},
            {"5", "4"},
            {"1", "5"},
            {"1", "99"}
        };

        for (String[] q : queries) {
            System.out.printf("Query (%s -> %s): %s%n", q[0], q[1], isReachable(digraph, q[0], q[1]));
        }

        System.out.println("\n--- 邊界案例: 空圖 ---");
        System.out.println("Query (A -> B) on empty: " + isReachable(new HashMap<>(), "A", "B"));
    }
}