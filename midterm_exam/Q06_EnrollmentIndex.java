import java.util.*;

public class Q06_EnrollmentIndex {
    private final Map<String, Set<String>> courseToStudents = new HashMap<>();

    public boolean enroll(String courseCode, String studentId) {
        if (courseCode == null || courseCode.isBlank() || studentId == null || studentId.isBlank()) {
            return false;
        }
        Set<String> students = courseToStudents.computeIfAbsent(courseCode, k -> new HashSet<>());
        return students.add(studentId);
    }

    public boolean drop(String courseCode, String studentId) {
        if (courseCode == null || courseCode.isBlank() || studentId == null || studentId.isBlank()) {
            return false;
        }
        Set<String> students = courseToStudents.get(courseCode);
        if (students == null || !students.contains(studentId)) {
            return false;
        }
        students.remove(studentId);
        if (students.isEmpty()) {
            courseToStudents.remove(courseCode);
        }
        return true;
    }

    public int courseSize(String courseCode) {
        Set<String> students = courseToStudents.get(courseCode);
        return students == null ? 0 : students.size();
    }

    public List<String> studentsOf(String courseCode) {
        Set<String> students = courseToStudents.get(courseCode);
        if (students == null) {
            return new ArrayList<>();
        }
        List<String> list = new ArrayList<>(students);
        Collections.sort(list);
        return list;
    }

    public List<String> coursesOf(String studentId) {
        if (studentId == null || studentId.isBlank()) {
            return new ArrayList<>();
        }
        List<String> list = new ArrayList<>();
        for (Map.Entry<String, Set<String>> entry : courseToStudents.entrySet()) {
            if (entry.getValue().contains(studentId)) {
                list.add(entry.getKey());
            }
        }
        Collections.sort(list);
        return list;
    }

    public Map<String, Integer> summary() {
        Map<String, Integer> sortedMap = new TreeMap<>();
        for (Map.Entry<String, Set<String>> entry : courseToStudents.entrySet()) {
            sortedMap.put(entry.getKey(), entry.getValue().size());
        }
        return sortedMap;
    }

    public static void main(String[] args) {
        Q06_EnrollmentIndex index = new Q06_EnrollmentIndex();
        index.enroll("DS", "S02");
        index.enroll("DS", "S01");
        index.enroll("JAVA", "S01");
        System.out.println(index.studentsOf("DS"));
        System.out.println(index.coursesOf("S01"));
        System.out.println(index.summary());
    }
}