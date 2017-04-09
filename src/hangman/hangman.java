package hangman;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

/**
 * Hangman - This program is a console version of the game hangman.
 * The user will be printed underscores for every character that they need to guess in the word.
 * Once a letter is guessed it will be printed out in the console in the correct location of the word.
 * If the user makes a wrong guess the user will lose a life. The user starts with 6 lives.
 * 
 * @author Cameron Pickle
 *
 */
public class hangman {
	private int lives = 6;
	private int lettersCorrect = 0;

	private boolean correct = false;
	private boolean reset = false;
	
	private static String[] words = {"Utah", "Mississippi", "Missouri", "Merica", "StarWars", "FourtyTwo", "Eagle", "Icecream", "Mountain", "HelloWorld"};
	private String word;
	private int wordLen;
	private String guess;
	private ArrayList<String> prevGuess;
	private char[] print;
	
	private static Random rand = new Random();
	private Scanner scanner = new Scanner(System.in);

	public hangman() {
		this.word = words[Math.abs(rand.nextInt())%words.length];
		this.wordLen = word.length();
		this.print = new char[wordLen];
		this.prevGuess = new ArrayList<String>();
	}
	
	public static void main(String[] args)
	{
		while (true)
		{
			hangman hangman = new hangman();
			for (int i = 0; i < hangman.wordLen; i++)
				hangman.print[i] = '_';

			while (true)
			{
				hangman.hang();
				if(hangman.reset)
					break;
			}
		}
	}

	/**
	 * The main function of the game. This function starts the game.
	 */
	private void hang()
	{
		//reset correct value to false
		correct = false;

		//print out the letters the user has guessed and underscores for letters user hasn't guessed
		for (int i = 0; i < wordLen; i++)
		{
			System.out.print(print[i] + " ");
		}
		System.out.println("\n");

		//Output game information to user
		System.out.println("(Lives Remaining: " + lives + ")");
		System.out.print("What is your guess? ");
		//get user input
		guess = scanner.next().substring(0, 1);
		
		//Verifies that the user has input a valid character
		for(int i=0; i<prevGuess.size();i++)
			if(guess.toLowerCase().equals(prevGuess.get(i))) //Do not allow duplicate letters
			{
				System.out.println("You already guessed this. Try again.");
				return;
			} else if (guess.isEmpty()) { //Do not allow empty entries
				System.out.println("You didn't enter anything. Try again.");
				return;
			} else if (!Character.isLetter(guess.charAt(0))) { //Only allow alphabetic characters
				System.out.println("This is not a letter. Try again.");
				return;
			}
		
		//add current guess to list of previous guesses
		prevGuess.add(guess.toLowerCase());

		//Check if the characters of the word and check if the user's guess matches any of the letters
		this.updateWord();

		//if user is incorrect decrement user's lives by one
		if (!correct)
			lives--;
		
		//When the user wins
		this.checkWin();

		//When the user loses
		this.checkLose();
	}
	
	/**
	 * Checks if the guessed character is part of the word being guessed. If it is then the guessed letters are updated.
	 */
	private void updateWord()
	{
		for (int i = 0; i < wordLen; i++)
			if (word.substring(i,i+1).toLowerCase().equals(guess.toLowerCase()))
			{
				print[i] = word.charAt(i);
				lettersCorrect++;
				correct = true;
			}
	}
	
	/**
	 * Checks is if the user has won. If the user won it congratulates the user.
	 */
	private void checkWin()
	{
		if (lettersCorrect == wordLen)
		{
			System.out.println("\n");
			//print out the letters the user has guessed and underscores for letters user hasn't guessed
			for (int i = 0; i < wordLen; i++)
			{
				System.out.print(print[i] + " ");
			}
			System.out.println();
			System.out.println("Congratulations! You guessed the word!\n\n");
			reset = true;
		}
	}
	
	/**
	 * Checks if the user has lost. If the user lost it displays the correct word.
	 */
	private void checkLose()
	{
		if (lives < 1)
		{
			System.out.println();
			System.out.println("The word was " + word + " - Try again\n\n");
			reset = true;
		}
	}
}