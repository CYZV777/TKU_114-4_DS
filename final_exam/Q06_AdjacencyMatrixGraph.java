import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Q06_AdjacencyMatrixGraph {
    private final List<String> vertexList;
    private final Map<String, Integer> vertexIndexMap;
    private final boolean[][] matrix;

    public Q06_AdjacencyMatrixGraph(List<String> vertices) {
        this.vertexList = new ArrayList<>();
        this.vertexIndexMap = new HashMap<>();

        if (vertices != null) {
            for (String v : vertices) {
                if (v != null && !vertexIndexMap.containsKey(v)) {
                    vertexIndexMap.put(v, vertexList.size());
                    vertexList.add(v);
                }
            }
        }
        int n = vertexList.size();
        this.matrix = new boolean[n][n];
    }

    public boolean addEdge(String first, String second) {
        if (first == null || second == null || first.equals(second)) {
            return false;
        }
        Integer i = vertexIndexMap.get(first);
        Integer j = vertexIndexMap.get(second);
        if (i == null || j == null) {
            return false;
        }
        if (matrix[i][j]) {
            return false;
        }

        matrix[i][j] = true;
        matrix[j][i] = true;
        return true;
    }

    public boolean removeEdge(String first, String second) {
        if (first == null || second == null) {
            return false;
        }
        Integer i = vertexIndexMap.get(first);
        Integer j = vertexIndexMap.get(second);
        if (i == null || j == null || !matrix[i][j]) {
            return false;
        }

        matrix[i][j] = false;
        matrix[j][i] = false;
        return true;
    }

    public boolean hasEdge(String first, String second) {
        if (first == null || second == null) {
            return false;
        }
        Integer i = vertexIndexMap.get(first);
        Integer j = vertexIndexMap.get(second);
        if (i == null || j == null) {
            return false;
        }
        return matrix[i][j];
    }

    public int degree(String vertex) {
        if (vertex == null) {
            return 0;
        }
        Integer i = vertexIndexMap.get(vertex);
        if (i == null) {
            return 0;
        }
        int count = 0;
        for (int j = 0; j < vertexList.size(); j++) {
            if (matrix[i][j]) {
                count++;
            }
        }
        return count;
    }

    public List<String> neighbors(String vertex) {
        if (vertex == null) {
            return Collections.emptyList();
        }
        Integer i = vertexIndexMap.get(vertex);
        if (i == null) {
            return Collections.emptyList();
        }
        List<String> result = new ArrayList<>();
        for (int j = 0; j < vertexList.size(); j++) {
            if (matrix[i][j]) {
                result.add(vertexList.get(j));
            }
        }
        return result;
    }

    public static void main(String[] args) {
        List<String> vertices = List.of("A", "B", "C", "D");
        Q06_AdjacencyMatrixGraph graph = new Q06_AdjacencyMatrixGraph(vertices);

        System.out.println("加邊 A-B: " + graph.addEdge("A", "B")); // true
        System.out.println("重複加邊 A-B: " + graph.addEdge("A", "B")); // false
        System.out.println("反向重複加邊 B-A: " + graph.addEdge("B", "A")); // false
        System.out.println("加自環 A-A: " + graph.addEdge("A", "A")); // false
        System.out.println("加不存在的頂點 A-Z: " + graph.addEdge("A", "Z")); // false

        graph.addEdge("A", "C");
        System.out.println("A 的 degree: " + graph.degree("A")); // 預期: 2
        System.out.println("A 的 neighbors: " + graph.neighbors("A")); // 預期: [B, C]

        System.out.println("查詢未知頂點 degree: " + graph.degree("Unknown")); // 預期: 0
        System.out.println("查詢未知頂點 neighbors: " + graph.neighbors("Unknown")); // 預期: []
        System.out.println("是否有邊 A-Z: " + graph.hasEdge("A", "Z")); // 預期: false

        System.out.println("刪除邊 A-B: " + graph.removeEdge("A", "B")); // true
        System.out.println("檢查邊 B-A: " + graph.hasEdge("B", "A")); // 預期: false
    }
}