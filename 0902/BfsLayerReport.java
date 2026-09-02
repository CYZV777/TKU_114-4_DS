import java.util.*;

public class BfsLayerReport {

    public static Map<String, Integer> getBfsDistances(Map<String, List<String>> graph, String start) {
        Map<String, Integer> distances = new LinkedHashMap<>();
        if (graph == null || start == null || !graph.containsKey(start)) {
            return distances;
        }

        Queue<String> queue = new LinkedList<>();
        Set<String> visited = new HashSet<>();

        queue.offer(start);
        visited.add(start);
        distances.put(start, 0);

        while (!queue.isEmpty()) {
            String curr = queue.poll();
            int currDist = distances.get(curr);

            for (String neighbor : graph.getOrDefault(curr, Collections.emptyList())) {
                if (!visited.contains(neighbor)) {
                    visited.add(neighbor);
                    distances.put(neighbor, currDist + 1);
                    queue.offer(neighbor);
                }
            }
        }

        return distances;
    }

    public static void main(String[] args) {
        Map<String, List<String>> graph = new HashMap<>();
        graph.put("A", Arrays.asList("B", "C"));
        graph.put("B", Arrays.asList("A", "D"));
        graph.put("C", Arrays.asList("A", "E"));
        graph.put("D", Arrays.asList("B"));
        graph.put("E", Arrays.asList("C"));

        System.out.println("--- 一般案例 (Start: A) ---");
        Map<String, Integer> result = getBfsDistances(graph, "A");
        result.forEach((k, v) -> System.out.println("Vertex " + k + " : " + v + " edges"));

        System.out.println("\n--- 邊界案例 1: 節點不存在 (Start: Z) ---");
        System.out.println("Result: " + getBfsDistances(graph, "Z"));

        System.out.println("\n--- 邊界案例 2: 圖為空 (Empty Graph) ---");
        System.out.println("Result: " + getBfsDistances(new HashMap<>(), "A"));
    }
}