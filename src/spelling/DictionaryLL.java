package spelling;

import java.util.LinkedList;

//import textgen.LLNode;
//import textgen.ListNode;

/**
 * A class that implements the Dictionary interface using a LinkedList
 *
 */
public class DictionaryLL implements Dictionary 
{

	private LinkedList<String> dict;
	private int size;
	
	public DictionaryLL() {

		dict = new LinkedList<String>();
		size = 0;
	}
    // TODO: Add a constructor


    /** Add this word to the dictionary.  Convert it to lowercase first
     * for the assignment requirements.
     * @param word The word to add
     * @return true if the word was added to the dictionary 
     * (it wasn't already there). */
    public boolean addWord(String word) {
    	// TODO: Implement this method
    	if (!dict.contains(word.toLowerCase())) {
    		dict.add(word.toLowerCase());
    		++size;
    		//System.out.println("adding "+word);
    	}
        return true;
    }


    /** Return the number of words in the dictionary */
    public int size()
    {
        // TODO: Implement this method
        return size;
    }

    /** Is this a word according to this dictionary? */
    public boolean isWord(String s) {
        //TODO: Implement this method

    	return dict.contains(s.toLowerCase());

    }
    

    
}
