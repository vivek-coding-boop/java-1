import java.io.*;
import java.util.*;

// ------------------- Student Class (for Serialization) -------------------
class Student implements Serializable {
    private static final long serialVersionUID = 1L;
    int studentID;
    String name;
    String grade;

    public Student(int studentID, String name, String grade) {
        this.studentID = studentID;
        this.name = name;
        this.grade = grade;
    }

    public void display() {
        System.out.println("Student ID: " + studentID);
        System.out.println("Name: " + name);
        System.out.println("Grade: " + grade);
    }
}

// ------------------- Employee Class (for File Handling) -------------------
class Employee {
    int id;
    String name;
    String designation;
    double salary;

    public Employee(int id, String name, String designation, double salary) {
        this.id = id;
        this.name = name;
        this.designation = designation;
        this.salary = salary;
    }

    @Override
    public String toString() {
        return id + "," + name + "," + designation + "," + salary;
    }
}

// ------------------- Main Program -------------------
public class Main {
    private static final String EMPLOYEE_FILE = "employees.txt";
    private static final String STUDENT_FILE = "student.ser";
    private static final Scanner sc = new Scanner(System.in);

    // -------- Part A: Autoboxing Sum --------
    public static void sumUsingAutoboxing() {
        ArrayList<Integer> numbers = new ArrayList<>();
        System.out.println("Enter integers (type 'done' to stop):");

        while (true) {
            String input = sc.next();
            if (input.equalsIgnoreCase("done")) break;
            try {
                int num = Integer.parseInt(input); // parse string
                numbers.add(num); // autoboxing
            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Try again.");
            }
        }

        int sum = 0;
        for (Integer n : numbers) {
            sum += n; // unboxing
        }

        System.out.println("The sum of entered integers is: " + sum);
    }

    // -------- Part B: Student Serialization --------
    public static void serializeStudent() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(STUDENT_FILE))) {
            System.out.print("Enter Student ID: ");
            int id = sc.nextInt();
            sc.nextLine(); // consume newline
            System.out.print("Enter Student Name: ");
            String name = sc.nextLine();
            System.out.print("Enter Grade: ");
            String grade = sc.nextLine();

            Student s = new Student(id, name, grade);
            out.writeObject(s);

            System.out.println("Student serialized successfully!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void deserializeStudent() {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(STUDENT_FILE))) {
            Student s = (Student) in.readObject();
            System.out.println("\nDeserialized Student Object:");
            s.display();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("No student data found! Serialize first.");
        }
    }

    // -------- Part C: Employee Management --------
    public static void addEmployee() {
        try (FileWriter fw = new FileWriter(EMPLOYEE_FILE, true);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw)) {

            System.out.print("Enter Employee ID: ");
            int id = sc.nextInt();
            sc.nextLine();
            System.out.print("Enter Name: ");
            String name = sc.nextLine();
            System.out.print("Enter Designation: ");
            String designation = sc.nextLine();
            System.out.print("Enter Salary: ");
            double salary = sc.nextDouble();

            Employee emp = new Employee(id, name, designation, salary);
            out.println(emp.toString());

            System.out.println("Employee added successfully!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void displayEmployees() {
        try (BufferedReader br = new BufferedReader(new FileReader(EMPLOYEE_FILE))) {
            String line;
            System.out.println("\n--- Employee Records ---");
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                System.out.println("ID: " + data[0] + ", Name: " + data[1] +
                        ", Designation: " + data[2] + ", Salary: " + data[3]);
            }
            System.out.println("------------------------");
        } catch (IOException e) {
            System.out.println("No employee records found!");
        }
    }

    // -------- Main Menu --------
    public static void main(String[] args) {
        while (true) {
            System.out.println("\n===== Main Menu =====");
            System.out.println("1. Sum of Integers (Autoboxing/Unboxing)");
            System.out.println("2. Serialize Student");
            System.out.println("3. Deserialize Student");
            System.out.println("4. Add Employee");
            System.out.println("5. Display Employees");
            System.out.println("6. Exit");
            System.out.print("Choose an option: ");
            int choice = sc.nextInt();

            switch (choice) {
                case 1 -> sumUsingAutoboxing();
                case 2 -> serializeStudent();
                case 3 -> deserializeStudent();
                case 4 -> addEmployee();
                case 5 -> displayEmployees();
                case 6 -> {
                    System.out.println("Exiting program...");
                    sc.close();
                    System.exit(0);
                }
                default -> System.out.println("Invalid choice, try again!");
            }
        }
    }
}
