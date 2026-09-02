import java.util.*;

public class CampusNavigationSystem {

    static class Location {
        String id;
        String name;

        public Location(String id, String name) {
            this.id = id;
            this.name = name;
        }
    }

    private Map<String, Location> locations = new HashMap<>();
    private Map<String, List<String>> adjList = new HashMap<>();

    public void addLocation(String id, String name) {
        locations.put(id, new Location(id, name));
        adjList.putIfAbsent(id, new ArrayList<>());
    }

    public void addRoad(String u, String v) {
        if (!locations.containsKey(u) || !locations.containsKey(v)) return;
        adjList.get(u).add(v);
        adjList.get(v).add(u);
    }

    public List<String> findShortestPath(String startId, String targetId) {
        if (!locations.containsKey(startId) || !locations.containsKey(targetId)) {
            return Collections.emptyList();
        }

        if (startId.equals(targetId)) {
            return Collections.singletonList(locations.get(startId).name);
        }

        Queue<String> queue = new LinkedList<>();
        Map<String, String> parent = new HashMap<>();

        queue.offer(startId);
        parent.put(startId, null);

        while (!queue.isEmpty()) {
            String curr = queue.poll();
            if (curr.equals(targetId)) break;

            for (String neighbor : adjList.getOrDefault(curr, Collections.emptyList())) {
                if (!parent.containsKey(neighbor)) {
                    parent.put(neighbor, curr);
                    queue.offer(neighbor);
                }
            }
        }

        if (!parent.containsKey(targetId)) {
            return Collections.emptyList();
        }

        List<String> path = new ArrayList<>();
        for (String at = targetId; at != null; at = parent.get(at)) {
            path.add(locations.get(at).name);
        }
        Collections.reverse(path);
        return path;
    }

    public static void main(String[] args) {
        CampusNavigationSystem nav = new CampusNavigationSystem();

        nav.addLocation("L1", "校門口");
        nav.addLocation("L2", "圖書館");
        nav.addLocation("L3", "資工系館");
        nav.addLocation("L4", "學生活動中心");
        nav.addLocation("L5", "體育館");
        nav.addLocation("L6", "偏遠苗圃");

        nav.addRoad("L1", "L2");
        nav.addRoad("L2", "L3");
        nav.addRoad("L1", "L4");
        nav.addRoad("L4", "L3");
        nav.addRoad("L3", "L5");

        System.out.println("--- 一般案例 (校門口 -> 體育館) ---");
        List<String> path = nav.findShortestPath("L1", "L5");
        System.out.println("導航路徑: " + String.join(" -> ", path));
        System.out.println("經過邊數 (Edges): " + (path.size() - 1));

        System.out.println("\n--- 邊界案例 1: 節點不存在 (Missing Node) ---");
        System.out.println("查詢結果: " + nav.findShortestPath("L1", "L999"));

        System.out.println("\n--- 邊界案例 2: 無法連通 (Unreachable) ---");
        System.out.println("查詢結果: " + nav.findShortestPath("L1", "L6"));
    }
}