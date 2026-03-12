package task1;

import java.util.Random;
import java.util.Scanner;

public class NumberGame 
{
	private static final int MAX_ATTEMPTS = 10;
	private static int totalScore = 0;
	private static int roundsWon = 0;

	public static void main(String[] args) 
	{
		Scanner sc = new Scanner(System.in);
		Random random = new Random();
		boolean playAgain = true;
		
		System.out.println("    *WELCOME TO THE NUMBER GAME*");
		System.out.println("____________________________________________");
	
		while(playAgain)
		{
			playRound(sc, random);
			
			System.out.println("\nDo you want to play again? (yes/no) : ");
			
			String response = sc.next().toLowerCase();
			playAgain = response.equals("yes") || response.equals("y");
		}
		
		System.out.println("\nGAME OVER - FINAL SCORE ");
		System.out.println("Rounds won : " + roundsWon);
		System.out.println("TOtal score : " + totalScore);
		System.out.println("Thanks for playing....!");
		
		 
		
		sc.close();

	}
	
	private static void playRound(Scanner sc, Random random)
	{
		System.out.println("\n ------- New Round -------");
		
		System.out.println("Enter minimum value : ");
		int min = sc.nextInt();
		
		System.out.println("Enter maximum value :");
		int max = sc.nextInt();
		
		if(min > max)
		{
			System.out.println("Invalid range! Using default range 1 to 100.");
			min = 1;
			max = 100;
		}
		
		int targetNumber = random.nextInt(max - min + 1) + min;
		int attempts = 0;
		boolean guessedCorrectly = false;
		
		System.out.println("\n I have picked a number between " + min + " and " + max);
		
		System.out.println("You have " + MAX_ATTEMPTS + " attempts. Good Luck! \n");
		
		while(attempts < MAX_ATTEMPTS && !guessedCorrectly)
		{
			System.out.println("Attempt " + (attempts + 1) + "/ " + MAX_ATTEMPTS + " - Enter you guess: ");
			
			int guess = sc.nextInt();
			
			attempts++;
			
			if(guess < min || guess > max)
			{
				System.out.println("Please enter a number between " + min + " and " + max);
				
				attempts--;
				
				continue;
			}
			
			if(guess == targetNumber)
			{
				System.out.println("CORRECT! You guesed it in " + attempts + " attempts!");
				
				guessedCorrectly = true;
				
				int roundScore = (MAX_ATTEMPTS - attempts + 1) * 10;
				totalScore += roundScore;
				roundsWon++;
				
				System.out.println("Round Score : " + roundScore);
				
			} 
			else if(guess < targetNumber)
			{
				System.out.println("Too low! Try again.");
			}
			else
			{
				System.out.println("Too high! Try again.");
			}
			
			if(attempts == MAX_ATTEMPTS - 1 && !guessedCorrectly)
			{
				System.out.println("Tip: Last attempt! Think carefully.");
			}	
		}
		
		if(!guessedCorrectly)
		{
			System.out.println("\n Sorry, you have used all " + MAX_ATTEMPTS + " attempts.");
			
			System.out.println("The correct number was: " + targetNumber);
		}
	}

}
