package Game;

import javax.swing.*;

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
                    User.logIn();
                    break;
                case 1:
                    User.signUp();
                    break;
                case 2:
                    User.displayAllUsers();
                    break;
                case 3:
                    System.exit(0);
                    break;
                default:
                    JOptionPane.showMessageDialog(null, "Invalid option. Please try again.");
            }
        }
    }
}
