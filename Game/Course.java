package Game;

import javax.swing.*;

public class Course {
    public static void displayCourse(User user) {
        CourseDetails currentCourse = getCurrentCourseDetails(user.getCurrentCourse());
        if (currentCourse != null) {
            boolean continueCourse = true;
            while (continueCourse) {
                // Adjust the difficulty level based on the user's main language
                String adjustedDifficulty = adjustDifficulty(currentCourse.getDifficulty(), currentCourse.getLanguages(), user.getMainLanguage());

                String[] options = {
                    "Start Course",
                    "Resume Course",
                    "Course Achievement",
                    "Change Course",
                    "Exit"
                };

                int choice = JOptionPane.showOptionDialog(null,
                        "Selected Course: " + currentCourse.getCourseName() + "\n" +
                        "Difficulty: " + adjustedDifficulty + "\n" +
                        "Description: " + currentCourse.getCourseDescription(),
                        "Course Management",
                        JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE,
                        null, options, options[0]);

                switch (choice) {
                    case 0:
                        JOptionPane.showMessageDialog(null, "Course starting...");
                        break;
                    case 1:
                        JOptionPane.showMessageDialog(null, "Resuming course...");
                        break;
                    case 2:
                        JOptionPane.showMessageDialog(null, "Achievements functionality is under construction.");
                        break;
                    case 3:
                        changeCourse(user);
                        break;
                    case 4:
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
        CourseDetails[] courses = CourseDatabase.getCourses();
        for (CourseDetails course : courses) {
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

    static void selectCourse(User user) {
        CourseDetails[] courses = CourseDatabase.getCourses();
        String[] courseOptions = new String[courses.length];
        for (int i = 0; i < courses.length; i++) {
            courseOptions[i] = courses[i].getCourseName();
        }
        String selectedCourse = (String) JOptionPane.showInputDialog(null,
                "Select a course",
                "Course Selection",
                JOptionPane.QUESTION_MESSAGE,
                null, courseOptions, courseOptions[0]);
        if (selectedCourse != null) {
            user.setCurrentCourse(selectedCourse);
            JOptionPane.showMessageDialog(null, "Course selected successfully!");
        }
    }

    private static String adjustDifficulty(String defaultDifficulty, String[] courseLanguages, String userMainLanguage) {
        for (String language : courseLanguages) {
            if (language.equalsIgnoreCase(userMainLanguage)) {
                return defaultDifficulty; // No change if the user's language matches
            }
        }
        // Increase difficulty for non-matching languages
        switch (defaultDifficulty.toLowerCase()) {
            case "easy":
                return "Intermediate";
            case "intermediate":
                return "Hard";
            case "hard":
                return "Very Hard";
            default:
                return "Unknown";
        }
    }
}
