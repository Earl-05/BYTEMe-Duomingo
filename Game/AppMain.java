package Game;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        while (true) {
            String[] options = {"Log In", "Sign Up", "Display All Users", "Delete User", "Exit"};
            int choice = JOptionPane.showOptionDialog(null, "WELCOME TO DUOMINGO", "Main Menu",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

            switch (choice) {
                case 0 -> User.logIn();
                case 1 -> User.signUp();
                case 2 -> User.displayAllUsers();
                case 3 -> User.deleteUser();
                case 4 -> System.exit(0);
                default -> JOptionPane.showMessageDialog(null, "Invalid option. Please try again.");
            }
        }
    }
}
