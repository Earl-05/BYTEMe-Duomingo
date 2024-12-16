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

public class User {
	
    public static void logIn() {
        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));

        JLabel userLabel = new JLabel("Username:");
        JTextField userField = new JTextField();

        JLabel passLabel = new JLabel("Password:");
        JPasswordField passField = new JPasswordField();

        JCheckBox showPassword = new JCheckBox("Show Password");
        showPassword.addActionListener(e -> {
            if (showPassword.isSelected()) {
                passField.setEchoChar((char) 0); 
            } else {
                passField.setEchoChar('*'); 
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
                    welcomeUser(userDetails);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Invalid credentials. Please try again.");
            }
        }
    }

    public static void signUp() {
        JPanel panel = new JPanel(new GridLayout(7, 2, 10, 10));

        JLabel nameLabel = new JLabel("User Name:");
        JTextField nameField = new JTextField();

        JLabel passLabel = new JLabel("Password:");
        JPasswordField passField = new JPasswordField();

        JCheckBox showPassword = new JCheckBox("Show Password");
        showPassword.addActionListener(e -> {
            if (showPassword.isSelected()) {
                passField.setEchoChar((char) 0);
            } else {
                passField.setEchoChar('*');
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
                        achievements, mainLanguage, 0, 0, 0, 0, 0, 0, 0, result
                );
                parentalConsentForm(underageUser);
                return;
            }

            UserDetails newUser = new UserDetails(userID, userName, password, email, birthday, selectedCourse, achievements, mainLanguage, 0, 0, 0, 0, 0, 0, 0, result);
            UserDatabase.addUser(newUser);
            JOptionPane.showMessageDialog(null, "Sign up successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
            break;
        }
    }

    public static void displayAllUsers() {
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

            if (option == 0) { 
                currentIndex = (currentIndex - 1 + userDetails.size()) % userDetails.size();
            } else if (option == 1) { 
                currentIndex = (currentIndex + 1) % userDetails.size();
            } else { 
                break;
            }
        }
    }

    private static String validateSignUp(String userName, String password, String email, String birthday) {
        StringBuilder error = new StringBuilder();

        if (userName.trim().isEmpty()) {
            error.append("- Username cannot be empty.\n");
        }

        if (password.length() < 8) {
            error.append("- Password must be at least 8 characters long.\n");
        }
        if (!password.matches(".*\\d.*")) { 
            error.append("- Password must contain at least one number.\n");
        }
        if (!password.matches(".*[A-Z].*")) { 
            error.append("- Password must contain at least one uppercase letter.\n");
        }

        if (!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
            error.append("- Email format is invalid.\n");
        }

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
                UserDatabase.addUser(underageUser);
                JOptionPane.showMessageDialog(null, "Parental consent received. User added to the database.", "Consent Received", JOptionPane.INFORMATION_MESSAGE);
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

    static void welcomeUser(UserDetails userDetails) {
        JFrame frame = new JFrame("Welcome Menu");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(new Color(240, 240, 240));

        JLabel titleLabel = new JLabel("Welcome " + userDetails.getUserName() + "!");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        titleLabel.setForeground(new Color(60, 90, 150));
        panel.add(titleLabel);

        panel.add(Box.createVerticalStrut(10));

        JLabel userInfo = new JLabel("<html>" +
                "Email: <b>" + userDetails.getEmail() + "</b><br>" +
                "Birthday: <b>" + userDetails.getBirthday() + "</b><br>" +
                "Current Course: <b>" + (userDetails.getCurrentCourse() == null ? "N/A" : userDetails.getCurrentCourse()) + "</b><br>" +
                "Main Language: <b>" + userDetails.getMainLanguage() + "</b><br>" +
                "Your total score: <b> " + userDetails.getTotalScore() + "</b>" +
                "</html>");
        userInfo.setFont(new Font("Arial", Font.PLAIN, 14));
        userInfo.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        panel.add(userInfo);
        panel.add(Box.createVerticalStrut(10));

        String[] options = {"Course", "Edit Info", "Achievements", "Log Out"};
        int choice = JOptionPane.showOptionDialog(null, panel, "Welcome Menu",
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

        switch (choice) {
            case 0:
                Course.displayCourse(userDetails);
                break;
            case 1:  
                editUserInfo(userDetails);
                welcomeUser(userDetails);
                break;
            case 2:
                displayAchievements(userDetails);
                break;
            case 3:
                frame.dispose(); 
                AppMain.MainScreen(new String[0]);
                break;
            default:
                System.exit(0);
                break;
        }
    }

    private static void editUserInfo(UserDetails userDetails) {
        JPanel panel = new JPanel(new GridLayout(5, 2, 10, 10)); 

        JLabel nameLabel = new JLabel("User Name:");
        JTextField nameField = new JTextField(userDetails.getUserName());

        JLabel emailLabel = new JLabel("Email:");
        JTextField emailField = new JTextField(userDetails.getEmail());

        JLabel birthdayLabel = new JLabel("Birthday (YYYY-MM-DD):");
        JTextField birthdayField = new JTextField(userDetails.getBirthday());

        JLabel passwordLabel = new JLabel("New Password:");
        JPasswordField passwordField = new JPasswordField();

        JButton deleteButton = new JButton("Delete Account");
        deleteButton.setBackground(new Color(153, 0, 0));
        deleteButton.setForeground(Color.WHITE);

        deleteButton.addActionListener(e -> deleteUser(userDetails));

        panel.add(nameLabel);
        panel.add(nameField);
        panel.add(emailLabel);
        panel.add(emailField);
        panel.add(birthdayLabel);
        panel.add(birthdayField);
        panel.add(passwordLabel);
        panel.add(passwordField);
        panel.add(new JLabel()); 
        panel.add(deleteButton);

        int result = JOptionPane.showConfirmDialog(null, panel, "Edit User Info", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            userDetails.setUserName(nameField.getText());
            userDetails.setEmail(emailField.getText());
            userDetails.setBirthday(birthdayField.getText());

            String newPassword = new String(passwordField.getPassword());
            if (!newPassword.trim().isEmpty()) {
                userDetails.setPassword(newPassword);
            }

            UserDatabase.updateUser(userDetails);
            JOptionPane.showMessageDialog(null, "User information updated successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private static void deleteUser(UserDetails userDetails) {
        int confirmation = JOptionPane.showConfirmDialog(null,
                "Are you sure you want to PERMANENTLY DELETE your account?\nYou will lose all your progress.",
                "Delete Account",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);

        if (confirmation == JOptionPane.YES_OPTION) {
            UserDatabase.deleteUser(userDetails.getUserID());
            JOptionPane.showMessageDialog(null, "Your account has been deleted.", "Account Deleted", JOptionPane.INFORMATION_MESSAGE);
            System.exit(0); 
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
        
        welcomeUser(userDetails);
    }
}
