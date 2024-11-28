package Game;

import com.google.gson.*;
import java.io.*;
import java.util.*;

public class CourseDatabase {

    private static List<CourseDetails> courses;

    static {
        loadCourses();
    }

    private static void loadCourses() {
        try (Reader reader = new FileReader("C:/Users/Ransss/Documents/GitHub/Finals/Finals/src/Game/courses.json")) {
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
}
