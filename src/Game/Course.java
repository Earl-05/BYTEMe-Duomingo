package Game;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.*;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


public class Course {
    public static void displayCourse(UserDetails userDetails) {
        CourseDetails currentCourse = getCurrentCourseDetails(userDetails.getCurrentCourse());
        if (currentCourse != null) {
            boolean continueCourse = true;
            while (continueCourse) {

                String[] options = {"Start Course", "Change Course", "Exit"};

                int choice = JOptionPane.showOptionDialog(null,
                        "Selected Course: " + currentCourse.getCourseName() + "\n" +
                        "Description: " + currentCourse.getCourseDescription(),
                        "Course Management",
                        JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE,
                        null, options, options[0]);
                
                if (choice==JOptionPane.CLOSED_OPTION) {
                	System.exit(0);
                }
                
                switch (choice) {
                    case 0:
                        Game.startGame(userDetails);
                        break;
                    case 1:
                        changeCourse(userDetails);
                        break;
                    case 2:
                        User.welcomeUser(userDetails);
                        break;
                    default:
                        break;
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "No course selected. Please choose a course first.");
        }
    }

    private static CourseDetails getCurrentCourseDetails(String courseName) {
        CourseDetails[] courses = CourseDatabase.getCourses();
        for (CourseDetails course : courses) {
            if (course.getCourseName().equals(courseName)) {
                return course;
            }
        }
        return null;
    }

    private static void changeCourse(UserDetails userDetails) {
        int confirm = JOptionPane.showConfirmDialog(null, "Changing the course will lose all progress. Do you want to continue?");
        if (confirm == JOptionPane.YES_OPTION) {
            selectCourse(userDetails);
            saveUserDetails(userDetails);
        }
    }

    static void selectCourse(UserDetails userDetails) {
        CourseDetails[] courses = CourseDatabase.getCourses();
        String[] courseOptions = new String[courses.length];
        for (int i = 0; i < courses.length; i++) {
            courseOptions[i] = courses[i].getCourseName();
        }
        String selectedCourse = (String) JOptionPane.showInputDialog(null,
                "Select a course",
                "Course Selection",
                JOptionPane.QUESTION_MESSAGE,
                null, courseOptions, courseOptions[0]);
        if (selectedCourse != null) {
            userDetails.setCurrentCourse(selectedCourse);
            saveUserDetails(userDetails); 
            JOptionPane.showMessageDialog(null, "Course selected successfully!");
            User.welcomeUser(userDetails);
            
        }
    }

   
    private static void saveUserDetails(UserDetails userDetails) {
        try {
            // Read the existing users.json file
            String filePath = getFilePath("users.json");
            Gson gson = new Gson();
            Gson prettyGson = new GsonBuilder().setPrettyPrinting().create();
            File file = new File(filePath);
            
            if (!file.exists()) {
                System.err.println("users.json file not found!");
                return;
            }
            
            // Read and parse the JSON file into an array of UserDetails
            try (FileReader reader = new FileReader(file)) {
                UserDetails[] users = gson.fromJson(reader, UserDetails[].class);
                
                // Find the user and update their current course
                for (UserDetails user : users) {
                    if (user.getUserID().equals(userDetails.getUserID())) {
                        user.setCurrentCourse(userDetails.getCurrentCourse());
                        break;
                    }
                }
                
                // Write the updated list back to the file with pretty-printing
                try (FileWriter writer = new FileWriter(file)) {
                    prettyGson.toJson(users, writer);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Failed to update user details!");
        }
    }


    
    private static String getFilePath(String fileName) {
        String userDir = System.getProperty("user.dir");
        return userDir + File.separator + "src" + File.separator + "Game" + File.separator + fileName;
    }
}
