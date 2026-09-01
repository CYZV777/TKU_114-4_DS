import java.util.*;

public class LogisticsWeightedGraph {
    private Map<String, Map<String, Integer>> adjList;

    public LogisticsWeightedGraph() {
        adjList = new HashMap<>();
    }

    public void addVertex(String vertex) {
        adjList.putIfAbsent(vertex, new HashMap<>());
    }

    public void setEdge(String from, String to, int weight) {
        if (weight < 0) {
            System.out.println("[錯誤] 拒絕負權重: " + weight);
            return;
        }
        if (!adjList.containsKey(from) || !adjList.containsKey(to)) {
            System.out.println("[錯誤] 起始或終點站點不存在: " + from + " -> " + to);
            return;
        }
        adjList.get(from).put(to, weight);
    }

    public void removeEdge(String from, String to) {
        if (adjList.containsKey(from)) {
            adjList.get(from).remove(to);
        }
    }

    public Integer getEdgeWeight(String from, String to) {
        if (adjList.containsKey(from) && adjList.get(from).containsKey(to)) {
            return adjList.get(from).get(to);
        }
        return null;
    }

    public void printGraph() {
        System.out.println("====== 物流成本網路報告 ======");
        for (Map.Entry<String, Map<String, Integer>> entry : adjList.entrySet()) {
            String from = entry.getKey();
            Map<String, Integer> targets = entry.getValue();
            System.out.println("站點 " + from + " 的配送路線: " + targets);
        }
        System.out.println("==============================");
    }

    public static void main(String[] args) {
        LogisticsWeightedGraph logistics = new LogisticsWeightedGraph();
        logistics.addVertex("Taipei");
        logistics.addVertex("Taichung");
        logistics.addVertex("Kaohsiung");

        logistics.setEdge("Taipei", "Taichung", 250);
        logistics.setEdge("Taichung", "Kaohsiung", 300);

        logistics.setEdge("Taipei", "Taichung", 200);

        logistics.setEdge("Taipei", "Kaohsiung", -50);

        logistics.setEdge("Taipei", "Hualien", 400);

        logistics.printGraph();

        System.out.println("查詢 Taipei -> Taichung 成本: " + logistics.getEdgeWeight("Taipei", "Taichung"));

        logistics.removeEdge("Taipei", "Taichung");
        System.out.println("移除後 Taipei -> Taichung 成本: " + logistics.getEdgeWeight("Taipei", "Taichung"));
    }
}