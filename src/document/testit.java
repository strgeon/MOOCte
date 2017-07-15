package document;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class testit {


	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String text = "My ";
		String s2 = "String";
		text = text + s2;
		String d = "one (1), two (2), three (3)";
		ArrayList<String> tokens = new ArrayList<String>();
		Pattern tokSplitter = Pattern.compile("[a-z]+|[()0-9]+");
		Matcher m = tokSplitter.matcher(d);
		
		while (m.find()) {
			tokens.add(m.group());
		}
		System.out.println(tokens);
	
		//d.getTokens("[^, ]+");
		//List<String> tokens = getTokens("[^ ,.!?0-9()]+");
		//System.out.println(text);
	}

}
