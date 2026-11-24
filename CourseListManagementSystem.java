import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;

class Course {
    private int id;
    private String name;
    private String instructor;
    private int credits;

    public Course(int id, String name, String instructor, int credits) {
        this.id = id;
        this.name = name;
        this.instructor = instructor;
        this.credits = credits;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getInstructor() {
        return instructor;
    }

    public int getCredits() {
        return credits;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setInstructor(String instructor) {
        this.instructor = instructor;
    }

    public void setCredits(int credits) {
        this.credits = credits;
    }

    public void display() {
        System.out.printf("%-8d %-25s %-22s %-8d%n",
                id, name, instructor, credits);
    }
}

public class CourseListManagementSystem {
    private static final ArrayList<Course> courses = new ArrayList<>();
    private static final Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        int choice;

        do {
            System.out.println("\n===== COURSE LIST MANAGEMENT SYSTEM =====");
            System.out.println("1. Add Course");
            System.out.println("2. Display All Courses");
            System.out.println("3. Search Course");
            System.out.println("4. Sort Courses by Name");
            System.out.println("5. Update Course");
            System.out.println("6. Delete Course");
            System.out.println("7. Exit");
            choice = readInt("Enter your choice: ");

            switch (choice) {
                case 1:
                    addCourse();
                    break;
                case 2:
                    displayCourses();
                    break;
                case 3:
                    searchCourse();
                    break;
                case 4:
                    sortCourses();
                    break;
                case 5:
                    updateCourse();
                    break;
                case 6:
                    deleteCourse();
                    break;
                case 7:
                    System.out.println("Thank you for using the system!");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 7);

        sc.close();
    }

    private static int readInt(String message) {
        while (true) {
            System.out.print(message);
            try {
                return Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid whole number.");
            }
        }
    }

    private static String readText(String message) {
        while (true) {
            System.out.print(message);
            String value = sc.nextLine().trim();
            if (!value.isEmpty()) {
                return value;
            }
            System.out.println("This field cannot be empty.");
        }
    }

    private static Course findById(int id) {
        for (Course course : courses) {
            if (course.getId() == id) {
                return course;
            }
        }
        return null;
    }

    private static void addCourse() {
        int id = readInt("Enter course ID: ");

        if (findById(id) != null) {
            System.out.println("A course with this ID already exists.");
            return;
        }

        String name = readText("Enter course name: ");
        String instructor = readText("Enter instructor name: ");
        int credits;

        do {
            credits = readInt("Enter course credits: ");
            if (credits <= 0) {
                System.out.println("Credits must be greater than zero.");
            }
        } while (credits <= 0);

        courses.add(new Course(id, name, instructor, credits));
        System.out.println("Course added successfully.");
    }

    private static void displayCourses() {
        if (courses.isEmpty()) {
            System.out.println("No courses available.");
            return;
        }

        System.out.printf("%-8s %-25s %-22s %-8s%n",
                "ID", "COURSE NAME", "INSTRUCTOR", "CREDITS");
        System.out.println("-------------------------------------------------------------------");

        for (Course course : courses) {
            course.display();
        }
    }

    private static void searchCourse() {
        if (courses.isEmpty()) {
            System.out.println("No courses available to search.");
            return;
        }

        System.out.println("1. Search by ID");
        System.out.println("2. Search by name");
        int option = readInt("Choose search type: ");

        if (option == 1) {
            int id = readInt("Enter course ID: ");
            Course course = findById(id);

            if (course != null) {
                System.out.println("Course found:");
                System.out.printf("%-8s %-25s %-22s %-8s%n",
                        "ID", "COURSE NAME", "INSTRUCTOR", "CREDITS");
                course.display();
            } else {
                System.out.println("Course not found.");
            }
        } else if (option == 2) {
            String name = readText("Enter course name: ");
            boolean found = false;

            for (Course course : courses) {
                if (course.getName().equalsIgnoreCase(name)) {
                    if (!found) {
                        System.out.printf("%-8s %-25s %-22s %-8s%n",
                                "ID", "COURSE NAME", "INSTRUCTOR", "CREDITS");
                    }
                    course.display();
                    found = true;
                }
            }

            if (!found) {
                System.out.println("Course not found.");
            }
        } else {
            System.out.println("Invalid search option.");
        }
    }

    private static void sortCourses() {
        if (courses.isEmpty()) {
            System.out.println("No courses available to sort.");
            return;
        }

        courses.sort(Comparator.comparing(
                course -> course.getName().toLowerCase()
        ));
        System.out.println("Courses sorted alphabetically by name.");
        displayCourses();
    }

    private static void updateCourse() {
        int id = readInt("Enter the ID of the course to update: ");
        Course course = findById(id);

        if (course == null) {
            System.out.println("Course not found.");
            return;
        }

        System.out.println("Enter the new details:");
        course.setName(readText("New course name: "));
        course.setInstructor(readText("New instructor name: "));

        int credits;
        do {
            credits = readInt("New course credits: ");
            if (credits <= 0) {
                System.out.println("Credits must be greater than zero.");
            }
        } while (credits <= 0);

        course.setCredits(credits);
        System.out.println("Course updated successfully.");
    }

    private static void deleteCourse() {
        int id = readInt("Enter the ID of the course to delete: ");
        Course course = findById(id);

        if (course == null) {
            System.out.println("Course not found.");
            return;
        }

        courses.remove(course);
        System.out.println("Course deleted successfully.");
    }
}