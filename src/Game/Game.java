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
        int sessionScore = 0; // Tracks the score for the current session.

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
                case 0 -> { // Word Association
                    int difficulty = chooseDifficulty(currentCourse.getDifficulty());
                    int result = new WordAssociation(difficulty, currentCourse.getCourseName(), userDetails).playGame();
                    if (result == -1) {
                        continue;
                    }
                    sessionScore += result; // Add session score.
                    UserDatabase.updateStats(userDetails.getUserID(), "WAPlayed", result);
                }
                case 1 -> { // Scramble Word Game
                    int difficulty = chooseDifficulty(currentCourse.getDifficulty());
                    int result = new ScrambleWordGame(difficulty, currentCourse.getCourseName()).playGame();
                    if (result == -1) {
                        continue;
                    }
                    sessionScore += result; // Add session score.
                    UserDatabase.updateStats(userDetails.getUserID(), "SWPlayed", result);
                }
                case 2 -> { // Reading
                    int difficulty = chooseDifficulty(currentCourse.getDifficulty());
                    int result = new Reading(difficulty, currentCourse.getCourseName()).playGame();
                    if (result == -1) {
                        continue;
                    }
                    sessionScore += result; // Add session score.
                    UserDatabase.updateStats(userDetails.getUserID(), "RPlayed", result);
                }
                case 3, -1 -> {
                    userDetails.setTotalScore(userDetails.getTotalScore() + sessionScore);
                    UserDatabase.updateUser(userDetails); 

                    JOptionPane.showMessageDialog(null, "Thank you for playing! Your total score for this session is: " + sessionScore
                            + "\nYour lifetime total score is now: " + userDetails.getTotalScore());

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
