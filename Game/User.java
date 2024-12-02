package Game;

import java.util.List;
import java.util.ArrayList;
import javax.swing.*;
import java.awt.*;

public class User {

    public static void main(String[] args) {
        while (true) {
            String[] options = {"Log In", "Sign Up", "Display All Users", "Delete User", "Exit"};
            int choice = JOptionPane.showOptionDialog(null, "WELCOME TO DUOMINGO, For Testing: User ID: 000, Password: 000", "Main Menu",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

            switch (choice) {
                case 0:
                    logIn();
                    break;
                case 1:
                    signUp();
                    break;
                case 2:
                    displayAllUsers();
                    break;
                case 3:
                    deleteUser();
                    break;
                case 4:
                    System.exit(0);
                    break;
                default:
                    JOptionPane.showMessageDialog(null, "Invalid option. Please try again.");
            }
        }
    }

    private static void logIn() {
        String userID = JOptionPane.showInputDialog("Enter User ID:");
        String password = JOptionPane.showInputDialog("Enter Password:");

        if (UserDatabase.validateUser(userID, password)) {
            UserDetails userDetails = UserDatabase.getUser(userID);
            if (userDetails != null) {
                welcomeUser(userDetails);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Invalid credentials. Please try again.");
        }
    }

    private static void signUp() {
        String userID = JOptionPane.showInputDialog("Enter User ID:");
        String userName = JOptionPane.showInputDialog("Enter User Name:");
        String password = JOptionPane.showInputDialog("Enter Password:");
        String email = JOptionPane.showInputDialog("Enter Email:");
        String birthday = JOptionPane.showInputDialog("Enter Birthday (YYYY-MM-DD):");
        String[] courses = {"Filipino", "English", "Spanish", "Japanese", "French", "Hiligaynon"};
        String selectedCourse = (String) JOptionPane.showInputDialog(null, "Select Course", "Course Selection",
                JOptionPane.QUESTION_MESSAGE, null, courses, courses[0]);

        String[] languages = {"English", "Filipino", "Spanish", "Japanese", "French", "Hiligaynon"};
        String mainLanguage = (String) JOptionPane.showInputDialog(null, "Select Your Main Language", "Main Language Selection",
                JOptionPane.QUESTION_MESSAGE, null, languages, languages[0]);

        // Initialize achievements as an empty list for a new user
        List<String> achievements = new ArrayList<>();

        // Create a new User with all fields
        UserDetails newUser = new UserDetails(userID, userName, password, email, birthday, selectedCourse, achievements, mainLanguage, 0, 0, 0, 0);
        UserDatabase.addUser(newUser);
    }

    static void welcomeUser(UserDetails userDetails) {
        // Create the main frame
        JFrame frame = new JFrame("Welcome Menu");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLocationRelativeTo(null);

        // Main panel for content
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(new Color(240, 240, 240));

        // Title label
        JLabel titleLabel = new JLabel("Welcome " + userDetails.getUserName() + "!");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        titleLabel.setForeground(new Color(60, 90, 150));
        panel.add(titleLabel);

        // Add spacing
        panel.add(Box.createVerticalStrut(10));

        // Personal information label
        JLabel userInfo = new JLabel("<html>" +
                "Email: <b>" + userDetails.getEmail() + "</b><br>" +
                "Birthday: <b>" + userDetails.getBirthday() + "</b><br>" +
                "Current Course: <b>" + (userDetails.getCurrentCourse() == null ? "N/A" : userDetails.getCurrentCourse()) + "</b><br>" +
                "Main Language: <b>" + userDetails.getMainLanguage() + "</b>" +
                "</html>");
        userInfo.setFont(new Font("Arial", Font.PLAIN, 14));
        userInfo.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        panel.add(userInfo);
        panel.add(Box.createVerticalStrut(10));

        // Option buttons
        String[] options = {"Games", "Course", "Achievements", "Exit"};
        int choice = JOptionPane.showOptionDialog(null, panel, "Welcome Menu",
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

        // Process the selected option
        switch (choice) {
            case 0:
                Game.startGame(userDetails);
                break;
            case 1:
                Course.displayCourse(userDetails);
                break;
            case 2:
                displayAchievements(userDetails); // Show achievements
                break;
            case 3:
                return;
            default:
                JOptionPane.showMessageDialog(null, "Invalid option. Please try again.");
        }
    }

    private static void displayAchievements(UserDetails userDetails) {
        List<String> achievements = userDetails.getAchievements();

        if (achievements == null || achievements.isEmpty()) {
            JOptionPane.showMessageDialog(null, "You have no achievements yet.", "Achievements", JOptionPane.INFORMATION_MESSAGE);
        } else {
            StringBuilder achievementsList = new StringBuilder("Your Achievements:\n\n");
            for (String achievement : achievements) {
                if (achievement != null && !achievement.trim().isEmpty()) {
                    achievementsList.append("- ").append(achievement).append("\n");
                }
            }

            if (achievementsList.toString().equals("Your Achievements:\n\n")) {
                JOptionPane.showMessageDialog(null, "You have no valid achievements yet.", "Achievements", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, achievementsList.toString(), "Achievements", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }


    private static void displayAllUsers() {
        List<UserDetails> userDetails = UserDatabase.getAllUsers();
        if (userDetails.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No users found.", "User List", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        int currentIndex = 0;
        while (true) {
            UserDetails currentUser = userDetails.get(currentIndex);
            StringBuilder userDetail = new StringBuilder("===== User Details =====\n")
                    .append("User ID: ").append(currentUser.getUserID()).append("\n")
                    .append("Name: ").append(currentUser.getUserName()).append("\n")
                    .append("Email: ").append(currentUser.getEmail()).append("\n")
                    .append("Birthday: ").append(currentUser.getBirthday()).append("\n")
                    .append("Course: ").append(currentUser.getCurrentCourse()).append("\n")
                    .append("Main Language: ").append(currentUser.getMainLanguage()).append("\n");

            int option = JOptionPane.showOptionDialog(
                    null,
                    userDetail.toString(),
                    "User Details (" + (currentIndex + 1) + " of " + userDetails.size() + ")",
                    JOptionPane.YES_NO_CANCEL_OPTION,
                    JOptionPane.INFORMATION_MESSAGE,
                    null,
                    new String[]{"Previous", "Next", "Close"},
                    "Next"
            );

            if (option == 0) { // Previous
                currentIndex = (currentIndex - 1 + userDetails.size()) % userDetails.size();
            } else if (option == 1) { // Next    
                currentIndex = (currentIndex + 1) % userDetails.size();
            } else { // Close
                break;
            }
        }
    }
    
    

    private static void deleteUser() {
        String userID = JOptionPane.showInputDialog("Enter User ID to delete:");
        UserDatabase.deleteUser(userID);
    }
}
