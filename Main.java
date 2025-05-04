
import java.util.Scanner;
import java.util.Stack;
class StudentNode {
    int studentID;
    StudentNode nextStudent;

    StudentNode(int studentID) {
        this.studentID = studentID;
        this.nextStudent = null;
    }
}

class CourseNode {
    int courseID;
    CourseNode nextCourse;

    CourseNode(int courseID) {
        this.courseID = courseID;
        this.nextCourse = null;
    }
}

class EnrollmentNode {
    int studentID;
    int courseID;
    EnrollmentNode nextEnrollment;

    EnrollmentNode(int studentID, int courseID) {
        this.studentID = studentID;
        this.courseID = courseID;
        this.nextEnrollment = null;
    }
}

class UniversitySystem {

    private StudentNode studentHead;
    private CourseNode courseHead;
    private EnrollmentNode enrollmentHead;
    private Integer lastStudentID = null;
    private Integer lastCourseID = null;

    private Stack<stack> undoStack = new Stack<>();
    private Stack<stack> redoStack = new Stack<>();


    public UniversitySystem() {
        studentHead = null;
        courseHead = null;
        enrollmentHead = null;
    }

    // Student-related methods
    public StudentNode findStudent(int studentID) {
        StudentNode current = studentHead;
        while (current != null) {
            if (current.studentID == studentID) {
                return current;
            }
            current = current.nextStudent;
        }
        return null; // Not found
    }

    public void addStudent(int studentID) {

        //refuse any id with zero or negative

        if (studentID <=0) {
            System.out.println("INVALID STUDENT ID ");
            return;
        }

        // Check if student already exists

        if (findStudent(studentID) != null) {
            System.out.println("Error: Student ID " + studentID + " already exists.");
            return;
        }

        // If we get here, the ID is unique
        StudentNode newNode = new StudentNode(studentID);
        newNode.nextStudent = studentHead;
        studentHead = newNode;
        lastStudentID = studentID; // track last added
        System.out.println("Student ID " + studentID + " added successfully.");

    }

    public void removeStudent(int studentID) {

        if (studentHead == null) {
            System.out.println("Student list is empty");
            return;
        }

        if (studentHead.studentID == studentID) {
            studentHead = studentHead.nextStudent;
        } else {
            StudentNode current = studentHead;
            while (current.nextStudent != null && current.nextStudent.studentID != studentID) {
                current = current.nextStudent;
            }

            if (current.nextStudent != null) {
                current.nextStudent = current.nextStudent.nextStudent;
            } else {
                System.out.println("Student ID " + studentID + " not found.");
                return;
            }
        }

        // Cascade delete enrollments
        EnrollmentNode curr = enrollmentHead;
        EnrollmentNode prev = null;
        while (curr != null) {
            if (curr.studentID == studentID) {
                if (prev == null) {
                    enrollmentHead = curr.nextEnrollment;
                    curr = enrollmentHead;
                } else {
                    prev.nextEnrollment = curr.nextEnrollment;
                    curr = prev.nextEnrollment;
                }
            } else {
                prev = curr;
                curr = curr.nextEnrollment;
            }
        }

        System.out.println("Student ID " + studentID + " and all their enrollments removed.");
    }


    // Course-related methods
    public CourseNode findCourse(int courseID) {
        CourseNode current = courseHead;
        while (current != null) {
            if (current.courseID == courseID) {
                return current;
            }
            current = current.nextCourse;
        }
        return null; // Not found
    }

    public void addCourse(int courseID) {

        // refuse any id with zero or negative

        if (courseID <=0) {
            System.out.println("INVALID STUDENT ID ");
            return;
        }
        // Check if course already exists
        if (findCourse(courseID) != null) {
            System.out.println("Error: Course ID " + courseID + " already exists.");
            return;
        }

        // If we get here, the ID is unique
        CourseNode newNode = new CourseNode(courseID);
        newNode.nextCourse = courseHead;
        courseHead = newNode;
        lastCourseID = courseID; // track last added

        System.out.println("Course ID " + courseID + " added successfully.");
    }

    public void removeCourse(int courseID) {
        // Check if course list is empty
        if (courseHead == null) {
            System.out.println("Course list is empty");
            return;
        }

        // Check if the first node matches
        if (courseHead.courseID == courseID) {
            courseHead = courseHead.nextCourse;
        } else {
            // Search for the course in the rest of the list
            CourseNode current = courseHead;
            while (current.nextCourse != null && current.nextCourse.courseID != courseID) {
                current = current.nextCourse;
            }

            if (current.nextCourse != null) {
                current.nextCourse = current.nextCourse.nextCourse;
            } else {
                System.out.println("Course ID " + courseID + " not found.");
                return;
            }
        }

        // Cascade delete: Remove related enrollments
        EnrollmentNode curr = enrollmentHead;
        EnrollmentNode prev = null;
        while (curr != null) {
            if (curr.courseID == courseID) {
                if (prev == null) {
                    enrollmentHead = curr.nextEnrollment;
                    curr = enrollmentHead;
                } else {
                    prev.nextEnrollment = curr.nextEnrollment;
                    curr = prev.nextEnrollment;
                }
            } else {
                prev = curr;
                curr = curr.nextEnrollment;
            }
        }

        System.out.println("Course ID " + courseID + " and all related enrollments removed.");
    }



    // Enrollment methods
    public void enrollStudent(int studentID, int courseID) {
        // Check if student exists
        if (findStudent(studentID) == null) {
            System.out.println("Error: Student ID " + studentID + " does not exist.");
            return;
        }

        // Check if course exists
        if (findCourse(courseID) == null) {
            System.out.println("Error: Course ID " + courseID + " does not exist.");
            return;
        }

        // Check if already enrolled
        EnrollmentNode current = enrollmentHead;
        while (current != null) {
            if (current.studentID == studentID && current.courseID == courseID) {
                System.out.println("Student " + studentID + " is already enrolled in course " + courseID);
                return;
            }
            current = current.nextEnrollment;
        }

        // Add the enrollment
        EnrollmentNode newNode = new EnrollmentNode(studentID, courseID);
        newNode.nextEnrollment = enrollmentHead;
        enrollmentHead = newNode;
        undoStack.push(new stack("ENROLL", studentID, courseID));
        redoStack.clear();
        System.out.println("Student " + studentID + " enrolled in course " + courseID + " successfully.");
    }

    public void removeEnrollment(int studentID, int courseID) {
        // Check if enrollment list is empty
        if (enrollmentHead == null) {
            System.out.println("No enrollments exist.");
            return;
        }

        // Check if the first node matches
        if (enrollmentHead.studentID == studentID && enrollmentHead.courseID == courseID) {
            enrollmentHead = enrollmentHead.nextEnrollment;
            System.out.println("Enrollment removed successfully.");
            return;
        }

        // Search for the enrollment in the rest of the list
        EnrollmentNode current = enrollmentHead;
        while (current.nextEnrollment != null &&
                (current.nextEnrollment.studentID != studentID ||
                        current.nextEnrollment.courseID != courseID)) {
            current = current.nextEnrollment;
        }

        // Check if course is full
        if (isFullCourse(courseID)) {
            System.out.println("Error: Course " + courseID + " is full (has 30 students).");
            return;
        }

        if (countCoursesForStudent(studentID) == 7) {
            System.out.println("Error: Student " + studentID + " already has 7 courses and cannot enroll in more.");
            return;
        }

        // Check if we found the enrollment
        if (current.nextEnrollment != null) {
            current.nextEnrollment = current.nextEnrollment.nextEnrollment;
            undoStack.push(new stack("REMOVE", studentID, courseID));
            redoStack.clear();
            System.out.println("Enrollment removed successfully.");
        } else {
            System.out.println("Enrollment not found.");
        }
    }

    public void listCoursesByStudent(int studentID) {
        // Check if student exists
        if (findStudent(studentID) == null) {
            System.out.println("Error: Student ID " + studentID + " does not exist.");
            return;
        }

        System.out.println("Courses for Student " + studentID + ":");
        boolean found = false;
        EnrollmentNode curr = enrollmentHead;

        while (curr != null) {
            if (curr.studentID == studentID) {
                System.out.println("  Course " + curr.courseID);
                found = true;
            }
            curr = curr.nextEnrollment;
        }

        if (!found) {
            System.out.println("  No courses found for this student.");
        }
    }

    public void listStudentsByCourse(int courseID) {
        // Check if course exists
        if (findCourse(courseID) == null) {
            System.out.println("Error: Course ID " + courseID + " does not exist.");
            return;
        }

        System.out.println("Students in Course " + courseID + ":");
        boolean found = false;
        EnrollmentNode curr = enrollmentHead;

        while (curr != null) {
            if (curr.courseID == courseID) {
                System.out.println("  Student " + curr.studentID);
                found = true;
            }
            curr = curr.nextEnrollment;
        }

        if (!found) {
            System.out.println("  No students enrolled in this course.");
        }
    }

    // Sort Students by Course
    public void sortStudentsByCourse(int courseID) {
        // Check if course exists
        if (findCourse(courseID) == null) {
            System.out.println("Error: Course ID " + courseID + " does not exist.");
            return;
        }

        EnrollmentNode sorted = null;  // local sorted list
        EnrollmentNode curr = enrollmentHead;
        boolean found = false;

        while (curr != null) {
            if (curr.courseID == courseID) {
                found = true;
                // Create a copy of the node
                EnrollmentNode newNode = new EnrollmentNode(curr.studentID, courseID);

                // Insert into sorted list (by studentID)
                if (sorted == null || newNode.studentID < sorted.studentID) {
                    newNode.nextEnrollment = sorted;
                    sorted = newNode;
                } else {
                    EnrollmentNode temp = sorted;
                    while (temp.nextEnrollment != null && temp.nextEnrollment.studentID < newNode.studentID) {
                        temp = temp.nextEnrollment;
                    }
                    newNode.nextEnrollment = temp.nextEnrollment;
                    temp.nextEnrollment = newNode;
                }
            }
            curr = curr.nextEnrollment;
        }

        // Print the sorted list
        System.out.println("Sorted students in course " + courseID + ":");
        if (!found) {
            System.out.println("  No students enrolled in this course.");
            return;
        }

        EnrollmentNode temp = sorted;
        while (temp != null) {
            System.out.println("  Student ID: " + temp.studentID);
            temp = temp.nextEnrollment;
        }
    }

    // Sort Courses by Student
    public void sortCoursesByStudent(int studentID) {
        // Check if student exists
        if (findStudent(studentID) == null) {
            System.out.println("Error: Student ID " + studentID + " does not exist.");
            return;
        }

        EnrollmentNode sorted = null;  // local sorted list
        EnrollmentNode curr = enrollmentHead;
        boolean found = false;

        while (curr != null) {
            if (curr.studentID == studentID) {
                found = true;
                // Create a copy of the node
                EnrollmentNode newNode = new EnrollmentNode(studentID, curr.courseID);

                // Insert into sorted list (by courseID)
                if (sorted == null || newNode.courseID < sorted.courseID) {
                    newNode.nextEnrollment = sorted;
                    sorted = newNode;
                } else {
                    EnrollmentNode temp = sorted;
                    while (temp.nextEnrollment != null && temp.nextEnrollment.courseID < newNode.courseID) {
                        temp = temp.nextEnrollment;
                    }
                    newNode.nextEnrollment = temp.nextEnrollment;
                    temp.nextEnrollment = newNode;
                }
            }
            curr = curr.nextEnrollment;
        }

        // Print the sorted list
        System.out.println("Sorted courses for student " + studentID + ":");
        if (!found) {
            System.out.println("  Student is not enrolled in any courses.");
            return;
        }

        EnrollmentNode temp = sorted;
        while (temp != null) {
            System.out.println("  Course ID: " + temp.courseID);
            temp = temp.nextEnrollment;
        }
    }

    public boolean isFullCourse(int courseID) {
        int count = 0;
        EnrollmentNode temp = enrollmentHead;
        while (temp != null) {
            if (temp.courseID == courseID) {
                count++;
            }
            temp = temp.nextEnrollment;
        }

        return count == 30;

    }

    public boolean isNormalStudent(int studentID) {
        int count = 0;
        EnrollmentNode temp = enrollmentHead;
        while (temp != null) {
            if (temp.studentID == studentID) {
                count++;
            }
            temp = temp.nextEnrollment;
        }
        if (count<=7 && count>=2) {
            System.out.println("True");
        }else
            System.out.println("False");

        System.out.println("The Student whose id is " + studentID + " registered " + count+" courses.");
        return count <= 7;
    }

    private int countCoursesForStudent(int studentID) {
        int count = 0;
        EnrollmentNode temp = enrollmentHead;
        while (temp != null) {
            if (temp.studentID == studentID) {
                count++;
            }
            temp = temp.nextEnrollment;
        }
        return count;
    }

    public Integer getLastStudentAdded() {
        if (lastStudentID == null) {
            System.out.println("No students have been added yet.");
            return null;
        }
        System.out.println("Last student added: " + lastStudentID);
        return lastStudentID;
    }





    public Integer getLastCourseAdded() {
        if (lastCourseID == null) {
            System.out.println("No courses have been added yet.");
            return null;
        }
        System.out.println("Last course added: " + lastCourseID);
        return lastCourseID;
    }
    class stack{
        String type;  //mn da bn3rf n3ml enroll wla removee
        int studentID;
        int courseID;
        stack(String type,int studentID,   int courseID){
            this.type=type;
            this.courseID=courseID;
            this.studentID=studentID;

        }
    }
    public void undo(){

        if(undoStack.isEmpty()){
            System.out.println("nothing to undo");
            return;
        }
        stack last=  undoStack.pop();
        // lw el last student enrolled undo it and save it f el redo push
        if(last.type.equals("ENROLL")){
            removeEnrollment(last.studentID,last.courseID);
            redoStack.push(new stack("ENROLL", last.studentID, last.courseID));
            // if the action removal ,re enroll student and push to redostack
        }else if (last.type.equals("REMOVE")){
            enrollStudent(last.studentID, last.courseID);
            redoStack.push(new stack("REMOVE", last.studentID, last.courseID));
        }}

    //check if no thing to undo
    public void redo(){
        if (redoStack.isEmpty()) {
            System.out.println("Nothing to redo.");
            return;
        }

        stack  last = redoStack.pop();

        //  If it's an enroll action, perform it again and log it back into undo.
        if (last.type.equals("ENROLL")) {
            enrollStudent(last.studentID, last.courseID);
            undoStack.push(new stack("ENROLL", last.studentID, last.courseID));
            //     If its a remove action reremove and push into undoStack
        } else if (last.type.equals("REMOVE")) {
            removeEnrollment(last.studentID, last.courseID);
            undoStack.push(new stack("REMOVE", last.studentID, last.courseID));
        }
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        UniversitySystem system = new UniversitySystem();

        int choice;
        do {
            System.out.println("\n--- University Course Registration System ---");
            System.out.println("1. Add Student");
            System.out.println("2. Add Course");
            System.out.println("3. Remove Student");
            System.out.println("4. Remove Course");
            System.out.println("5. Get Last Student Added");
            System.out.println("6. Get Last Course Added");
            System.out.println("7. Enroll Student in Course");
            System.out.println("8. Remove Enrollment");
            System.out.println("9. List Courses by Student");
            System.out.println("10. List Students by Course");
            System.out.println("11. Sort Students by Course");
            System.out.println("12. Sort Courses by Student");
            System.out.println("13. is full Course");
            System.out.println("14. is normal Student");
            System.out.println("15. Undo Last Move");
            System.out.println("16. Redo Last Move");
            System.out.println("0. Exit");

            System.out.print("Enter choice: ");
            choice = scanner.nextInt();

            int studentID, courseID;

            switch (choice) {
                case 1:
                    System.out.print("Enter Student ID: ");
                    studentID = scanner.nextInt();
                    system.addStudent(studentID);
                    break;
                case 2:
                    System.out.print("Enter Course ID: ");
                    courseID = scanner.nextInt();
                    system.addCourse(courseID);
                    break;

                case 3:
                    System.out.print("Enter Student ID: ");
                    studentID = scanner.nextInt();
                    system.removeStudent(studentID);
                    break;
                case 4:
                    System.out.print("Enter Course ID: ");
                    courseID = scanner.nextInt();
                    system.removeCourse(courseID);
                    break;
                case 5:
                    system.getLastStudentAdded();
                    break;
                case 6:
                    system.getLastCourseAdded();
                    break;
                case 7:
                    System.out.print("Enter Student ID: ");
                    studentID = scanner.nextInt();
                    System.out.print("Enter Course ID: ");
                    courseID = scanner.nextInt();
                    system.enrollStudent(studentID, courseID);
                    break;
                case 8:
                    System.out.print("Enter Student ID: ");
                    studentID = scanner.nextInt();
                    System.out.print("Enter Course ID: ");
                    courseID = scanner.nextInt();
                    system.removeEnrollment(studentID, courseID);
                    break;
                case 9:
                    System.out.print("Enter Student ID: ");
                    studentID = scanner.nextInt();
                    system.listCoursesByStudent(studentID);
                    break;
                case 10:
                    System.out.print("Enter Course ID: ");
                    courseID = scanner.nextInt();
                    system.listStudentsByCourse(courseID);
                    break;
                case 11:
                    System.out.print("Enter Course ID: ");
                    courseID = scanner.nextInt();
                    system.sortStudentsByCourse(courseID);
                    break;
                case 12:
                    System.out.print("Enter Student ID: ");
                    studentID = scanner.nextInt();
                    system.sortCoursesByStudent(studentID);
                    break;
                case 13:
                    System.out.print("Enter Course ID: ");
                    courseID = scanner.nextInt();
                    system.isFullCourse(courseID);
                    boolean isFull = system.isFullCourse(courseID);
                    if (isFull) {
                        System.out.println("Course " + courseID + " is full");
                    } else {
                        System.out.println("Course " + courseID + " is not full");
                    }
                    break;
                case 14:
                    System.out.print("Enter Student ID: ");
                    studentID = scanner.nextInt();
                    system.isNormalStudent(studentID);
                    break;
                case 15:
                    system.undo();
                    break;
                case 16:
                    system.redo();
                    break;
                case 0:
                    System.out.println("Exiting program.");
                    break;
                default:
                    System.out.println("Invalid choice.");
                    break;
            }
        } while (choice != 0);

        scanner.close();
    }
}
