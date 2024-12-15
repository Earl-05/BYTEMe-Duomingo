package Game;

import javax.swing.*;

public class Game {

    public static void startGame(UserDetails userDetails) {
        CourseDetails currentCourse = getCurrentCourseDetails(userDetails.getCurrentCourse());

        if (currentCourse == null) {
            JOptionPane.showMessageDialog(null, "No course selected. Please select a course first.");
            return;
        }

        boolean keepPlaying = true;
        int totalScore = 0;

        while (keepPlaying) {

            String[] gameOptions = {
                "Word Association",
                "Scramble Word Game",
                "Reading",
                "Exit"
            };

            int gameChoice = JOptionPane.showOptionDialog(null,
                    "Welcome to the Language Learning Game!\n" +
                            "Course: " + currentCourse.getCourseName() + "\n" +
                            "Description: " + currentCourse.getCourseDescription() + "\n" +
                            "Difficulty: " + currentCourse.getDifficulty() + "\n\n" +
                            "Select a game to play:",
                    "Game Selection",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE,
                    null, gameOptions, gameOptions[0]);

            switch (gameChoice) {
                case 0 -> {
                    int difficulty = chooseDifficulty(currentCourse.getDifficulty());
                    int result = new WordAssociation(difficulty, currentCourse.getCourseName(), userDetails).playGame();
                    if (result == -1) {
                        continue;
                    }
                    totalScore += result;
                }
                case 1 -> {
                    int difficulty = chooseDifficulty(currentCourse.getDifficulty());
                    int result = new ScrambleWordGame(difficulty, currentCourse.getCourseName()).playGame();
                    if (result == -1) {
                        continue;
                    }
                    totalScore += result;
                }
                case 2 -> {
                    int difficulty = chooseDifficulty(currentCourse.getDifficulty());
                    int result = new Reading(difficulty, currentCourse.getCourseName()).playGame();
                    if (result == -1) {
                        continue;
                    }
                    totalScore += result;
                }
                case 3, -1 -> {
                    JOptionPane.showMessageDialog(null, "Thank you for playing! Your total score is: " + totalScore);
                    keepPlaying = false;
                    User.welcomeUser(userDetails);
                }
                default -> JOptionPane.showMessageDialog(null, "Invalid choice, please try again.");
            }
        }
    }

    private static CourseDetails getCurrentCourseDetails(String courseName) {
        CourseDetails[] courses = CourseDatabase.getCourses();
        for (CourseDetails course : courses) {
            if (course.getCourseName().equalsIgnoreCase(courseName)) {
                return course;
            }
        }
        return null;
    }

    private static int chooseDifficulty(String currentDifficulty) {
        String[] difficultyOptions = {"Beginner", "Intermediate"};
        int difficultyChoice = JOptionPane.showOptionDialog(null,
                "Select Difficulty Level (Current: " + currentDifficulty + "):",
                "Difficulty Selection",
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE,
                null, difficultyOptions, difficultyOptions[0]);

        return difficultyChoice == -1 ? 0 : difficultyChoice;
    }
}