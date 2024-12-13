
package Game;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

public class AppMain {

    public static void main(String[] args) {
        MainScreen(args);
    }

    public static void MainScreen(String[] args) {
        while (true) {
            String[] options = {"Log In", "Sign Up", "Display All Users", "Delete User", "Exit"};
            int choice = JOptionPane.showOptionDialog(null, "WELCOME TO DUOMINGO, KUNG MAKITA NIYU NI GA WORK ANG PUSH", "Main Menu",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
            
            if (choice == JOptionPane.CLOSED_OPTION) {
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
        if (userID== null) {
        	return;
        }
        String password = JOptionPane.showInputDialog("Enter Password:");
        if (password==null) {
        	return;
        }
		
        if (UserDatabase.validateUser(userID, password)) {
            UserDetails userDetails = UserDatabase.getUser(userID);
            if (userDetails != null) {
                User.welcomeUser(userDetails);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Invalid credentials. Please try again.");
        }
    }

    private static void signUp() {
        String userID = JOptionPane.showInputDialog("Enter User ID:");
        if (userID==null) {
        	return;
        }
        
        String userName = JOptionPane.showInputDialog("Enter User Name:");
        if (userName==null) {
        	return;
        }
        
        String password = JOptionPane.showInputDialog("Enter Password:");
        if(password==null) {
        	return;
        }
        
        String email = JOptionPane.showInputDialog("Enter Email:");
        if(email==null) {
        	return;
        }
        
        String birthday = JOptionPane.showInputDialog("Enter Birthday (YYYY-MM-DD):");
        if(birthday==null) {
        	return;
        }
        
        String[] courses = {"Filipino", "English", "Spanish", "Japanese", "French", "Hiligaynon"};
        String selectedCourse = (String) JOptionPane.showInputDialog(null, "Select Course", "Course Selection",
                JOptionPane.QUESTION_MESSAGE, null, courses, courses[0]);
        if(selectedCourse==null) {
        	return;
        }

        String[] languages = {"English", "Filipino", "Spanish", "Japanese", "French", "Hiligaynon"};
        String mainLanguage = (String) JOptionPane.showInputDialog(null, "Select Your Main Language", "Main Language Selection",
                JOptionPane.QUESTION_MESSAGE, null, languages, languages[0]);
        if(mainLanguage==null) {
        	return;
        }

        List<String> achievements = new ArrayList<>();

        UserDetails newUser = new UserDetails(userID, userName, password, email, birthday, selectedCourse, achievements, mainLanguage, 0, 0, 0, 0);
        UserDatabase.addUser(newUser);
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
