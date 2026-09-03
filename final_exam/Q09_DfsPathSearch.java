import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Q09_DfsPathSearch {

    public static List<String> dfs(Map<String, List<String>> graph, String start) {
        if (graph == null || start == null || !graph.containsKey(start)) {
            return new ArrayList<>();
        }

        List<String> result = new ArrayList<>();
        Set<String> visited = new HashSet<>();
        dfsHelper(graph, start, visited, result);
        return result;
    }

    private static void dfsHelper(Map<String, List<String>> graph, String curr, Set<String> visited, List<String> result) {
        visited.add(curr);
        result.add(curr);

        List<String> neighbors = graph.get(curr);
        if (neighbors != null) {
            for (String neighbor : neighbors) {
                if (neighbor != null && !visited.contains(neighbor)) {
                    dfsHelper(graph, neighbor, visited, result);
                }
            }
        }
    }

    public static boolean reachable(Map<String, List<String>> graph, String start, String target) {
        if (graph == null || start == null || target == null) {
            return false;
        }
        if (!graph.containsKey(start) || !graph.containsKey(target)) {
            return false;
        }

        if (start.equals(target)) {
            return true;
        }

        Set<String> visited = new HashSet<>();
        return reachHelper(graph, start, target, visited);
    }

    private static boolean reachHelper(Map<String, List<String>> graph, String curr, String target, Set<String> visited) {
        if (curr.equals(target)) {
            return true;
        }
        visited.add(curr);

        List<String> neighbors = graph.get(curr);
        if (neighbors != null) {
            for (String neighbor : neighbors) {
                if (neighbor != null && !visited.contains(neighbor)) {
                    if (reachHelper(graph, neighbor, target, visited)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static void main(String[] args) {
        Map<String, List<String>> graph = new HashMap<>();
        graph.put("A", List.of("B", "C"));
        graph.put("B", List.of("D"));
        graph.put("C", List.of("E"));
        graph.put("D", List.of("A"));
        graph.put("E", List.of());
        graph.put("F", List.of());

        System.out.println("DFS 走訪順序: " + dfs(graph, "A"));

        System.out.println("A 是否可到達 E: " + reachable(graph, "A", "E")); // true
        System.out.println("A 是否可到達 F: " + reachable(graph, "A", "F")); // false
        System.out.println("起點等於終點 (A -> A): " + reachable(graph, "A", "A")); // true

        System.out.println("未知起點可達性: " + reachable(graph, "Z", "A")); // false
        System.out.println("未知終點可達性: " + reachable(graph, "A", "Z")); // false
        System.out.println("無效起點 DFS: " + dfs(graph, "Z")); // []
    }
}