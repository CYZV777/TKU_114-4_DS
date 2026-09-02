import java.util.*;

public class IterativeDfsTrace {

    public static void iterativeDfsWithTrace(Map<String, List<String>> graph, String start) {
        if (graph == null || start == null || !graph.containsKey(start)) {
            System.out.println("[Edge Case] 圖為空或起點不存在，終止搜尋。");
            return;
        }

        Deque<String> stack = new ArrayDeque<>();
        Set<String> visited = new LinkedHashSet<>();

        stack.push(start);
        System.out.printf("[PUSH] 節點: %s | Stack: %s | Visited: %s%n", start, stack, visited);

        while (!stack.isEmpty()) {
            String curr = stack.pop();
            System.out.printf("[POP ] 節點: %s | Stack: %s | Visited: %s%n", curr, stack, visited);

            if (!visited.contains(curr)) {
                visited.add(curr);
                System.out.printf("  -> 造訪: %s | 累積 Visited: %s%n", curr, visited);

                List<String> neighbors = graph.getOrDefault(curr, Collections.emptyList());
                for (int i = neighbors.size() - 1; i >= 0; i--) {
                    String neighbor = neighbors.get(i);
                    if (!visited.contains(neighbor)) {
                        stack.push(neighbor);
                        System.out.printf("[PUSH] 節點: %s | Stack: %s | Visited: %s%n", neighbor, stack, visited);
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        Map<String, List<String>> graph = new HashMap<>();
        graph.put("A", Arrays.asList("B", "C"));
        graph.put("B", Arrays.asList("D"));
        graph.put("C", Arrays.asList("E"));
        graph.put("D", Collections.emptyList());
        graph.put("E", Collections.emptyList());

        System.out.println("--- 一般案例 (Start: A) ---");
        iterativeDfsWithTrace(graph, "A");

        System.out.println("\n--- 邊界案例 (Missing Node) ---");
        iterativeDfsWithTrace(graph, "X");
    }
}