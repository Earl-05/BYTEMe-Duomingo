package Game;

public class CourseDatabase {
    public static CourseDetails[] getCourses() {
        return new CourseDetails[]{
            new CourseDetails("ESP-103", "Spanish", "Vocabulary, Grammar, Pronunciation", 120, 0.0, "Beginner"),
            new CourseDetails("JAP-102", "Japanese", "Vocabulary, Grammar, Pronunciation", 100, 0.0, "Intermediate"),
            new CourseDetails("FIL-111", "Filipino", "Vocabulary, Grammar, Pronunciation", 110, 0.0, "Beginner"),
            new CourseDetails("ENG-112", "English", "Vocabulary, Grammar, Pronunciation", 100, 0.0, "Advanced"),
            new CourseDetails("FRE-104", "French", "Vocabulary, Grammar, Pronunciation", 110, 0.0, "Intermediate"),
            new CourseDetails("HIL-113", "Hiligaynon", "Vocabulary, Grammar, Pronunciation", 90, 0.0, "Intermediate")
        };
    }
}
