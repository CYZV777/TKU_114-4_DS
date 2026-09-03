import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

public class Q12_CampusDispatchSystem {

    public record Request(String id, String location, int priority, long sequence) {}

    private final Map<String, List<String>> roads = new HashMap<>();

    private final Map<String, Request> requestsById = new HashMap<>();

    private final PriorityQueue<Request> pq = new PriorityQueue<>(
            Comparator.comparingInt(Request::priority)
                    .thenComparingLong(Request::sequence)
    );

    public boolean addLocation(String location) {
        if (location == null || roads.containsKey(location)) {
            return false;
        }
        roads.put(location, new ArrayList<>());
        return true;
    }

    public boolean addRoad(String first, String second) {
        if (first == null || second == null || first.equals(second)) {
            return false;
        }
        if (!roads.containsKey(first) || !roads.containsKey(second)) {
            return false;
        }
        List<String> neighborsFirst = roads.get(first);
        if (neighborsFirst.contains(second)) {
            return false;
        }

        neighborsFirst.add(second);
        roads.get(second).add(first);
        return true;
    }

    public boolean submit(Request request) {
        if (request == null || request.id() == null || request.location() == null) {
            return false;
        }
        if (!roads.containsKey(request.location())) {
            return false;
        }
        if (requestsById.containsKey(request.id())) {
            return false;
        }

        requestsById.put(request.id(), request);
        pq.offer(request);
        return true;
    }

    public Request nextReachable(String serviceCenter) {
        if (serviceCenter == null || !roads.containsKey(serviceCenter) || pq.isEmpty()) {
            return null;
        }

        Set<String> reachableLocations = getReachableLocations(serviceCenter);

        List<Request> unreachableList = new ArrayList<>();
        Request matched = null;

        while (!pq.isEmpty()) {
            Request candidate = pq.poll();
            if (reachableLocations.contains(candidate.location())) {
                matched = candidate;
                break;
            } else {
                unreachableList.add(candidate);
            }
        }

        for (Request unreached : unreachableList) {
            pq.offer(unreached);
        }

        if (matched != null) {
            requestsById.remove(matched.id());
        }

        return matched;
    }

    private Set<String> getReachableLocations(String start) {
        Set<String> visited = new HashSet<>();
        Queue<String> queue = new ArrayDeque<>();

        visited.add(start);
        queue.offer(start);

        while (!queue.isEmpty()) {
            String curr = queue.poll();
            List<String> neighbors = roads.get(curr);
            if (neighbors != null) {
                for (String neighbor : neighbors) {
                    if (neighbor != null && !visited.contains(neighbor)) {
                        visited.add(neighbor);
                        queue.offer(neighbor);
                    }
                }
            }
        }
        return visited;
    }

    public List<String> route(String start, String target) {
        if (start == null || target == null) {
            return new ArrayList<>();
        }
        if (!roads.containsKey(start) || !roads.containsKey(target)) {
            return new ArrayList<>();
        }
        if (start.equals(target)) {
            return new ArrayList<>(List.of(start));
        }

        Map<String, String> predecessor = new HashMap<>();
        Queue<String> queue = new ArrayDeque<>();

        queue.offer(start);
        predecessor.put(start, null);

        boolean reached = false;
        while (!queue.isEmpty()) {
            String curr = queue.poll();
            if (curr.equals(target)) {
                reached = true;
                break;
            }

            for (String neighbor : roads.get(curr)) {
                if (!predecessor.containsKey(neighbor)) {
                    predecessor.put(neighbor, curr);
                    queue.offer(neighbor);
                }
            }
        }

        if (!reached) {
            return new ArrayList<>();
        }

        List<String> path = new ArrayList<>();
        String step = target;
        while (step != null) {
            path.add(step);
            step = predecessor.get(step);
        }
        Collections.reverse(path);
        return path;
    }

    public int pendingCount() {
        return pq.size();
    }

    public static void main(String[] args) {
        Q12_CampusDispatchSystem system = new Q12_CampusDispatchSystem();

        system.addLocation("A");
        system.addLocation("B");
        system.addLocation("C");
        system.addLocation("D");

        system.addRoad("A", "B");
        system.addRoad("B", "C");

        system.submit(new Request("REQ1", "D", 1, 100L));
        system.submit(new Request("REQ2", "C", 2, 200L));
        System.out.println("重複提交 REQ1: " + system.submit(new Request("REQ1", "B", 1, 300L)));

        System.out.println("待處理請求數: " + system.pendingCount());

        Request dispatched = system.nextReachable("A");
        System.out.println("派送結果: " + (dispatched != null ? dispatched.id() : null));
        System.out.println("派送後待處理請求數 (REQ1 應被保留): " + system.pendingCount());

        System.out.println("A 到 C 路徑: " + system.route("A", "C"));
        System.out.println("A 到 D 路徑 (斷路): " + system.route("A", "D"));
    }
}