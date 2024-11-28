public class Course {
    private String courseID;
    private String courseName;
    private String courseContent;
    private double courseDuration;
    private double coursePercentage;
    private String courseLevel;
    private String courseDetails;

    public Course (String courseID, String courseName,String courseDetails, String courseContent, double courseDuration, double coursePercentage, String courseLevel) {
        this.courseID = courseID;
        this.courseName = courseName;
        this.courseDetails = courseDetails;
        this.courseContent = courseContent;
        this.courseDuration = courseDuration;
        this.coursePercentage = coursePercentage;
        this.courseLevel = courseLevel;
    }

    public String getCourseID() {
        return courseID;
    }

    public String getCourseName() {
        return courseName;
    }

    public String getCourseDetails() {
        return courseDetails;
    }

    public String getCourseContent() {
        return courseContent;
    }

    public double getCourseDuration() {
        return courseDuration;
    }

    public double getCoursePercentage() {
        return coursePercentage;
    }

    public String getCourseLevel() {
        return courseLevel;
    }

    public void setCourseID(String courseID) {
        this.courseID = courseID;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public void setCourseDetails(String courseDetails) {
        this.courseDetails = courseDetails;
    }

    public void setCourseContent(String courseContent) {
        this.courseContent = courseContent;
    }

    public void setCourseDuration(double courseDuration) {
        this.courseDuration = courseDuration;
    }

    public void setCoursePercentage(double coursePercentage) {
        this.coursePercentage = coursePercentage;
    }

    public void setCourseLevel(String courseLevel) {
        this.courseLevel = courseLevel;
    }


    public void enrollCourse () {
        System.out.println("You have successfully enrolled in the " + courseName + " course!");
    }

    public void startCourse () {
        coursePercentage = 0.0;
        System.out.println("The course " + courseName + " has officially started!");
    }

    public void resumeCourse () {
        System.out.println("Resuming the course " + courseName + " from " + coursePercentage);
    }

    public void completeLesson (int lessonCompleted, int totalLessons) {
        coursePercentage = ((double) lessonCompleted / totalLessons ) * 100;
        System.out.println("You have successfully completed " + coursePercentage + "%" + " of the course!");
    }

    public void courseCompleted () {
        System.out.println("You have successfully completed the " + courseName + " course!");
    }

    public void showCourseDetails () {
        System.out.println("Course ID: " + courseID);
        System.out.println("Course Name: " + courseName);
        System.out.println("Course Details: " + courseDetails);
        System.out.println("Course Content: " + courseContent);
        System.out.println("Course Duration: " + courseDuration);
        System.out.println("Course Difficulty: " + courseLevel);
    }
    

    /*public void displayCourse () {
        System.out.println("-----------------WELCOME TO DUOMINGO!-----------------");
        System.out.println("");
        System.out.println("Course ID: " + courseID);
        System.out.println("Course Name: " + courseName);
        System.out.println("Course Details: " + courseDetails);
        System.out.println("Course Content: " + courseContent);
        System.out.println("Course Duration: " + courseDuration);
        System.out.println("Course Difficulty: " + courseLevel);
    }*/  
}