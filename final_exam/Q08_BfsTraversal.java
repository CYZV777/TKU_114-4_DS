import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

public class Q08_BfsTraversal {

    public static List<String> bfs(Map<String, List<String>> graph, String start) {
        if (graph == null || start == null || !graph.containsKey(start)) {
            return new ArrayList<>();
        }

        List<String> order = new ArrayList<>();
        Set<String> visited = new HashSet<>();
        Queue<String> queue = new ArrayDeque<>();

        visited.add(start);
        queue.offer(start);

        while (!queue.isEmpty()) {
            String curr = queue.poll();
            order.add(curr);

            List<String> neighbors = graph.get(curr);
            if (neighbors != null) {
                for (String neighbor : neighbors) {
                    if (neighbor != null && !visited.contains(neighbor)) {
                        visited.add(neighbor);
                        queue.offer(neighbor);
                    }
                }
            }
        }

        return order;
    }

    public static Map<String, Integer> distanceFrom(Map<String, List<String>> graph, String start) {
        if (graph == null || start == null || !graph.containsKey(start)) {
            return new HashMap<>();
        }

        Map<String, Integer> dist = new HashMap<>();
        Queue<String> queue = new ArrayDeque<>();

        dist.put(start, 0);
        queue.offer(start);

        while (!queue.isEmpty()) {
            String curr = queue.poll();
            int currentDist = dist.get(curr);

            List<String> neighbors = graph.get(curr);
            if (neighbors != null) {
                for (String neighbor : neighbors) {
                    if (neighbor != null && !dist.containsKey(neighbor)) {
                        dist.put(neighbor, currentDist + 1);
                        queue.offer(neighbor);
                    }
                }
            }
        }

        return dist;
    }

    public static void main(String[] args) {
        Map<String, List<String>> graph = new HashMap<>();
        graph.put("A", List.of("B", "C"));
        graph.put("B", List.of("D"));
        graph.put("C", List.of("D", "E"));
        graph.put("D", List.of("A"));
        graph.put("E", List.of());
        graph.put("F", List.of());

        System.out.println("BFS 順序: " + bfs(graph, "A"));

        System.out.println("起點 A 的距離: " + distanceFrom(graph, "A"));

        System.out.println("無效起點 BFS: " + bfs(graph, "Z"));
        System.out.println("無效起點距離: " + distanceFrom(graph, null));
    }
}