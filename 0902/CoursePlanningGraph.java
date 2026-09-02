import java.util.*;

public class CoursePlanningGraph {

    private Map<String, List<String>> adjList = new HashMap<>();

    public void addCourse(String course) {
        adjList.putIfAbsent(course, new ArrayList<>());
    }

    public void addPrerequisite(String prereq, String nextCourse) {
        addCourse(prereq);
        addCourse(nextCourse);
        adjList.get(prereq).add(nextCourse);
    }

    public Set<String> getAffectedCourses(String course) {
        Set<String> affected = new LinkedHashSet<>();
        if (!adjList.containsKey(course)) {
            return affected;
        }

        dfs(course, affected);
        affected.remove(course);
        return affected;
    }

    private void dfs(String curr, Set<String> visited) {
        visited.add(curr);
        for (String next : adjList.getOrDefault(curr, Collections.emptyList())) {
            if (!visited.contains(next)) {
                dfs(next, visited);
            }
        }
    }

    public boolean isDependent(String prereq, String target) {
        if (!adjList.containsKey(prereq) || !adjList.containsKey(target)) return false;
        Set<String> visited = new HashSet<>();
        dfs(prereq, visited);
        return visited.contains(target);
    }

    public static void main(String[] args) {
        CoursePlanningGraph planner = new CoursePlanningGraph();

        planner.addPrerequisite("微積分", "工程數學");
        planner.addPrerequisite("程式設計", "資料結構");
        planner.addPrerequisite("資料結構", "演算法");
        planner.addPrerequisite("資料結構", "資料庫系統");
        planner.addPrerequisite("演算法", "機器學習");
        planner.addCourse("體育");

        System.out.println("--- 影響評估 (若未修過「程式設計」) ---");
        Set<String> affected = planner.getAffectedCourses("程式設計");
        System.out.println("受影響的所有後續課程: " + affected);

        System.out.println("\n--- 可達性判斷 ---");
        System.out.println("資料結構 -> 機器學習 (依賴): " + planner.isDependent("資料結構", "機器學習"));
        System.out.println("工程數學 -> 演算法 (依賴): " + planner.isDependent("工程數學", "演算法"));

        System.out.println("\n--- 邊界案例 ---");
        System.out.println("不存在課程影響清單: " + planner.getAffectedCourses("量子物理"));
        System.out.println("無後續依賴的課程: " + planner.getAffectedCourses("體育"));
    }
}