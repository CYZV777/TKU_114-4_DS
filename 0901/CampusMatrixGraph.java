import java.util.*;

public class CampusMatrixGraph {
    private int[][] matrix;
    private int numVertices;
    private int edgeCount;

    public CampusMatrixGraph(int vertices) {
        this.numVertices = vertices;
        this.matrix = new int[vertices][vertices];
        this.edgeCount = 0;
    }

    public void addEdge(int u, int v) {
        if (isValid(u) && isValid(v) && u != v) {
            if (matrix[u][v] == 0) {
                matrix[u][v] = 1;
                matrix[v][u] = 1;
                edgeCount++;
            }
        }
    }

    public void removeEdge(int u, int v) {
        if (isValid(u) && isValid(v)) {
            if (matrix[u][v] == 1) {
                matrix[u][v] = 0;
                matrix[v][u] = 0;
                edgeCount--;
            }
        }
    }

    public int getDegree(int v) {
        if (!isValid(v)) return 0;
        int degree = 0;
        for (int i = 0; i < numVertices; i++) {
            degree += matrix[v][i];
        }
        return degree;
    }

    public List<Integer> getNeighbors(int v) {
        List<Integer> neighbors = new ArrayList<>();
        if (isValid(v)) {
            for (int i = 0; i < numVertices; i++) {
                if (matrix[v][i] == 1) {
                    neighbors.add(i);
                }
            }
        }
        return neighbors;
    }

    public int getEdgeCount() {
        return edgeCount;
    }

    private boolean isValid(int v) {
        return v >= 0 && v < numVertices;
    }

    public static void main(String[] args) {
        CampusMatrixGraph graph = new CampusMatrixGraph(5);
        graph.addEdge(0, 1);
        graph.addEdge(0, 2);
        graph.addEdge(0, 1);
        graph.addEdge(1, 3);

        System.out.println("總邊數: " + graph.getEdgeCount());
        System.out.println("節點 0 的 Degree: " + graph.getDegree(0)); // 應為 2
        System.out.println("節點 0 的 Neighbors: " + graph.getNeighbors(0)); // [1, 2]

        graph.removeEdge(0, 1);
        System.out.println("移除邊 (0, 1) 後的總邊數: " + graph.getEdgeCount()); // 應為 2
        System.out.println("節點 0 的 Neighbors: " + graph.getNeighbors(0)); // [2]
    }
}