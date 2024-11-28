package Game;

import java.util.List;
import java.util.ArrayList;
import javax.swing.*;
import java.awt.*;

public class AppMain {

    public static void main(String[] args) {
        while (true) {
            String[] options = {"Log In", "Sign Up", "Display All Users", "Delete User", "Exit"};
            int choice = JOptionPane.showOptionDialog(null, "WELCOME TO DUOMINGO", "Main Menu",
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
            User user = UserDatabase.getUser(userID);
            if (user != null) {
                welcomeUser(user);
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
        User newUser = new User(userID, userName, password, email, birthday, selectedCourse, mainLanguage, achievements);
        UserDatabase.addUser(newUser);
    }

    private static void welcomeUser(User user) {
        // Create the main frame
        JFrame frame = new JFrame("Welcome Menu");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLocationRelativeTo(null);

        // Main panel for content
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(new Color(240, 240, 240)); // Light background color

        // Title label
        JLabel titleLabel = new JLabel("WELCOME " + user.getUserName() + "!");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        titleLabel.setForeground(new Color(60, 90, 150)); // Custom title color
        panel.add(titleLabel);

        // Add spacing
        panel.add(Box.createVerticalStrut(10));

        // Personal information label
        JLabel userInfo = new JLabel("<html>" +
                "Email: <b>" + user.getEmail() + "</b><br>" +
                "Birthday: <b>" + user.getBirthday() + "</b><br>" +
                "Current Course: <b>" + (user.getCurrentCourse() == null ? "N/A" : user.getCurrentCourse()) + "</b><br>" +
                "Achievements: <b>" + (user.getAchievements().isEmpty() ? "None" : String.join(", ", user.getAchievements())) + "</b><br>" +
                "Main Language: <b>" + user.getMainLanguage() + "</b>" +
                "</html>");
        userInfo.setFont(new Font("Arial", Font.PLAIN, 14));
        userInfo.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        panel.add(userInfo);

        // Add some padding between the user information and options
        panel.add(Box.createVerticalStrut(10));

        // Option buttons
        String[] options = {"Games", "Course", "Achievements", "Exit"};
        int choice = JOptionPane.showOptionDialog(null, panel, "Welcome Menu",
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

        // Process the selected option
        switch (choice) {
            case 0:
                JOptionPane.showMessageDialog(null, "Games functionality is under construction.");
                break;
            case 1:
                Course.displayCourse(user); // Directly display the course information
                break;
            case 2:
                JOptionPane.showMessageDialog(null, "Achievements functionality is under construction.");
                break;
            case 3:
                return;
            default:
                JOptionPane.showMessageDialog(null, "Invalid option. Please try again.");
        }
    }


    private static void displayAllUsers() {
        List<User> users = UserDatabase.getAllUsers();
        StringBuilder userList = new StringBuilder("===== User List =====\n");
        for (User user : users) {
            userList.append("User ID: ").append(user.getUserID()).append("\n")
                    .append("Name: ").append(user.getUserName()).append("\n")
                    .append("Email: ").append(user.getEmail()).append("\n")
                    .append("Birthday: ").append(user.getBirthday()).append("\n")
                    .append("Course: ").append(user.getCurrentCourse()).append("\n")
                    .append("Main Language: ").append(user.getMainLanguage()).append("\n")
                    .append("-------------------\n");
        }
        JOptionPane.showMessageDialog(null, userList.toString());
    }

    private static void deleteUser() {
        String userID = JOptionPane.showInputDialog("Enter User ID to delete:");
        UserDatabase.deleteUser(userID);
    }
}
