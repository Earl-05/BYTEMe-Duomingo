package Game;

import javax.swing.*;

public class Course {
    public static void displayCourse(User user) {
        CourseDetails currentCourse = getCurrentCourseDetails(user.getCurrentCourse());

        if (currentCourse != null) {
            boolean continueCourse = true;
            while (continueCourse) {
                String[] options = {"Display Course", "Enroll Course", "Start Course", "Complete Lesson", "Resume Course", "Change Course", "Exit"};
                int choice = JOptionPane.showOptionDialog(null, "Select an option", "Course Management", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

                switch (choice) {
                    case 0:
                        currentCourse.displayCourse();
                        break;
                    case 1:
                        currentCourse.enrollCourse();
                        break;
                    case 2:
                        currentCourse.startCourse();
                        break;
                    case 3:
                        int lessonCompleted = Integer.parseInt(JOptionPane.showInputDialog("Enter lessons completed:"));
                        int totalLessons = Integer.parseInt(JOptionPane.showInputDialog("Enter total lessons:"));
                        currentCourse.completeLesson(lessonCompleted, totalLessons);
                        break;
                    case 4:
                        currentCourse.resumeCourse();
                        break;
                    case 5:
                        changeCourse(user);
                        break;
                    case 6:
                        continueCourse = false;
                        break;
                    default:
                        break;
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "No course selected. Please choose a course first.");
        }
    }

    private static CourseDetails getCurrentCourseDetails(String courseName) {
        for (CourseDetails course : CourseDatabase.getCourses()) {
            if (course.getCourseName().equals(courseName)) {
                return course;
            }
        }
        return null;
    }

    private static void changeCourse(User user) {
        int confirm = JOptionPane.showConfirmDialog(null, "Changing the course will lose all progress. Do you want to continue?");
        if (confirm == JOptionPane.YES_OPTION) {
            selectCourse(user);
        }
    }

    private static void selectCourse(User user) {
        CourseDetails[] courses = CourseDatabase.getCourses();
        String[] courseOptions = new String[courses.length];
        for (int i = 0; i < courses.length; i++) {
            courseOptions[i] = courses[i].getCourseName();
        }

        String selectedCourse = (String) JOptionPane.showInputDialog(null, "Select a course", "Course Selection", JOptionPane.QUESTION_MESSAGE, null, courseOptions, courseOptions[0]);

        if (selectedCourse != null) {
            user.setCurrentCourse(selectedCourse);
            JOptionPane.showMessageDialog(null, "Course selected successfully!");
        }
    }
}
