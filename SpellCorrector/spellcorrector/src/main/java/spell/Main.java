package spell;

import java.io.IOException;

/**
 * A simple main class for running the spelling corrector. This class is not
 * used by the passoff program.
 */
public class Main {
	
	/**
	 * Give the dictionary file name as the first argument and the word to correct
	 * as the second argument.
	 */
	public static void main(String[] args) throws IOException {
		
		String dictionaryFileName = args[0];

		//String filename = args[1];

		String inputWord = args[1];
		
		/**
		 * Create an instance of your corrector here
		 */
		ISpellCorrector corrector = new SpellCorrector();

		//ISpellCorrector number2 = new SpellCorrector();
		
		corrector.useDictionary(dictionaryFileName);

		/*number2.useDictionary(filename);

		boolean same = corrector.getDictionary().equals(number2.getDictionary());

		if (!same) {
			System.out.println("Not same");
		}
		else {
			System.out.println("Same");
		}*/

		//System.out.println(corrector.PrintDictionary());

		String suggestion = corrector.suggestSimilarWord(inputWord);
		if (suggestion == null) {
		    suggestion = "No similar word found";
		}
		
		System.out.println("Suggestion is: " + suggestion);
	}

}
