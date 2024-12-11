package Game;

import javax.swing.*;


public class Course {
    public static void displayCourse(UserDetails userDetails) {
        CourseDetails currentCourse = getCurrentCourseDetails(userDetails.getCurrentCourse());
        if (currentCourse != null) {
            boolean continueCourse = true;
            while (continueCourse) {
                // Adjust the difficulty level based on the user's main language
                String adjustedDifficulty = adjustDifficulty(currentCourse.getDifficulty(), currentCourse.getLanguages(), userDetails.getMainLanguage());

                String[] options = {
                    "Start Course",
                    "Resume Course",
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
                
                if (choice==JOptionPane.CLOSED_OPTION) {
                	System.exit(0);
                }
                
                switch (choice) {
                    case 0:
                        Game.startGame(userDetails);
                        break;
                    case 1:
                        Game.startGame(userDetails);
                        break;
                    case 2:
                        changeCourse(userDetails);
                        break;
                    case 3:
                        User.welcomeUser(userDetails);
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

    private static void changeCourse(UserDetails userDetails) {
        int confirm = JOptionPane.showConfirmDialog(null, "Changing the course will lose all progress. Do you want to continue?");
        if (confirm == JOptionPane.YES_OPTION) {
            selectCourse(userDetails);
        }
    }

    static void selectCourse(UserDetails userDetails) {
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
            userDetails.setCurrentCourse(selectedCourse);
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
