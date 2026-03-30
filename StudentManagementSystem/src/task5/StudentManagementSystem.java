package task5;

import java.util.*;
import java.io.*;

public class StudentManagementSystem {
    private List<Student> students;
    private final String DATA_FILE = "students.txt";
    
    public StudentManagementSystem() {
        students = new ArrayList<>();
        loadData();
    }
    
    public boolean addStudent(Student student) {
        for (Student s : students) {
            if (s.getRollNumber().equals(student.getRollNumber())) {
                System.out.println("❌ Student with roll number " + student.getRollNumber() + " already exists!");
                return false;
            }
        }
        students.add(student);
        saveData();
        System.out.println("✅ Student added successfully!");
        return true;
    }
    
    public boolean removeStudent(String rollNumber) {
        Iterator<Student> iterator = students.iterator();
        while (iterator.hasNext()) {
            Student student = iterator.next();
            if (student.getRollNumber().equals(rollNumber)) {
                iterator.remove();
                saveData();
                System.out.println("✅ Student removed successfully!");
                return true;
            }
        }
        System.out.println("❌ Student with roll number " + rollNumber + " not found!");
        return false;
    }
    
    public Student searchStudent(String rollNumber) {
        for (Student student : students) {
            if (student.getRollNumber().equals(rollNumber)) {
                return student;
            }
        }
        return null;
    }
    
    public List<Student> searchByName(String name) {
        List<Student> results = new ArrayList<>();
        for (Student student : students) {
            if (student.getName().toLowerCase().contains(name.toLowerCase())) {
                results.add(student);
            }
        }
        return results;
    }
    
    public boolean updateStudent(String rollNumber, Student updatedStudent) {
        for (int i = 0; i < students.size(); i++) {
            if (students.get(i).getRollNumber().equals(rollNumber)) {
                students.set(i, updatedStudent);
                saveData();
                System.out.println("✅ Student information updated successfully!");
                return true;
            }
        }
        System.out.println("❌ Student not found!");
        return false;
    }
    
    public void displayAllStudents() {
        if (students.isEmpty()) {
            System.out.println("📭 No students in the system.");
            return;
        }
        
        System.out.println("\n╔══════════════════════════════════════════════════════════════════╗");
        System.out.println("                     ALL STUDENTS (" + students.size() + ")");
        System.out.println("╠══════════════════════════════════════════════════════════════════╣");
        System.out.printf("%-5s %-15s %-12s %-6s %-20s %-12s%n", 
            "S.No", "Name", "Roll No", "Grade", "Email", "Phone");
        System.out.println("╠══════════════════════════════════════════════════════════════════╣");
        
        int count = 1;
        for (Student student : students) {
            System.out.printf("%-5d %-15s %-12s %-6s %-20s %-12s%n", 
                count++,
                truncate(student.getName(), 15),
                student.getRollNumber(),
                student.getGrade(),
                truncate(student.getEmail(), 20),
                student.getPhone()
            );
        }
        System.out.println("╚══════════════════════════════════════════════════════════════════╝");
    }
    
    private String truncate(String str, int length) {
        if (str == null) return "";
        if (str.length() <= length) return str;
        return str.substring(0, length - 3) + "...";
    }
    
    public void showStatistics() {
        System.out.println("\n📊 SYSTEM STATISTICS");
        System.out.println("══════════════════════");
        System.out.println("Total Students: " + students.size());
        
        if (!students.isEmpty()) {
            Map<String, Integer> gradeCount = new HashMap<>();
            for (Student s : students) {
                gradeCount.put(s.getGrade(), gradeCount.getOrDefault(s.getGrade(), 0) + 1);
            }
            
            System.out.println("Students by Grade:");
            for (Map.Entry<String, Integer> entry : gradeCount.entrySet()) {
                System.out.println("  Grade " + entry.getKey() + ": " + entry.getValue());
            }
        }
    }
    
    private void saveData() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(DATA_FILE))) {
            for (Student student : students) {
                writer.println(student.toString());
            }
        } catch (IOException e) {
            System.out.println("❌ Error saving data: " + e.getMessage());
        }
    }
    
    private void loadData() {
        File file = new File(DATA_FILE);
        if (!file.exists()) {
            return;
        }
        
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    students.add(Student.fromString(line));
                }
            }
        } catch (IOException e) {
            System.out.println("❌ Error loading data: " + e.getMessage());
        }
    }
    
    public List<Student> getAllStudents() {
        return new ArrayList<>(students);
    }
}