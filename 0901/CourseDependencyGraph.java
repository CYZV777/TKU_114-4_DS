import java.util.*;

public class CourseDependencyGraph {
    private Map<String, Set<String>> nextCourseMap;
    private Map<String, Set<String>> prereqMap;

    public CourseDependencyGraph() {
        nextCourseMap = new HashMap<>();
        prereqMap = new HashMap<>();
    }

    public void addCourse(String course) {
        nextCourseMap.putIfAbsent(course, new HashSet<>());
        prereqMap.putIfAbsent(course, new HashSet<>());
    }

    public void addDependency(String prereq, String course) {
        addCourse(prereq);
        addCourse(course);
        nextCourseMap.get(prereq).add(course);
        prereqMap.get(course).add(prereq);
    }

    public int getInDegree(String course) {
        return prereqMap.getOrDefault(course, Collections.emptySet()).size();
    }

    public int getOutDegree(String course) {
        return nextCourseMap.getOrDefault(course, Collections.emptySet()).size();
    }

    public void printSummary() {
        List<String> courses = new ArrayList<>(nextCourseMap.keySet());
        Collections.sort(courses);

        System.out.println("====== 課程相依關係報告 ======");
        for (String course : courses) {
            Set<String> prereqs = prereqMap.get(course);
            Set<String> nextCourses = nextCourseMap.get(course);

            System.out.printf("課程: %-10s | In-degree: %d | Out-degree: %d%n",
                    course, getInDegree(course), getOutDegree(course));
            System.out.println("  - 前置課程 (Prerequisites): " + prereqs);
            System.out.println("  - 後續課程 (Next Courses) : " + nextCourses);
        }
    }

    public static void main(String[] args) {
        CourseDependencyGraph graph = new CourseDependencyGraph();
        graph.addDependency("CS101", "CS102");
        graph.addDependency("CS101", "DS201");
        graph.addDependency("CS102", "ALGO301");
        graph.addDependency("DS201", "ALGO301");

        graph.printSummary();
    }
}