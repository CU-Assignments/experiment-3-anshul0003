import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

// Custom exception for full course
class CourseFullException extends Exception {
    public CourseFullException(String message) {
        super(message);
    }
}

// Custom exception for missing prerequisite
class PrerequisiteNotMetException extends Exception {
    public PrerequisiteNotMetException(String message) {
        super(message);
    }
}

// Course class
class Course {
    private String name;
    private String prerequisite;
    private int maxEnrollment;
    private int enrolledStudents;

    public Course(String name, String prerequisite, int maxEnrollment) {
        this.name = name;
        this.prerequisite = prerequisite;
        this.maxEnrollment = maxEnrollment;
        this.enrolledStudents = 0;
    }

    public String getName() {
        return name;
    }

    public String getPrerequisite() {
        return prerequisite;
    }

    public int getMaxEnrollment() {
        return maxEnrollment;
    }

    public int getEnrolledStudents() {
        return enrolledStudents;
    }

    public void enrollStudent() throws CourseFullException {
        if (enrolledStudents >= maxEnrollment) {
            throw new CourseFullException("Error: CourseFullException - " + name + " is full.");
        }
        enrolledStudents++;
    }
}

// University enrollment system
public class UniversityEnrollmentSystem {
    private static Map<String, Course> courses = new HashMap<>();
    private static Map<String, Boolean> studentCompletedCourses = new HashMap<>();

    public static void main(String[] args) {
        // Adding courses
        courses.put("Core Java", new Course("Core Java", "None", 5));
        courses.put("Advanced Java", new Course("Advanced Java", "Core Java", 3));

        Scanner scanner = new Scanner(System.in);

        try {
            // User Input
            System.out.print("Enter course to enroll in: ");
            String courseName = scanner.nextLine();

            // Check if course exists
            if (!courses.containsKey(courseName)) {
                System.out.println("Error: Course not found.");
                return;
            }

            Course course = courses.get(courseName);

            // Check prerequisite
            String prerequisite = course.getPrerequisite();
            if (!prerequisite.equals("None") && !studentCompletedCourses.getOrDefault(prerequisite, false)) {
                throw new PrerequisiteNotMetException("Error: PrerequisiteNotMetException - Complete " 
                    + prerequisite + " before enrolling in " + courseName + ".");
            }

            // Enroll student
            course.enrollStudent();
            System.out.println("Enrollment successful in " + courseName + "!");

            // Mark course as completed
            studentCompletedCourses.put(courseName, true);

        } catch (CourseFullException | PrerequisiteNotMetException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println("Error: 
