/**
 * 
 */
package spelling;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;


/**
 * @author UC San Diego Intermediate MOOC team
 *
 */
public class NearbyWords implements SpellingSuggest {
	// THRESHOLD to determine how many words to look through when looking
	// for spelling suggestions (stops prohibitively long searching)
	// For use in the Optional Optimization in Part 2.
	private static final int THRESHOLD = 100; 

	Dictionary dict;

	public NearbyWords (Dictionary dict) 
	{
		this.dict = dict;
	}

	/** Return the list of Strings that are one modification away
	 * from the input string.  
	 * @param s The original String
	 * @param wordsOnly controls whether to return only words or any String
	 * @return list of Strings which are nearby the original string
	 */
	public List<String> distanceOne(String s, boolean wordsOnly )  {
		   List<String> retList = new ArrayList<String>();
		   insertions(s, retList, wordsOnly);
		   substitution(s, retList, wordsOnly);
		   deletions(s, retList, wordsOnly);
		   return retList;
	}

	
	/** Add to the currentList Strings that are one character mutation away
	 * from the input string.  
	 * @param s The original String
	 * @param currentList is the list of words to append modified words 
	 * @param wordsOnly controls whether to return only words or any String
	 * @return
	 */
	public void substitution(String s, List<String> currentList, boolean wordsOnly) {
		// for each letter in the s and for all possible replacement characters
		for(int index = 0; index < s.length(); index++){
			for(int charCode = (int)'a'; charCode <= (int)'z'; charCode++) {
				// use StringBuffer for an easy interface to permuting the 
				// letters in the String
				StringBuffer sb = new StringBuffer(s);
				sb.setCharAt(index, (char)charCode);

				// if the item isn't in the list, isn't the original string, and
				// (if wordsOnly is true) is a real word, add to the list
				if(!currentList.contains(sb.toString()) && 
						(!wordsOnly||dict.isWord(sb.toString())) &&
						!s.equals(sb.toString())) {
					currentList.add(sb.toString());
				}
				//System.out.println("substitution trying ["+sb+"] indict? "+dict.isWord(sb.toString()));
			}
		}
	}
	
	/** Add to the currentList Strings that are one character insertion away
	 * from the input string.  
	 * @param s The original String
	 * @param currentList is the list of words to append modified words 
	 * @param wordsOnly controls whether to return only words or any String
	 * @return
	 */
	public void insertions(String s, List<String> currentList, boolean wordsOnly ) {
		// done
		String t = s + " ";
		//System.out.println("string sizes = "+s.length()+" "+t.length());
		for(int index = 0; index < t.length(); index++){
			for(int charCode = (int)'a'; charCode <= (int)'z'; charCode++) {
				// use StringBuffer for an easy interface to permuting the 
				// letters in the String
				StringBuffer sb = new StringBuffer(t);
				//System.out.println("sb = ["+sb+"] index = "+index+" lenght of t is "+t.length());
				
				//for (int i = t.length(); i == index; i--) {
				for (int i = s.length(); i > index; i--) {
					sb.setCharAt(i, sb.charAt(i-1));
					//System.out.println("sb = ["+sb+"] i = "+i);
				}
				sb.setCharAt(index, (char)charCode);
				//System.out.println("insert trying ["+sb+"] indict? "+dict.isWord(sb.toString()));

				// if the item isn't in the list, isn't the original string, and
				// (if wordsOnly is true) is a real word, add to the list
				if(!currentList.contains(sb.toString()) && 
						(!wordsOnly||dict.isWord(sb.toString())) &&
						!s.equals(sb.toString())) {
					currentList.add(sb.toString());
				}
			}
		}
	}

	/** Add to the currentList Strings that are one character deletion away
	 * from the input string.  
	 * @param s The original String
	 * @param currentList is the list of words to append modified words 
	 * @param wordsOnly controls whether to return only words or any String
	 * @return
	 */
	public void deletions(String s, List<String> currentList, boolean wordsOnly ) {
		// done: Implement this method
		//System.out.println("string sizes = "+s.length());
		for(int index = 0; index < s.length(); index++){
				StringBuffer sb = new StringBuffer(s);
				//System.out.println("sb = ["+sb+"] index = "+index+" lenght of s is "+s.length());
				sb.setCharAt(index, ' ');
				for (int i = index; i < s.length()-1; i++) {
					sb.setCharAt(i, sb.charAt(i+1));
					sb.setCharAt(i+1, ' ');
				}
				sb.setLength(s.length()-1);
				
				//System.out.println("delete trying ["+sb+"] indict? "+dict.isWord(sb.toString()));

				// if the item isn't in the list, isn't the original string, and
				// (if wordsOnly is true) is a real word, add to the list
				if(!currentList.contains(sb.toString()) && 
						(!wordsOnly||dict.isWord(sb.toString())) &&
						!s.equals(sb.toString())) {
					currentList.add(sb.toString());
				}
			//}
		}

	}

	/** Add to the currentList Strings that are one character deletion away
	 * from the input string.  
	 * @param word The misspelled word
	 * @param numSuggestions is the maximum number of suggestions to return 
	 * @return the list of spelling suggestions
	 */
	@Override
	public List<String> suggestions(String word, int numSuggestions) {

		// initial variables
		LinkedList<String> queue = new LinkedList<String>();     // String to explore
		HashSet<String> visited = new HashSet<String>();   // to avoid exploring the same  
														   // string multiple times
		List<String> retList = new LinkedList<String>();   // words to return
		String curr;
		int i = 0;
		int ret = 0;
		
		// insert first node
		queue.add(word);
		visited.add(word);

		if (numSuggestions == 0) {
	   		return retList;
		}

		//System.out.println("queue size = ["+queue.size()+"]");

	   	while (queue.size() > 0) {
	   		curr = queue.poll();
			//System.out.println("found curr = ["+curr+"]");
			for ( String l : distanceOne(curr, true)) {
				if (++ret > THRESHOLD) {
					return retList;
				}
				
				if (!visited.contains(l) && i < numSuggestions) {
					visited.add(l);
					queue.add(l);
					if (dict.isWord(l)) {
						retList.add(l);
						i++;
						//System.out.println("i =["+i+"]");
					}
	   			}			
	   		}
	   	}
	   		
	   		
		// DONE: Implement the remainder of this method, see assignment for algorithm
		
		return retList;

	}	
	
   public static void main(String[] args) {
	   /* basic testing code to get started */
	   String word = "i";
	   // Pass NearbyWords any Dictionary implementation you prefer
	   Dictionary d = new DictionaryHashSet();
	   DictionaryLoader.loadDictionary(d, "data/dict.txt");
	   NearbyWords w = new NearbyWords(d);
	   List<String> l = w.distanceOne(word, true);
	   System.out.println("One away word Strings for for \""+word+"\" are:");
	   System.out.println(l+"\n");

	   
	   word = "tailo";
	   List<String> suggest = w.suggestions(word, 10);
	   System.out.println("Spelling Suggestions for \""+word+"\" are:");
	   System.out.println(suggest);
	   
	   word = "kangaro";
	   List<String> sug2= w.suggestions(word, 10);
	   System.out.println("Spelling Suggestions for \""+word+"\" are:");
	   System.out.println(sug2);
	   
	   word = "kangaro";
	   List<String> x = w.distanceOne(word, true);
	   System.out.println("One away word Strings for for \""+word+"\" are:");
	   System.out.println(x+"\n");
	   
   }

}
