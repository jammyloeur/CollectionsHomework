import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

/*
 * SD2x Homework #3
 * Implement the methods below according to the specification in the assignment description.
 * Please be sure not to change the method signatures!
 */
public class Analyzer {
	
	/*
	 * Check whether String is an Integer
	 */
	public static boolean isInteger(String s) {
		return isInteger(s, 10);
	}
	
	public static boolean isInteger(String s, int radix) {
		if (s.isEmpty()) return false;
		for (int i = 0; i < s.length(); i++) {
			if (i == 0 && s.charAt(i) == '-') {
				if (s.length() == 1) return false;
				else continue;
			}
			if (Character.digit(s.charAt(i), radix) < 0) return false;
		}
		return true;
	}
	
	/*
	 * Implement this method in Part 1
	 */
	public static List<Sentence> readFile(String filename) {

		/* IMPLEMENT THIS METHOD! */
		List<Sentence> sentenceList = new ArrayList<Sentence>();
		
		String line = null;
		
		try {
			// FileReader reads text files in the default encoding.
			if (filename == null) {
				return sentenceList;
			}
			
			// Open file to be read.
			FileReader fileReader = new FileReader(filename);
			
			// Always wrap FileReader in BufferedReader
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			
			while ((line = bufferedReader.readLine()) != null) {
				
				// Tokenize string to accurately retrieve score.
				String[] tokens = line.split(" ");
				if (tokens.length <= 1) {
					continue;
				}
				
				// Check whether score is an integer.
				if (isInteger(tokens[0])) {
					// Check whether score is between -2 to 2 inclusive.
					if (Integer.parseInt(tokens[0]) >= -2 
							&& Integer.parseInt(tokens[0]) <= 2) {		
						// Check whether text is null.
						if (line.substring(line.indexOf(" ")) != null) {
							Sentence currentSentence = new Sentence(0, null);
							// Assign score to current sentence object.
							currentSentence.score = Integer.parseInt(tokens[0]);
							// Assign text to current sentence object.
							currentSentence.text = line.substring(line.indexOf(" ")).trim();
							// Add sentence object to list.
							sentenceList.add(currentSentence);
						}
					}
				}
			}
			
			// Always close files.
			bufferedReader.close();
		}
		catch (FileNotFoundException ex) {
			System.out.println("Unable to open file '" + filename + "'");
		}
		catch (IOException ex) {
			System.out.println("Error reading file '" + filename + "'");
		}
		
		return sentenceList; // Return to main method.
		
	}
	
	/*
	 * Implement this method in Part 2
	 */
	public static Set<Word> allWords(List<Sentence> sentences) {

		/* IMPLEMENT THIS METHOD! */
		Set<Word> wordSet = new HashSet<Word>(); 
	
		// Return an empty set if input list is null or empty.
		if (sentences == null || sentences.isEmpty()) {
			return wordSet;
		}
		
		for (int i = 0; i < sentences.size(); i++) {
			// Check whether sentence object is null.
			if (sentences.get(i) == null) {
				continue;
			}
			// Split sentence objects into individual words.
			String[] tokens = sentences.get(i).text.split(" ");
			for (int j = 0; j < tokens.length; j++) {
				// Check whether tokens start with a letter.
				if (!tokens[j].toLowerCase().matches("^[a-z].*$")) {
					continue;
				}
				// Place valid tokens into Word objects.
				Word currentWord = new Word(tokens[j].toLowerCase());
				if (wordSet.contains(currentWord)) {
					currentWord.count++;
					currentWord.total += sentences.get(i).score;
				} else {
					wordSet.add(currentWord);
				}
			}
		}
		// Return to calling method.
		return wordSet; 
	}
	
	/*
	 * Implement this method in Part 3
	 */
	public static Map<String, Double> calculateScores(Set<Word> words) {

		/* IMPLEMENT THIS METHOD! */
		
		return null; // this line is here only so this code will compile if you don't modify it

	}
	
	/*
	 * Implement this method in Part 4
	 */
	public static double calculateSentenceScore(Map<String, Double> wordScores, String sentence) {

		/* IMPLEMENT THIS METHOD! */
		
		return 0; // this line is here only so this code will compile if you don't modify it

	}
	
	/*
	 * This method is here to help you run your program. Y
	 * You may modify it as needed.
	 */
	public static void main(String[] args) {
		if (args.length == 0) {
			System.out.println("Please specify the name of the input file");
			System.exit(0);
		}
		String filename = args[0];
		System.out.print("Please enter a sentence: ");
		Scanner in = new Scanner(System.in);
		String sentence = in.nextLine();
		in.close();
		List<Sentence> sentences = Analyzer.readFile(filename);
		Set<Word> words = Analyzer.allWords(sentences);
		Map<String, Double> wordScores = Analyzer.calculateScores(words);
		double score = Analyzer.calculateSentenceScore(wordScores, sentence);
		System.out.println("The sentiment score is " + score);
	}
}
