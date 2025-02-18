
package academy.javapro;

import java.util.*;
import java.util.regex.*;

public class Lexer {
	private static final Pattern[] PATTERNS = { Pattern.compile("\\s+"), // whitespace
			Pattern.compile("\\b(if|else|for|while|int|float|String)\\b"), // keywords
			Pattern.compile("\\b\\d+(\\.\\d+)?\\b"), // literals
			Pattern.compile("==|<=|>=|!=|&&|\\|\\||[+\\-*/=<>!]"), // operators
			Pattern.compile("[;,.(){}\\[\\]]"), // punctuation
			Pattern.compile("\\b[a-zA-Z_][a-zA-Z0-9_]*\\b") // identifiers
	};

	private static final String[] TYPES = { "WHITESPACE", "KEYWORD", "LITERAL", "OPERATOR", "PUNCTUATION",
			"IDENTIFIER" };

	private String input;
	private List<String[]> tokens;
	private int position;

	public Lexer(String input) {
		this.input = input;
		this.tokens = new ArrayList<>();
		this.position = 0;

	}

	public void tokenize() {
		tokens = new ArrayList<>(); // Ensure tokens list is initialized

		String combinedRegex = String.join("|", Arrays.stream(PATTERNS).map(Pattern::pattern).toArray(String[]::new));
		Pattern combinedPattern = Pattern.compile(combinedRegex);
		Matcher matcher = combinedPattern.matcher(input);

		while (matcher.find()) {
			String matchedText = matcher.group(); // Get the matched objects

			// Checks pattern type of token to be later identified
			for (int i = 0; i < PATTERNS.length; i++) {
				
				if (PATTERNS[i].matcher(matchedText).matches()) {
					
					if (!TYPES[i].equals("WHITESPACE")) { 
						
						tokens.add(new String[] { TYPES[i], matchedText });
					}
					break;
				}
			}
		}
	}

	
	public List<String[]> getTokens() {// Return the list of tokens
		if (tokens == null) {
			tokens = new ArrayList<>(); // Initialize tokens only when accessed
		}
		return tokens;
	}

	public static void main(String[] args) {
		String code = "int x = 10; if (x > 5) { x = x + 1; }";
		Lexer lexer = new Lexer(code);
		lexer.tokenize();
		for (String[] token : lexer.getTokens()) {
			System.out.println(token[0] + ": " + token[1]);
		}
	}
}	
