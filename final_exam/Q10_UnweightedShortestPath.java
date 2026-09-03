import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;

public class Q10_UnweightedShortestPath {

    public static List<String> shortestPath(
            Map<String, List<String>> graph,
            String start, String target) {

        if (graph == null || start == null || target == null) {
            return new ArrayList<>();
        }
        if (!graph.containsKey(start) || !graph.containsKey(target)) {
            return new ArrayList<>();
        }

        if (start.equals(target)) {
            List<String> single = new ArrayList<>();
            single.add(start);
            return single;
        }

        Queue<String> queue = new ArrayDeque<>();
        Map<String, String> predecessor = new HashMap<>();

        queue.offer(start);
        predecessor.put(start, null);

        boolean reached = false;
        while (!queue.isEmpty()) {
            String curr = queue.poll();

            if (curr.equals(target)) {
                reached = true;
                break;
            }

            List<String> neighbors = graph.get(curr);
            if (neighbors != null) {
                for (String neighbor : neighbors) {
                    if (neighbor != null && !predecessor.containsKey(neighbor)) {
                        predecessor.put(neighbor, curr);
                        queue.offer(neighbor);
                    }
                }
            }
        }

        if (!reached) {
            return new ArrayList<>();
        }

        List<String> path = new ArrayList<>();
        String curr = target;
        while (curr != null) {
            path.add(curr);
            curr = predecessor.get(curr);
        }
        Collections.reverse(path);
        return path;
    }

    public static void main(String[] args) {
        Map<String, List<String>> graph = new HashMap<>();
        graph.put("A", List.of("B", "C"));
        graph.put("B", List.of("D"));
        graph.put("C", List.of("D"));
        graph.put("D", List.of("E"));
        graph.put("E", List.of());
        graph.put("F", List.of());

        System.out.println("A 到 D 最短路徑: " + shortestPath(graph, "A", "D")); // [A, B, D]
        System.out.println("A 到 E 最短路徑: " + shortestPath(graph, "A", "E")); // [A, B, D, E]

        System.out.println("起點終點相同 (A 到 A): " + shortestPath(graph, "A", "A")); // [A]

        System.out.println("不可到達 (A 到 F): " + shortestPath(graph, "A", "F")); // []

        System.out.println("不存在節點 (A 到 Z): " + shortestPath(graph, "A", "Z")); // []
    }
}