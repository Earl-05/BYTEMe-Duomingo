package Game;

import java.io.*;
import java.util.*;

import com.google.gson.Gson;






public class CourseDatabase {

    private static final String COURSES_FILE = getFilePath("courses.json");
    private static List<CourseDetails> courses;

    static {
        loadCourses();
    }

    private static void loadCourses() {
        File file = new File(COURSES_FILE);
        if (!file.exists()) {
            System.err.println("Courses file not found: " + COURSES_FILE);
            courses = new ArrayList<>(); // Initialize an empty list if file is not found
            return;
        }

        try (Reader reader = new FileReader(file)) {
            Gson gson = new Gson();
            CourseDetails[] courseArray = gson.fromJson(reader, CourseDetails[].class);
            courses = Arrays.asList(courseArray);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static CourseDetails[] getCourses() {
        return courses.toArray(new CourseDetails[0]);
    }

    private static String getFilePath(String fileName) {
        String userDir = System.getProperty("user.dir");
        return userDir + File.separator + "src" + File.separator + "Game" + File.separator + fileName;
    }
}