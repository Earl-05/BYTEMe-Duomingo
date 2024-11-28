package Game;

import javax.swing.*;

public class AppMain {

    public static void main(String[] args) {
        while (true) {
            String[] options = {"Log In", "Sign Up", "Display All Users", "Exit"};
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
            welcomeUser(user);
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

        User newUser = new User(userID, userName, password, email, birthday);
        UserDatabase.addUser(newUser);
        JOptionPane.showMessageDialog(null, "User added successfully!");

        Course.selectCourse(newUser);
    }

    private static void welcomeUser(User user) {
        JOptionPane.showMessageDialog(null, "WELCOME " + user.getUserName() + "!\n" +
                "Email: " + user.getEmail() + "\n" +
                "Birthday: " + user.getBirthday() + "\n" +
                "Current Course: " + user.getCurrentCourse() + "\n" +
                "Progress of the Current Course: " + (user.getCurrentCourse() == null ? "N/A" : "To be implemented"));

        while (true) {
            String[] options = {"Games", "Course", "Achievement", "Exit"};
            int choice = JOptionPane.showOptionDialog(null, "Select an option", "Welcome Menu",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

            switch (choice) {
                case 0:
                    // Implement Games functionality
                    break;
                case 1:
                    Course.displayCourse(user);
                    break;
                case 2:
                    // Implement Achievement functionality
                    break;
                case 3:
                    return;
                default:
                    JOptionPane.showMessageDialog(null, "Invalid option. Please try again.");
            }
        }
    }

    private static void displayAllUsers() {
        if (UserDatabase.getAllUsers().isEmpty()) {
            JOptionPane.showMessageDialog(null, "No users to display.");
        } else {
            StringBuilder userList = new StringBuilder("\n===== User List =====\n");
            for (User user : UserDatabase.getAllUsers()) {
                userList.append("User ID: ").append(user.getUserID()).append("\n");
                userList.append("User Name: ").append(user.getUserName()).append("\n");
                userList.append("Email: ").append(user.getEmail()).append("\n");
                userList.append("Birthday: ").append(user.getBirthday()).append("\n");
                userList.append("Current Course: ").append(user.getCurrentCourse()).append("\n");
                userList.append("Achievements: ").append(user.getAchievements()).append("\n");
                userList.append("---------------------\n");
            }
            JOptionPane.showMessageDialog(null, userList.toString());
        }
    }
}
