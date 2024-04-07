package darshan.prajapati.n01584247.dp;
import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Course {
    private String courseId;
    private String courseName;
    private String courseDescription;

    public Course() {
        //this constructor is required
    }

    public Course(String courseId, String courseName, String courseDescription) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.courseDescription = courseDescription;
    }

    public String getCourseId() {
        return courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public String getCourseDescription() {
        return courseDescription;
    }
}
