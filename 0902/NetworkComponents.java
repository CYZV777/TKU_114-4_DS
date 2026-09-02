import java.util.*;

public class NetworkComponents {

    public static class ComponentReport {
        public List<List<String>> components = new ArrayList<>();
        public int count = 0;
        public List<String> largestComponent = new ArrayList<>();

        public void printReport() {
            System.out.println("連通分量總數 (Count): " + count);
            for (int i = 0; i < components.size(); i++) {
                System.out.println(" Component " + (i + 1) + " (大小 " + components.get(i).size() + "): " + components.get(i));
            }
            System.out.println("最大分量 (Largest Component): " + largestComponent);
        }
    }

    public static ComponentReport analyzeComponents(Map<String, List<String>> graph) {
        ComponentReport report = new ComponentReport();
        if (graph == null || graph.isEmpty()) {
            return report;
        }

        Set<String> visited = new HashSet<>();

        for (String node : graph.keySet()) {
            if (!visited.contains(node)) {
                List<String> comp = new ArrayList<>();
                Queue<String> queue = new LinkedList<>();
                queue.offer(node);
                visited.add(node);

                while (!queue.isEmpty()) {
                    String curr = queue.poll();
                    comp.add(curr);
                    for (String neighbor : graph.getOrDefault(curr, Collections.emptyList())) {
                        if (!visited.contains(neighbor)) {
                            visited.add(neighbor);
                            queue.offer(neighbor);
                        }
                    }
                }

                report.components.add(comp);
                report.count++;
                if (comp.size() > report.largestComponent.size()) {
                    report.largestComponent = comp;
                }
            }
        }
        return report;
    }

    public static void main(String[] args) {
        Map<String, List<String>> network = new HashMap<>();
        network.put("A", Arrays.asList("B"));
        network.put("B", Arrays.asList("A", "C"));
        network.put("C", Arrays.asList("B"));
        network.put("D", Arrays.asList("E"));
        network.put("E", Arrays.asList("D"));
        network.put("F", Collections.emptyList());

        System.out.println("--- 一般案例分析 ---");
        analyzeComponents(network).printReport();

        System.out.println("\n--- 邊界案例 (Empty Graph) ---");
        analyzeComponents(new HashMap<>()).printReport();
    }
}