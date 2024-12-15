package Game;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.UUID;
import java.util.ArrayList;
import java.util.Collections;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AppMain {

    public static void main(String[] args) {
        MainScreen(args);
    }

    public static void MainScreen(String[] args) {
        while (true) {
            String[] options = {"Log In", "Sign Up", "Display All Users", "Exit"};
            int choice = JOptionPane.showOptionDialog(null, "WELCOME TO DUOMINGO!", "Main Menu",
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
        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));

        JLabel userLabel = new JLabel("Username:");
        JTextField userField = new JTextField();

        JLabel passLabel = new JLabel("Password:");
        JPasswordField passField = new JPasswordField();

        JCheckBox showPassword = new JCheckBox("Show Password");
        showPassword.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (showPassword.isSelected()) {
                    passField.setEchoChar((char) 0); // Show password
                } else {
                    passField.setEchoChar('*'); // Mask password
                }
            }
        });

        panel.add(userLabel);
        panel.add(userField);
        panel.add(passLabel);
        panel.add(passField);
        panel.add(new JLabel());
        panel.add(showPassword);

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
        JPanel panel = new JPanel(new GridLayout(7, 2, 10, 10));

        JLabel nameLabel = new JLabel("User Name:");
        JTextField nameField = new JTextField();

        JLabel passLabel = new JLabel("Password:");
        JPasswordField passField = new JPasswordField();

        JCheckBox showPassword = new JCheckBox("Show Password");
        showPassword.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (showPassword.isSelected()) {
                    passField.setEchoChar((char) 0);
                } else {
                    passField.setEchoChar('*');
                }
            }
        });

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
        panel.add(new JLabel());
        panel.add(showPassword);
        panel.add(emailLabel);
        panel.add(emailField);
        panel.add(birthdayLabel);
        panel.add(birthdayField);
        panel.add(courseLabel);
        panel.add(courseBox);

        while (true) {
            int result = JOptionPane.showConfirmDialog(null, panel, "Sign Up", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

            if (result != JOptionPane.OK_OPTION) {
                return;
            }

            String userID = UUID.randomUUID().toString();
            String userName = nameField.getText();
            String password = new String(passField.getPassword());
            String email = emailField.getText();
            String birthday = birthdayField.getText();
            String selectedCourse = (String) courseBox.getSelectedItem();
            String mainLanguage = "English";

            List<String> achievements = new ArrayList<>();

            String validationError = validateSignUp(userName, password, email, birthday);
            if (!validationError.isEmpty()) {
                JOptionPane.showMessageDialog(null, validationError, "Invalid Input", JOptionPane.ERROR_MESSAGE);
                continue;
            }

            if (isUnderAge(birthday)) {
                UserDetails underageUser = new UserDetails(
                        userID, userName, password, email, birthday, selectedCourse,
                        achievements, mainLanguage, 0, 0, 0, 0, 0, 0, result
                );
                parentalConsentForm(underageUser);
                return;
            }

            UserDetails newUser = new UserDetails(userID, userName, password, email, birthday, selectedCourse, achievements, mainLanguage, 0, 0, 0, 0, 0, 0, result);
            UserDatabase.addUser(newUser);
            JOptionPane.showMessageDialog(null, "Sign up successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
            break;
        }
    }

    private static String validateSignUp(String userName, String password, String email, String birthday) {
        StringBuilder error = new StringBuilder();

        // Username validation
        if (userName.trim().isEmpty()) {
            error.append("- Username cannot be empty.\n");
        }

        // Password validation
        if (password.length() < 8) {
            error.append("- Password must be at least 8 characters long.\n");
        }
        if (!password.matches(".*\\d.*")) { // Check for at least one digit
            error.append("- Password must contain at least one number.\n");
        }
        if (!password.matches(".*[A-Z].*")) { // Check for at least one uppercase letter
            error.append("- Password must contain at least one uppercase letter.\n");
        }

        // Email validation
        if (!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
            error.append("- Email format is invalid.\n");
        }

        // Birthday validation
        try {
            LocalDate.parse(birthday, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        } catch (DateTimeParseException e) {
            error.append("- Birthday must follow the format YYYY-MM-DD.\n");
        }

        return error.toString();
    }

    private static boolean isUnderAge(String birthday) {
        try {
            LocalDate birthDate = LocalDate.parse(birthday, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            LocalDate currentDate = LocalDate.now();
            int age = currentDate.getYear() - birthDate.getYear();
            if (birthDate.plusYears(age).isAfter(currentDate)) {
                age--;
            }
            return age < 14;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    private static void parentalConsentForm(UserDetails underageUser) {
        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));

        JLabel parentNameLabel = new JLabel("Parent/Guardian Name:");
        JTextField parentNameField = new JTextField();

        JLabel parentEmailLabel = new JLabel("Parent/Guardian Email:");
        JTextField parentEmailField = new JTextField();

        JLabel consentLabel = new JLabel("Consent:");
        JCheckBox consentCheckBox = new JCheckBox("I agree to allow my child to use this application.");

        panel.add(parentNameLabel);
        panel.add(parentNameField);
        panel.add(parentEmailLabel);
        panel.add(parentEmailField);
        panel.add(consentLabel);
        panel.add(consentCheckBox);

        int result = JOptionPane.showConfirmDialog(null, panel, "Parental Consent", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            if (parentNameField.getText().trim().isEmpty() || parentEmailField.getText().trim().isEmpty() || !consentCheckBox.isSelected()) {
                JOptionPane.showMessageDialog(null, "Parental consent form is incomplete. Please try again.", "Consent Required", JOptionPane.ERROR_MESSAGE);
            } else {
                // Add the underage user to the database
                UserDatabase.addUser(underageUser);
                JOptionPane.showMessageDialog(null, "Parental consent received. User added to the database.", "Consent Received", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }

    private static void displayAllUsers() {
        List<UserDetails> userDetails = UserDatabase.getAllUsers();
        if (userDetails.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No users found.", "User List", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        quickSort(userDetails, 0, userDetails.size() - 1);

        int currentIndex = 0;
        while (true) {
            UserDetails currentUser = userDetails.get(currentIndex);
            StringBuilder userDetail = new StringBuilder("===== DUOMINGGO USERS =====\n")
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

    private static void quickSort(List<UserDetails> users, int low, int high) {
        if (low < high) {
            int pi = partition(users, low, high);
            quickSort(users, low, pi - 1);
            quickSort(users, pi + 1, high);
        }
    }

    private static int partition(List<UserDetails> users, int low, int high) {
        String pivot = users.get(high).getUserName().toLowerCase();
        int i = (low - 1);
        for (int j = low; j < high; j++) {
            if (users.get(j).getUserName().toLowerCase().compareTo(pivot) <= 0) {
                i++;
                Collections.swap(users, i, j);
            }
        }
        Collections.swap(users, i + 1, high);
        return i + 1;
    }
}
