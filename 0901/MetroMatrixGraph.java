import java.util.*;

public class MetroMatrixGraph {
    private int[][] matrix;
    private int numStations;
    private int edgeCount;
    private String[] stationNames;

    public MetroMatrixGraph(String[] stations) {
        this.stationNames = stations;
        this.numStations = stations.length;
        this.matrix = new int[numStations][numStations];
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

    public List<String> getNeighbors(int v) {
        List<String> neighbors = new ArrayList<>();
        if (isValid(v)) {
            for (int i = 0; i < numStations; i++) {
                if (matrix[v][i] == 1) {
                    neighbors.add(stationNames[i]);
                }
            }
        }
        return neighbors;
    }

    public int getDegree(int v) {
        if (!isValid(v)) return 0;
        int degree = 0;
        for (int i = 0; i < numStations; i++) {
            degree += matrix[v][i];
        }
        return degree;
    }

    public int getEdgeCount() {
        return edgeCount;
    }

    public void printMatrixReport() {
        System.out.println("====== 捷運網路矩陣報告 (Edge Count: " + edgeCount + ") ======");
        System.out.print(String.format("%-10s", ""));
        for (String s : stationNames) {
            System.out.print(String.format("%-10s", s));
        }
        System.out.println();

        for (int i = 0; i < numStations; i++) {
            System.out.print(String.format("%-10s", stationNames[i]));
            for (int j = 0; j < numStations; j++) {
                System.out.print(String.format("%-10d", matrix[i][j]));
            }
            System.out.println(" | Degree: " + getDegree(i) + " | 鄰站: " + getNeighbors(i));
        }
        System.out.println("==================================================");
    }

    private boolean isValid(int v) {
        return v >= 0 && v < numStations;
    }

    public static void main(String[] args) {
        String[] stations = {"A", "B", "C", "D"};
        MetroMatrixGraph metro = new MetroMatrixGraph(stations);
        
        metro.addEdge(0, 1);
        metro.addEdge(0, 2);
        metro.addEdge(1, 3);
        metro.addEdge(2, 3);

        metro.printMatrixReport();
    }
}