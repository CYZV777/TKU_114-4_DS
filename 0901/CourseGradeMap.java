import java.util.*;

public class CourseGradeMap {
    private Map<String, List<Integer>> courseMap;

    public CourseGradeMap() {
        courseMap = new HashMap<>();
    }

    public void addGrade(String courseId, int score) {
        courseMap.putIfAbsent(courseId, new ArrayList<>());
        courseMap.get(courseId).add(score);
    }

    public double getAverage(String courseId) {
        List<Integer> grades = courseMap.get(courseId);
        if (grades == null || grades.isEmpty()) return 0.0;
        
        int sum = 0;
        for (int score : grades) {
            sum += score;
        }
        return (double) sum / grades.size();
    }

    public int getMaxScore(String courseId) {
        List<Integer> grades = courseMap.get(courseId);
        if (grades == null || grades.isEmpty()) return -1;
        return Collections.max(grades);
    }

    public void printReport() {
        Map<String, List<Integer>> sortedMap = new TreeMap<>(courseMap);
        
        System.out.println("====== 成績統計報告 ======");
        for (Map.Entry<String, List<Integer>> entry : sortedMap.entrySet()) {
            String courseId = entry.getKey();
            List<Integer> grades = entry.getValue();
            System.out.printf("課號: %-8s | 成績: %-15s | 平均: %-6.2f | 最高分: %d%n",
                    courseId, grades, getAverage(courseId), getMaxScore(courseId));
        }
    }

    public static void main(String[] args) {
        CourseGradeMap gradeSystem = new CourseGradeMap();
        gradeSystem.addGrade("CS101", 85);
        gradeSystem.addGrade("CS101", 92);
        gradeSystem.addGrade("MATH201", 78);
        gradeSystem.addGrade("MATH201", 88);
        gradeSystem.addGrade("MATH201", 95);
        gradeSystem.addGrade("ENG102", 90);

        gradeSystem.printReport();
    }
}