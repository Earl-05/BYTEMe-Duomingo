package Game;

import java.util.*;
import java.util.List;
import javax.swing.*;
import java.awt.*;

public class AppMain {

    public static void main(String[] args) {
        MainScreen(args);
    }

    public static void MainScreen(String[] args) {
        while (true) {
            String[] options = {"Log In", "Sign Up", "Display All Users", "Exit"};
            int choice = JOptionPane.showOptionDialog(null, "WELCOME TO DUOMINGO! Username: Jade, Password: 123", "Main Menu",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

            if (choice == JOptionPane.CLOSED_OPTION) {
                break;
            }

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
                    System.exit(0);
                    break;
                default:
                    JOptionPane.showMessageDialog(null, "Invalid option. Please try again.");
            }
        }
    }

    private static void logIn() {
        JPanel panel = new JPanel(new GridLayout(2, 2, 10, 10));

        JLabel userLabel = new JLabel("Username:");
        JTextField userField = new JTextField();

        JLabel passLabel = new JLabel("Password:");
        JPasswordField passField = new JPasswordField();

        panel.add(userLabel);
        panel.add(userField);
        panel.add(passLabel);
        panel.add(passField);

        int result = JOptionPane.showConfirmDialog(null, panel, "Log In", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            String userName = userField.getText();
            String password = new String(passField.getPassword());

            if (UserDatabase.validateUser(userName, password)) {
                UserDetails userDetails = UserDatabase.getUserByUsername(userName);
                if (userDetails != null) {
                    User.welcomeUser(userDetails);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Invalid credentials. Please try again.");
            }
        }
    }


    private static void signUp() {
        JPanel panel = new JPanel(new GridLayout(5, 2, 10, 10));

        JLabel nameLabel = new JLabel("User Name:");
        JTextField nameField = new JTextField();

        JLabel passLabel = new JLabel("Password:");
        JPasswordField passField = new JPasswordField();

        JLabel emailLabel = new JLabel("Email:");
        JTextField emailField = new JTextField();

        JLabel birthdayLabel = new JLabel("Birthday (YYYY-MM-DD):");
        JTextField birthdayField = new JTextField();

        JLabel courseLabel = new JLabel("Select Course:");
        String[] courses = {"Filipino", "Spanish", "Japanese", "French", "Hiligaynon"};
        JComboBox<String> courseBox = new JComboBox<>(courses);

        panel.add(nameLabel);
        panel.add(nameField);
        panel.add(passLabel);
        panel.add(passField);
        panel.add(emailLabel);
        panel.add(emailField);
        panel.add(birthdayLabel);
        panel.add(birthdayField);
        panel.add(courseLabel);
        panel.add(courseBox);

        int result = JOptionPane.showConfirmDialog(null, panel, "Sign Up", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            String userID = UUID.randomUUID().toString();
            String userName = nameField.getText();
            String password = new String(passField.getPassword());
            String email = emailField.getText();
            String birthday = birthdayField.getText();
            String selectedCourse = (String) courseBox.getSelectedItem();
            String mainLanguage = "English";

            List<String> achievements = new ArrayList<>();

            UserDetails newUser = new UserDetails(userID, userName, password, email, birthday, selectedCourse, achievements, mainLanguage, 0, 0, 0, 0);
            UserDatabase.addUser(newUser);
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
                    .append("Name: ").append(currentUser.getUserName()).append("\n")
                    .append("Email: ").append(currentUser.getEmail()).append("\n")
                    .append("Birthday: ").append(currentUser.getBirthday()).append("\n")
                    .append("Course: ").append(currentUser.getCurrentCourse()).append("\n");

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
}
