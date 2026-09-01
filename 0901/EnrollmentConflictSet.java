import java.util.*;

public class EnrollmentConflictSet {
    public static class EnrollmentRecord {
        String studentId;
        String courseId;

        public EnrollmentRecord(String studentId, String courseId) {
            this.studentId = studentId;
            this.courseId = courseId;
        }

        public String getCompositeKey() {
            return studentId + "#" + courseId;
        }

        @Override
        public String toString() {
            return "(" + studentId + ", " + courseId + ")";
        }
    }

    public static void processEnrollments(List<EnrollmentRecord> rawRecords) {
        Set<String> seenEnrollments = new HashSet<>();
        List<EnrollmentRecord> duplicateRecords = new ArrayList<>();

        Map<String, Set<String>> studentCourses = new HashMap<>();
        Map<String, Set<String>> courseStudents = new HashMap<>();

        for (EnrollmentRecord record : rawRecords) {
            String compositeKey = record.getCompositeKey();

            if (!seenEnrollments.add(compositeKey)) {
                duplicateRecords.add(record);
            } else {
                studentCourses.putIfAbsent(record.studentId, new HashSet<>());
                studentCourses.get(record.studentId).add(record.courseId);

                courseStudents.putIfAbsent(record.courseId, new HashSet<>());
                courseStudents.get(record.courseId).add(record.studentId);
            }
        }

        System.out.println("========== 選課檢查與統計報告 ==========");
        System.out.println("1. 重複選課異常紀錄:");
        if (duplicateRecords.isEmpty()) {
            System.out.println("   無重複紀錄");
        } else {
            for (EnrollmentRecord dup : duplicateRecords) {
                System.out.println("   [重複] 學生 " + dup.studentId + " 重複選修 " + dup.courseId);
            }
        }

        System.out.println("\n2. 每人已選課程清單:");
        for (Map.Entry<String, Set<String>> entry : studentCourses.entrySet()) {
            System.out.printf("   學生 %-8s 已修 %d 門課: %s%n", entry.getKey(), entry.getValue().size(), entry.getValue());
        }

        System.out.println("\n3. 每門課實際修課人數:");
        for (Map.Entry<String, Set<String>> entry : courseStudents.entrySet()) {
            System.out.printf("   課程 %-8s 修課人數: %2d 人 (名單: %s)%n", entry.getKey(), entry.getValue().size(), entry.getValue());
        }
        System.out.println("========================================");
    }

    public static void main(String[] args) {
        List<EnrollmentRecord> records = Arrays.asList(
            new EnrollmentRecord("S001", "CS101"),
            new EnrollmentRecord("S001", "MATH201"),
            new EnrollmentRecord("S002", "CS101"),
            new EnrollmentRecord("S001", "CS101"),
            new EnrollmentRecord("S003", "CS101"),
            new EnrollmentRecord("S002", "ENG102"),
            new EnrollmentRecord("S003", "CS101")
        );

        processEnrollments(records);
    }
}