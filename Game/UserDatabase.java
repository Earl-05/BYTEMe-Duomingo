package Game;

import java.util.ArrayList;

public class UserDatabase {
    private static ArrayList<User> users = new ArrayList<>();

    static {
        for (User user : getUsers()) {
            users.add(user);
        }
    }

    public static User[] getUsers() {
        return new User[]{
            new User("001", "Juan", "password123", "juan@example.com", "1990-01-01"),
            new User("002", "Maria", "mypass456", "maria@example.com", "1992-02-02"),
            new User("003", "Pedro", "password789", "pedro@example.com", "1994-03-03"),
            new User("004", "Ana", "mypassword", "ana@example.com", "1996-04-04"),
            new User("005", "Carlos", "password000", "carlos@example.com", "1998-05-05")
        };
    }

    public static void addUser(User user) {
        users.add(user);
    }

    public static User getUser(String userID) {
        for (User user : users) {
            if (user.getUserID().equals(userID)) {
                return user;
            }
        }
        return null;
    }

    public static void updateUser(User user) {
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getUserID().equals(user.getUserID())) {
                users.set(i, user);
                return;
            }
        }
    }

    public static ArrayList<User> getAllUsers() {
        return new ArrayList<>(users);
    }

    public static boolean validateUser(String userID, String password) {
        User user = getUser(userID);
        return user != null && user.getPassword().equals(password);
    }
}
