import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Q05_StudentHashIndex {

    private final Map<String, Set<String>> studentToCourses = new HashMap<>();
    private final Map<String, Set<String>> courseToStudents = new HashMap<>();
    private int totalEnrollments = 0;

    private String normalize(String s) {
        if (s == null || s.isBlank()) {
            return null;
        }
        return s.trim().toUpperCase();
    }

    public boolean enroll(String studentId, String courseId) {
        String sId = normalize(studentId);
        String cId = normalize(courseId);

        if (sId == null || cId == null) {
            return false;
        }

        Set<String> courses = studentToCourses.computeIfAbsent(sId, k -> new HashSet<>());
        if (courses.contains(cId)) {
            return false;
        }

        courses.add(cId);
        courseToStudents.computeIfAbsent(cId, k -> new HashSet<>()).add(sId);
        totalEnrollments++;
        return true;
    }

    public boolean drop(String studentId, String courseId) {
        String sId = normalize(studentId);
        String cId = normalize(courseId);

        if (sId == null || cId == null) {
            return false;
        }

        Set<String> courses = studentToCourses.get(sId);
        if (courses == null || !courses.contains(cId)) {
            return false;
        }

        courses.remove(cId);
        if (courses.isEmpty()) {
            studentToCourses.remove(sId);
        }

        Set<String> students = courseToStudents.get(cId);
        if (students != null) {
            students.remove(sId);
            if (students.isEmpty()) {
                courseToStudents.remove(cId);
            }
        }

        totalEnrollments--;
        return true;
    }

    public Set<String> coursesOf(String studentId) {
        String sId = normalize(studentId);
        if (sId == null || !studentToCourses.containsKey(sId)) {
            return Collections.emptySet();
        }
        return Collections.unmodifiableSet(new HashSet<>(studentToCourses.get(sId)));
    }

    public Set<String> studentsIn(String courseId) {
        String cId = normalize(courseId);
        if (cId == null || !courseToStudents.containsKey(cId)) {
            return Collections.emptySet();
        }
        return Collections.unmodifiableSet(new HashSet<>(courseToStudents.get(cId)));
    }

    public int enrollmentCount() {
        return totalEnrollments;
    }

    public static void main(String[] args) {
        Q05_StudentHashIndex index = new Q05_StudentHashIndex();

        System.out.println("加選成功: " + index.enroll(" s001 ", " cs101 "));
        System.out.println("加選成功: " + index.enroll("S001", "CS102"));
        System.out.println("重複選課: " + index.enroll("S001", "CS101"));
        System.out.println("無效輸入: " + index.enroll(" ", "CS101"));

        System.out.println("S001 的課程: " + index.coursesOf("s001"));
        System.out.println("CS101 的學生: " + index.studentsIn("cs101"));
        System.out.println("總選課人次: " + index.enrollmentCount());

        System.out.println("退選 CS101: " + index.drop("S001", "CS101"));
        System.out.println("退選 CS102: " + index.drop("S001", "CS102"));
        System.out.println("退選後的課程: " + index.coursesOf("S001"));
        System.out.println("總選課人次: " + index.enrollmentCount());
    }
}