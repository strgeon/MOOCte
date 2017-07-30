package textgen;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/** 
 * An implementation of the MTG interface that uses a list of lists.
 * @author UC San Diego Intermediate Programming MOOC team 
 */
public class MarkovTextGeneratorLoL implements MarkovTextGenerator {

	// The list of words with their next words
	private List<ListNode> wordList; 
	
	// The starting "word"
	private String starter;
	
	// The random number generator
	private Random rnGenerator;
	
	public MarkovTextGeneratorLoL(Random generator)
	{
		wordList = new LinkedList<ListNode>();
		starter = "";
		rnGenerator = generator;
	}
	
	/** Returns the tokens that match the regex pattern from the document 
	 * text string.
	 * @param pattern A regular expression string specifying the 
	 *   token pattern desired
	 * @return A List of tokens from the document text that match the regex 
	 *   pattern
	 */
	protected List<String> getTokens(String pattern, String text)
	{
		ArrayList<String> tokens = new ArrayList<String>();
		Pattern tokSplitter = Pattern.compile(pattern);
		Matcher m = tokSplitter.matcher(text);
		
		while (m.find()) {
			//System.out.println("m.group = "+m.group());
			tokens.add(m.group());
			
		}
		
		return tokens;
	}
	
	
	public ListNode inList(String str) {
		
		for (ListNode ln : wordList) {
			if (str.equals(ln.getWord())) {
				return ln;
			}
		}
		
		return newWord(str);
		
	}
	
	public ListNode newWord (String str) {
		ListNode newOne = new ListNode(str);
		wordList.add(newOne);
		
		return newOne;
	}
	
	/** Train the generator by adding the sourceText */
	@Override
	public void train(String sourceText)
	{
		ListNode starter = null;
		ListNode prevWord = null;
		boolean firstWord = true;
		
		if (sourceText.length() == 0 ) {
			return;
		}
		
		if (sourceText.length() > 0 ) {
			List<String> tokens = getTokens("[A-Za-z0-9'.!?]+", sourceText);
			for (String str : tokens) {
				//System.out.println("token = "+str+" "+rnGenerator);
				if (firstWord) {
					starter = newWord(str);
					prevWord = starter;
					firstWord = false;
				}
				else {
					prevWord.addNextWord(str);
					prevWord = inList(str);
					
				}
			}
			//set the last word to point to the first word
			prevWord.addNextWord(starter.getWord());
		}
		

	}
	
		/** 
	 * Generate the number of words requested.
	 */
	@Override
	public String generateText(int numWords) {
	    // TODO: Implement this method
		String output = "";
		String currWord = "" ;
		
		if (wordList.size() == 0) {
			//throw new NullPointerException();
			return output;
		}
		
		ListNode ln = wordList.get(0);
		currWord = ln.getWord();
		
		for (int i = 1; i<=numWords; i++) {
			output += (currWord + " ");
			currWord = ln.getRandomNextWord(rnGenerator);
			//System.out.println("currWord = "+currWord+", Output = "+output);
			ln = findNext(currWord);
		}
		
		return output;
	}
	
	public ListNode findNext(String word) {
		//System.out.println("trying to find word = "+word);
		for (ListNode ln : wordList) {
			//System.out.println("comparing "+word+" to getword "+ln.getWord());
			if ((ln.getWord()).equals(word)) {
				//System.out.println("got new word = "+ln.getWord());
				return ln;
			}
		}
		return null;
	}
	
	// Can be helpful for debugging
	@Override
	public String toString()
	{
		String toReturn = "";
		for (ListNode n : wordList)
		{
			toReturn += n.toString();
		}
		return toReturn;
	}
	
	/** Retrain the generator from scratch on the source text */
	@Override
	public void retrain(String sourceText)
	{
		//for (ListNode ln : wordList) {
			//ln = null;
		//}
		//wordList = null;
		wordList = new LinkedList<ListNode>();
		starter = "";
		rnGenerator = new Random(42);
		
		//if (sourceText.length() > 0) {
			this.train(sourceText);
		//}

		// TODO: Implement this method.
	}
	
	// TODO: Add any private helper methods you need here.
		
	
	/**
	 * This is a minimal set of tests.  Note that it can be difficult
	 * to test methods/classes with randomized behavior.   
	 * @param args
	 */
	public static void main(String[] args)
	{
		// feed the generator a fixed random value for repeatable behavior
		MarkovTextGeneratorLoL gen = new MarkovTextGeneratorLoL(new Random(42));
		System.out.println("starting first test");
		System.out.println(gen.generateText(20));
		System.out.println("starting second test");
		gen.train(""); 
		System.out.println(gen.generateText(20)); 
		System.out.println("starting third test");
		String textString = "Hello.  Hello there.  This is a test.  Hello there.  Hello Bob.  Test again.";
		//System.out.println(textString);
		gen.train(textString ); 
		//gen.train(textString);
		System.out.println(gen);
		System.out.println(gen.generateText(20));

		System.out.println("starting fourth test");
		gen.retrain("");
		System.out.println(gen.generateText(20));

		System.out.println("starting fifth test");
		String textString2 = "You say yes, I say no, "+
				"You say stop, and I say go, go, go, "+
				"Oh no. You say goodbye and I say hello, hello, hello, "+
				"I don't know why you say goodbye, I say hello, hello, hello, "+
				"I don't know why you say goodbye, I say hello. "+
				"I say high, you say low, "+
				"You say why, and I say I don't know. "+
				"Oh no. "+
				"You say goodbye and I say hello, hello, hello. "+
				"I don't know why you say goodbye, I say hello, hello, hello, "+
				"I don't know why you say goodbye, I say hello. "+
				"Why, why, why, why, why, why, "+
				"Do you say goodbye. "+
				"Oh no. "+
				"You say goodbye and I say hello, hello, hello. "+
				"I don't know why you say goodbye, I say hello, hello, hello, "+
				"I don't know why you say goodbye, I say hello. "+
				"You say yes, I say no, "+
				"You say stop and I say go, go, go. "+
				"Oh, oh no. "+
				"You say goodbye and I say hello, hello, hello. "+
				"I don't know why you say goodbye, I say hello, hello, hello, "+
				"I don't know why you say goodbye, I say hello, hello, hello, "+
				"I don't know why you say goodbye, I say hello, hello, hello,";
		System.out.println(textString2);
		gen.retrain(textString2);
		System.out.println(gen);
		System.out.println(gen.generateText(20));
	}

}

/** Links a word to the next words in the list 
 * You should use this class in your implementation. */
class ListNode
{
    // The word that is linking to the next words
	private String word;
	
	// The next words that could follow it
	private List<String> nextWords;
	
	ListNode(String word)
	{
		this.word = word;
		nextWords = new LinkedList<String>();
	}
	
	public String getWord()
	{
		return word;
	}

	public void addNextWord(String nextWord)
	{
		nextWords.add(nextWord);
	}
	
	public String getRandomNextWord(Random generator)
	{
		// TODO: Implement this method
	    // The random number generator should be passed from 
	    // the MarkovTextGeneratorLoL class
	
		int  n = generator.nextInt(nextWords.size());
		//System.out.println("size = "+nextWords.size()+", n = "+n);
		int i = 0;
		for (String str : nextWords) {
			if (i == n) { return str; }
			else 		{ i++; }
		}
		return null;
	}
	    	
	public String toString()
	{
		String toReturn = word + ": ";
		for (String s : nextWords) {
			toReturn += s + "->";
		}
		toReturn += "\n";
		return toReturn;
	}
	
}


