package task5;

import java.io.Serializable;

public class Student implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	private String name;
	private String rollNumber;
	private String grade;
	private String email;
	private String phone;
	private String address;
	
	public Student(String name, String rollNumber, String grade, String email, String phone, String address) {
        this.name = name;
        this.rollNumber = rollNumber;
        this.grade = grade;
        this.email = email;
        this.phone = phone;
        this.address = address;
    }
    
    public String getName() { return name; }
    public String getRollNumber() { return rollNumber; }
    public String getGrade() { return grade; }
    public String getEmail() { return email; }
    public String getPhone() { return phone; }
    public String getAddress() { return address; }
    
    public void setName(String name) { this.name = name; }
    public void setRollNumber(String rollNumber) { this.rollNumber = rollNumber; }
    public void setGrade(String grade) { this.grade = grade; }
    public void setEmail(String email) { this.email = email; }
    public void setPhone(String phone) { this.phone = phone; }
    public void setAddress(String address) { this.address = address; }
    
    public void displayInfo() {
        System.out.println("╔══════════════════════════════════════════╗");
        System.out.println("           STUDENT DETAILS");
        System.out.println("╠══════════════════════════════════════════╣");
        System.out.printf("  Name        : %s%n", name);
        System.out.printf("  Roll Number : %s%n", rollNumber);
        System.out.printf("  Grade       : %s%n", grade);
        System.out.printf("  Email       : %s%n", email);
        System.out.printf("  Phone       : %s%n", phone);
        System.out.printf("  Address     : %s%n", address);
        System.out.println("╚══════════════════════════════════════════╝");
    }
    
    @Override
    public String toString() {
        return String.format("%s,%s,%s,%s,%s,%s", name, rollNumber, grade, email, phone, address);
    }
    
    public static Student fromString(String data) {
        String[] parts = data.split(",");
        return new Student(parts[0], parts[1], parts[2], parts[3], parts[4], parts[5]);
    }

}
