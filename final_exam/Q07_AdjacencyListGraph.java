import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Q07_AdjacencyListGraph {

    private final Map<String, Set<String>> adj = new HashMap<>();
    private int edgeCount = 0;

    public boolean addVertex(String vertex) {
        if (vertex == null || adj.containsKey(vertex)) {
            return false;
        }
        adj.put(vertex, new LinkedHashSet<>());
        return true;
    }

    public boolean addEdge(String from, String to) {
        if (from == null || to == null || from.equals(to)) {
            return false;
        }
        if (!adj.containsKey(from) || !adj.containsKey(to)) {
            return false;
        }

        Set<String> neighbors = adj.get(from);
        if (neighbors.contains(to)) {
            return false;
        }

        neighbors.add(to);
        edgeCount++;
        return true;
    }

    public boolean removeEdge(String from, String to) {
        if (from == null || to == null) {
            return false;
        }
        if (!adj.containsKey(from) || !adj.containsKey(to)) {
            return false;
        }

        if (adj.get(from).remove(to)) {
            edgeCount--;
            return true;
        }
        return false;
    }

    public List<String> outgoing(String vertex) {
        if (vertex == null || !adj.containsKey(vertex)) {
            return Collections.emptyList();
        }
        return new ArrayList<>(adj.get(vertex));
    }

    public int inDegree(String vertex) {
        if (vertex == null || !adj.containsKey(vertex)) {
            return 0;
        }

        int count = 0;
        for (Set<String> neighbors : adj.values()) {
            if (neighbors.contains(vertex)) {
                count++;
            }
        }
        return count;
    }

    public int edgeCount() {
        return edgeCount;
    }

    public static void main(String[] args) {
        Q07_AdjacencyListGraph g = new Q07_AdjacencyListGraph();

        System.out.println("加頂點 A: " + g.addVertex("A"));
        System.out.println("加頂點 B: " + g.addVertex("B"));
        System.out.println("加頂點 C: " + g.addVertex("C"));
        System.out.println("重複加 A: " + g.addVertex("A"));

        System.out.println("加邊 A -> B: " + g.addEdge("A", "B"));
        System.out.println("加邊 A -> C: " + g.addEdge("A", "C"));
        System.out.println("加重複邊 A -> B: " + g.addEdge("A", "B"));
        System.out.println("加自環 A -> A: " + g.addEdge("A", "A"));
        System.out.println("加不存在的頂點 A -> Z: " + g.addEdge("A", "Z"));

        System.out.println("A 的 outgoing (依序 B, C): " + g.outgoing("A"));
        System.out.println("目前邊數量: " + g.edgeCount());

        System.out.println("B 的 inDegree: " + g.inDegree("B"));
        System.out.println("A 的 inDegree: " + g.inDegree("A"));

        System.out.println("未知節點 outgoing: " + g.outgoing("Z"));
        System.out.println("未知節點 inDegree: " + g.inDegree("Z"));

        System.out.println("刪除邊 A -> B: " + g.removeEdge("A", "B"));
        System.out.println("刪除後邊數量: " + g.edgeCount());
    }
}