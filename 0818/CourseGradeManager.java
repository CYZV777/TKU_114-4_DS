class CourseGrade {
    private String studentId;
    private String name;
    private double dailyScore;
    private double midtermScore;
    private double finalScore;
    private double attendanceScore;

    public CourseGrade(String studentId, String name, double dailyScore, double midtermScore, double finalScore, double attendanceScore) {
        this.studentId = (studentId == null || studentId.isEmpty()) ? "Unknown" : studentId;
        this.name = (name == null || name.isEmpty()) ? "Unknown" : name;
        this.dailyScore = clampScore(dailyScore);
        this.midtermScore = clampScore(midtermScore);
        this.finalScore = clampScore(finalScore);
        this.attendanceScore = clampScore(attendanceScore);
    }

    private double clampScore(double score) {
        if (score < 0) return 0;
        if (score > 100) return 100;
        return score;
    }

    public String getStudentId() {
        return this.studentId;
    }

    public String getName() {
        return this.name;
    }

    public double calculateFinalScore() {
        return (this.dailyScore * 0.5) + (this.midtermScore * 0.2) + (this.finalScore * 0.2) + (this.attendanceScore * 0.1);
    }

    public char getLevel() {
        double score = calculateFinalScore();
        if (score >= 90) return 'A';
        if (score >= 80) return 'B';
        if (score >= 70) return 'C';
        if (score >= 60) return 'D';
        return 'F';
    }

    @Override
    public String toString() {
        return "學號: " + studentId + ", 姓名: " + name + ", 總分: " + String.format("%.2f", calculateFinalScore()) + ", 等第: " + getLevel();
    }
}

public class CourseGradeManager {
    public static void main(String[] args) {
        CourseGrade[] grades = {
            new CourseGrade("S001", "張三", 85, 78, 90, 95),
            new CourseGrade("S002", "李四", 92, 88, 95, 100),
            new CourseGrade("S003", "王五", 50, 45, 60, 70),
            new CourseGrade("S004", "趙六", 70, 65, 80, 85),
            new CourseGrade("S005", "孫七", 40, 30, 50, 60)
        };

        System.out.println("=== 所有學生成績 ===");
        double totalSum = 0;
        CourseGrade topStudent = grades[0];

        for (CourseGrade grade : grades) {
            System.out.println(grade);
            double currentScore = grade.calculateFinalScore();
            totalSum += currentScore;
            if (currentScore > topStudent.calculateFinalScore()) {
                topStudent = grade;
            }
        }

        System.out.println("\n=== 班級平均成績 ===");
        System.out.printf("平均分: %.2f\n", (totalSum / grades.length));

        System.out.println("\n=== 最高分學生 ===");
        System.out.println(topStudent);

        System.out.println("\n=== 不及格名單 (總分 < 60) ===");
        for (CourseGrade grade : grades) {
            if (grade.calculateFinalScore() < 60) {
                System.out.println(grade.getName() + " (" + grade.getStudentId() + ") - 總分: " + String.format("%.2f", grade.calculateFinalScore()));
            }
        }
    }
}