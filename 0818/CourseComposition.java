class Instructor {
    private String id;
    private String name;

    public Instructor(String id, String name) {
        if (id == null || id.trim().isEmpty()) {
            this.id = "Unknown";
        } else {
            this.id = id;
        }

        if (name == null || name.trim().isEmpty()) {
            this.name = "Unknown";
        } else {
            this.name = name;
        }
    }

    public String getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }
}

class Course {
    private String courseCode;
    private String title;
    private Instructor instructor;

    public Course(String courseCode, String title, Instructor instructor) {
        if (courseCode == null || courseCode.trim().isEmpty()) {
            this.courseCode = "Unknown";
        } else {
            this.courseCode = courseCode;
        }

        if (title == null || title.trim().isEmpty()) {
            this.title = "Unknown";
        } else {
            this.title = title;
        }

        this.instructor = instructor;
    }

    public String summary() {
        String instructorInfo = (this.instructor != null) 
            ? this.instructor.getName() + " (" + this.instructor.getId() + ")" 
            : "未指定授課者";
        return "課程代碼: " + this.courseCode + ", 課程名稱: " + this.title + ", 授課者: " + instructorInfo;
    }
}

public class CourseComposition {
    public static void main(String[] args) {
        Instructor teacher = new Instructor("T001", "培宇教授");

        Course course1 = new Course("CS101", "資料結構", teacher);
        Course course2 = new Course("CS102", "系統分析", teacher);

        System.out.println(course1.summary());
        System.out.println(course2.summary());
    }
}