package Game;

import javax.swing.JOptionPane;

public class CourseDetails {
    private String courseID;
    private String courseName;
    private String courseContent;
    private double courseDuration;
    private double coursePercentage;
    private String courseLevel;

    public CourseDetails(String courseID, String courseName, String courseContent, double courseDuration, double coursePercentage, String courseLevel) {
        this.courseID = courseID;
        this.courseName = courseName;
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

    public void enrollCourse() {
        JOptionPane.showMessageDialog(null, "You have successfully enrolled in the " + courseName + " course!");
    }

    public void startCourse() {
        coursePercentage = 0.0;
        JOptionPane.showMessageDialog(null, "The course " + courseName + " has officially started!");
    }

    public void resumeCourse() {
        JOptionPane.showMessageDialog(null, "Resuming the course " + courseName + " from " + coursePercentage + "%");
    }

    public void completeLesson(int lessonCompleted, int totalLessons) {
        coursePercentage = ((double) lessonCompleted / totalLessons) * 100;
        JOptionPane.showMessageDialog(null, "You have successfully completed " + coursePercentage + "%" + " of the course!");
    }

    public void courseCompleted () {
        System.out.println("You successfully completed");

    }
}