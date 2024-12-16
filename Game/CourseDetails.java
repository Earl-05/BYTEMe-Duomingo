package Game;

public class CourseDetails {
    private String courseCode;
    private String courseName;
    private String courseDescription;
    private String difficulty;
    private String[] languages;
    
    public CourseDetails() {
    	
    }

    public CourseDetails(String courseCode, String courseName, String courseDescription, String difficulty, String[] languages) {
        this.courseCode = courseCode;
        this.courseName = courseName;
        this.courseDescription = courseDescription;
        this.difficulty = difficulty;
        this.languages = languages;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public String getCourseName() {
        return courseName;
    }

    public String getCourseDescription() {
        return courseDescription;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public String[] getLanguages() {
        return languages;
    }
}
