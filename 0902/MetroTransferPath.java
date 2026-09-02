import java.util.*;

public class MetroTransferPath {

    public static class PathResult {
        public List<String> path;
        public int edgeCount;

        public PathResult(List<String> path, int edgeCount) {
            this.path = path;
            this.edgeCount = edgeCount;
        }

        @Override
        public String toString() {
            if (path == null) return "無可行路徑 (Unreachable)";
            return "路徑: " + String.join(" -> ", path) + " | 總 Edge 數: " + edgeCount;
        }
    }

    public static PathResult findShortestPath(Map<String, List<String>> graph, String start, String target) {
        if (graph == null || start == null || target == null ||
            !graph.containsKey(start) || !graph.containsKey(target)) {
            return new PathResult(null, -1);
        }

        if (start.equals(target)) {
            return new PathResult(Collections.singletonList(start), 0);
        }

        Queue<String> queue = new LinkedList<>();
        Map<String, String> parent = new HashMap<>();

        queue.offer(start);
        parent.put(start, null);

        while (!queue.isEmpty()) {
            String curr = queue.poll();
            if (curr.equals(target)) break;

            for (String next : graph.getOrDefault(curr, Collections.emptyList())) {
                if (!parent.containsKey(next)) {
                    parent.put(next, curr);
                    queue.offer(next);
                }
            }
        }

        if (!parent.containsKey(target)) {
            return new PathResult(null, -1);
        }

        List<String> path = new ArrayList<>();
        for (String at = target; at != null; at = parent.get(at)) {
            path.add(at);
        }
        Collections.reverse(path);

        return new PathResult(path, path.size() - 1);
    }

    public static void main(String[] args) {
        Map<String, List<String>> metro = new HashMap<>();
        metro.put("淡水", Arrays.asList("北投"));
        metro.put("北投", Arrays.asList("淡水", "士林"));
        metro.put("士林", Arrays.asList("北投", "台北車站"));
        metro.put("台北車站", Arrays.asList("士林", "大安", "西門"));
        metro.put("大安", Arrays.asList("台北車站"));
        metro.put("西門", Arrays.asList("台北車站"));
        metro.put("獨立站點", Collections.emptyList());

        System.out.println("--- 一般案例 (淡水 -> 大安) ---");
        System.out.println(findShortestPath(metro, "淡水", "大安"));

        System.out.println("\n--- 邊界案例 1: 兩站不連通 ---");
        System.out.println(findShortestPath(metro, "淡水", "獨立站點"));

        System.out.println("\n--- 邊界案例 2: 站點不存在 ---");
        System.out.println(findShortestPath(metro, "淡水", "高雄車站"));
    }
}