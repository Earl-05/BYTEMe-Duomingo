package Game;

import java.util.List;
import javax.swing.*;
import java.awt.*;

public class User {

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
        String[] options = {"Course", "Edit Info", "Achievements", "Log Out"};
        int choice = JOptionPane.showOptionDialog(null, panel, "Welcome Menu",
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

        // Process the selected option
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
        JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10));

        JLabel nameLabel = new JLabel("User Name:");
        JTextField nameField = new JTextField(userDetails.getUserName());

        JLabel emailLabel = new JLabel("Email:");
        JTextField emailField = new JTextField(userDetails.getEmail());

        JLabel birthdayLabel = new JLabel("Birthday (YYYY-MM-DD):");
        JTextField birthdayField = new JTextField(userDetails.getBirthday());
        
        JButton deleteButton = new JButton("Delete Account");
        deleteButton.setBackground(new Color(153,0,0));
        deleteButton.setForeground(Color.WHITE);

        deleteButton.addActionListener(e -> deleteUser(userDetails));

        panel.add(nameLabel);
        panel.add(nameField);
        panel.add(emailLabel);
        panel.add(emailField);
        panel.add(birthdayLabel);
        panel.add(birthdayField);
        panel.add(new JLabel()); // Placeholder to align the delete button
        panel.add(deleteButton);

        int result = JOptionPane.showConfirmDialog(null, panel, "Edit User Info", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            userDetails.setUserName(nameField.getText());
            userDetails.setEmail(emailField.getText());
            userDetails.setBirthday(birthdayField.getText());

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
            System.exit(0); // Exit the application after account deletion
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
