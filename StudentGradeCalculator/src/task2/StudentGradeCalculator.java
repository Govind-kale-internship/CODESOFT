package task2;

import java.util.Scanner;

public class StudentGradeCalculator 
{

	public static void main(String[] args) 
	{
		 Scanner sc = new Scanner(System.in);
		 
		 System.out.println(" \n||     STUDENT GRADE CALCULATOR     ||");
		 System.out.println("_______________________________________________");
		 
		 int numSubjects = getNumberOfSubject(sc);
		 
		 int totalMarks = 0;
		 
		 System.out.println("\nEnter marks for each subject (out of 100) : ");
		 System.out.println("_______________________________________________");
		 
		 for(int i = 1; i <= numSubjects; i++)
		 {
			 totalMarks += getValidMarks(sc, i);
		 }
		 
		 double averagePercentage = (double) totalMarks / numSubjects;
		 
		 String grade = calculateGrade(averagePercentage);
		 
		 displayResults(totalMarks, numSubjects, averagePercentage, grade);
		 
		 sc.close();

	}
	
	 private static int getNumberOfSubject(Scanner sc)
	 {
		 int numSubjects;
		 
		 while(true)
		 {
			 System.out.print("Enter number of subjects : ");
			 numSubjects = sc.nextInt();
			 
			 if(numSubjects > 0)
			 {
				 break;
			 }
			 System.out.println("Please enter a valid positive number!");
		 }
		 
		 return numSubjects;
	 }
	 
	 private static int getValidMarks(Scanner sc, int subjectNumber)
	 {
		 while(true)
		 {
			 System.out.print("Subject " + subjectNumber + " : ");
			 int marks = sc.nextInt();
			 
			 if(marks >= 0 && marks <= 100)
			 {
				 return marks;
			 }
			 
			 System.out.println("Invalid! Marks must be between 0-100");
		 }
	 }
	 
	 private static String calculateGrade(double percentage)
	 {
		 if(percentage >= 90) return "A+ (Outstanding)";
		 else if(percentage >= 80) return "A (Excellent)";
		 else if(percentage >= 70) return "B (Very Good)";
		 else if(percentage >= 60) return "C (Good)";
		 else if(percentage >= 50) return "D (Satisfactory)";
		 else if(percentage >= 40) return "E (Pass)";
		 else return "F (Fail)";
	 }
	 
	 private static void displayResults(int totalMarks, int numSubjects, double percentage, String grade)
	 {
		 System.out.println("\n||        FINAL RESULTS        ||");
		 System.out.println("_______________________________________________");
		 
		 System.out.println("Total Marks        : " + totalMarks + "/" + numSubjects * 100);
		 System.out.printf("Average Percentage : %.2f%%%n", percentage);
		 System.out.println("Grade              : " + grade);
		 
		 System.out.println("\nFeedback : " + getFeedbackMessage(grade));
		  
	 }
	 
	 private static String getFeedbackMessage(String grade)
	 {
		 if(grade.startsWith("A"))
		 {
			 return "Excellent work! Keep shining!";
		 }
		 else if(grade.startsWith("B") || grade.startsWith("C"))
		 {
			 return "Good job! Keep improving!";
		 }
		 else if(grade.startsWith("D") || grade.startsWith("E"))
		 {
			 return "You passed! Work harder for better grades!";
		 }
		 else
		 {
			 return "Failed! Need improvement. Don't give up! Practice more!";
		 }
	 } 
	 
}
