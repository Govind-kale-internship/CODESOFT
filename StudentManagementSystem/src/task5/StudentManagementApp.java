package task5;

import java.util.*;

public class StudentManagementApp {
    private static StudentManagementSystem sms;
    private static Scanner scanner;
    
    public static void main(String[] args) {
        sms = new StudentManagementSystem();
        scanner = new Scanner(System.in);
        
        printWelcomeMessage();
        
        while (true) {
            printMenu();
            int choice = getChoice();
            
            switch (choice) {
                case 1:
                    addNewStudent();
                    break;
                case 2:
                    editStudent();
                    break;
                case 3:
                    searchStudent();
                    break;
                case 4:
                    sms.displayAllStudents();
                    break;
                case 5:
                    removeStudent();
                    break;
                case 6:
                    sms.showStatistics();
                    break;
                case 7:
                    exitApplication();
                    return;
                default:
                    System.out.println("❌ Invalid option! Please try again.");
            }
        }
    }
    
    private static void printWelcomeMessage() {
        System.out.println("╔══════════════════════════════════════════╗");
        System.out.println("║     STUDENT MANAGEMENT SYSTEM v1.0       ║");
        System.out.println("║              📚 MANAGE  📚               ║");
        System.out.println("╚══════════════════════════════════════════╝");
    }
    
    private static void printMenu() {
        System.out.println("\n╔══════════════════════════════════════════╗");
        System.out.println("║                 MENU                     ║");
        System.out.println("╠══════════════════════════════════════════╣");
        System.out.println("║  1. Add New Student                      ║");
        System.out.println("║  2. Edit Student                         ║");
        System.out.println("║  3. Search Student                       ║");
        System.out.println("║  4. Display All Students                 ║");
        System.out.println("║  5. Remove Student                       ║");
        System.out.println("║  6. Show Statistics                      ║");
        System.out.println("║  7. Exit                                 ║");
        System.out.println("╚══════════════════════════════════════════╝");
        System.out.print("👉 Enter your choice: ");
    }
    
    private static int getChoice() {
        try {
            return scanner.nextInt();
        } catch (InputMismatchException e) {
            scanner.next();
            return 0;
        }
    }
    
    private static void addNewStudent() {
        System.out.println("\n📝 ADD NEW STUDENT");
        System.out.println("═══════════════════");
        scanner.nextLine();
        
        String name = getValidInput("Enter name: ", true);
        String rollNumber = getValidInput("Enter roll number: ", true);
        String grade = getValidInput("Enter grade: ", true);
        String email = getValidEmail();
        String phone = getValidPhone();
        String address = getValidInput("Enter address: ", false);
        
        Student student = new Student(name, rollNumber, grade, email, phone, address);
        sms.addStudent(student);
    }
    
    private static void editStudent() {
        System.out.println("\n✏️ EDIT STUDENT");
        System.out.println("════════════════");
        scanner.nextLine();
        
        System.out.print("Enter roll number of student to edit: ");
        String rollNumber = scanner.nextLine();
        
        Student existing = sms.searchStudent(rollNumber);
        if (existing == null) {
            System.out.println("❌ Student not found!");
            return;
        }
        
        System.out.println("\n📋 Current Information:");
        existing.displayInfo();
        
        System.out.println("\n📝 Enter new information (press Enter to keep current):");
        
        String name = getValidInputWithDefault("Enter name [" + existing.getName() + "]: ", existing.getName(), true);
        String grade = getValidInputWithDefault("Enter grade [" + existing.getGrade() + "]: ", existing.getGrade(), true);
        String email = getValidEmailWithDefault(existing.getEmail());
        String phone = getValidPhoneWithDefault(existing.getPhone());
        String address = getValidInputWithDefault("Enter address [" + existing.getAddress() + "]: ", existing.getAddress(), false);
        
        Student updated = new Student(name, rollNumber, grade, email, phone, address);
        sms.updateStudent(rollNumber, updated);
    }
    
    private static void searchStudent() {
        System.out.println("\n🔍 SEARCH STUDENT");
        System.out.println("══════════════════");
        System.out.println("1. Search by Roll Number");
        System.out.println("2. Search by Name");
        System.out.print("Choose option: ");
        
        int option = getChoice();
        scanner.nextLine();
        
        if (option == 1) {
            System.out.print("Enter roll number: ");
            String rollNumber = scanner.nextLine();
            Student student = sms.searchStudent(rollNumber);
            if (student != null) {
                student.displayInfo();
            } else {
                System.out.println("❌ Student not found!");
            }
        } else if (option == 2) {
            System.out.print("Enter name to search: ");
            String name = scanner.nextLine();
            List<Student> results = sms.searchByName(name);
            if (results.isEmpty()) {
                System.out.println("❌ No students found with name containing: " + name);
            } else {
                System.out.println("\n📋 Search Results (" + results.size() + " found):");
                for (Student s : results) {
                    s.displayInfo();
                }
            }
        } else {
            System.out.println("❌ Invalid option!");
        }
    }
    
    private static void removeStudent() {
        System.out.println("\n🗑️ REMOVE STUDENT");
        System.out.println("══════════════════");
        scanner.nextLine();
        System.out.print("Enter roll number of student to remove: ");
        String rollNumber = scanner.nextLine();
        
        System.out.print("Are you sure? (yes/no): ");
        String confirm = scanner.nextLine().toLowerCase();
        if (confirm.equals("yes") || confirm.equals("y")) {
            sms.removeStudent(rollNumber);
        } else {
            System.out.println("❌ Removal cancelled.");
        }
    }
    
    private static void exitApplication() {
        System.out.println("\n✅ Thank you for using Student Management System!");
        System.out.println("   Goodbye! 👋");
        scanner.close();
    }
    
    private static String getValidInput(String prompt, boolean required) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            
            if (!required || !input.isEmpty()) {
                return input;
            }
            System.out.println("❌ This field cannot be empty!");
        }
    }
    
    private static String getValidInputWithDefault(String prompt, String defaultValue, boolean required) {
        System.out.print(prompt);
        String input = scanner.nextLine().trim();
        
        if (input.isEmpty()) {
            return defaultValue;
        }
        return input;
    }
    
    private static String getValidEmail() {
        while (true) {
            System.out.print("Enter email: ");
            String email = scanner.nextLine().trim();
            
            if (email.isEmpty()) {
                return "N/A";
            }
            
            if (email.contains("@") && email.contains(".")) {
                return email;
            }
            System.out.println("❌ Please enter a valid email address (e.g., name@domain.com)!");
        }
    }
    
    private static String getValidEmailWithDefault(String defaultValue) {
        while (true) {
            System.out.print("Enter email [" + defaultValue + "]: ");
            String email = scanner.nextLine().trim();
            
            if (email.isEmpty()) {
                return defaultValue;
            }
            
            if (email.contains("@") && email.contains(".")) {
                return email;
            }
            System.out.println("❌ Please enter a valid email address!");
        }
    }
    
    private static String getValidPhone() {
        while (true) {
            System.out.print("Enter phone number: ");
            String phone = scanner.nextLine().trim();
            
            if (phone.isEmpty()) {
                return "N/A";
            }
            
            if (phone.matches("\\d{10}")) {
                return phone;
            }
            System.out.println("❌ Please enter a valid 10-digit phone number!");
        }
    }
    
    private static String getValidPhoneWithDefault(String defaultValue) {
        while (true) {
            System.out.print("Enter phone number [" + defaultValue + "]: ");
            String phone = scanner.nextLine().trim();
            
            if (phone.isEmpty()) {
                return defaultValue;
            }
            
            if (phone.matches("\\d{10}")) {
                return phone;
            }
            System.out.println("❌ Please enter a valid 10-digit phone number!");
        }
    }
}