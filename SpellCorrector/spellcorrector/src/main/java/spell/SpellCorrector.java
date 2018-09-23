package spell;

import java.io.IOException;
import java.io.File;
import java.util.*;


/**
 * Created by Jonathan on 1/19/18.
 */

public class SpellCorrector implements ISpellCorrector {

    private Trie dictionary;

    public SpellCorrector() {
        this.dictionary = new Trie();
    }

    /*public Trie getDictionary() {
        return this.dictionary;
    }*/

    public void useDictionary(String dictionaryFileName) throws IOException {
        Scanner s = new Scanner(new File(dictionaryFileName));
        String word;

        while (s.hasNext()) { //will read till end of file/string
            word = s.next();
            if (word.matches("[a-zA-Z]+")) {
                dictionary.add(word);
            }
        }
        s.close();
    }

    /*public String PrintDictionary() {
        return this.dictionary.toString();
    }*/

    public String suggestSimilarWord(String inputWord) {
        if (!inputWord.matches("[a-zA-Z]+")) { //not a valid expression
            return null;
        }

        inputWord = inputWord.toLowerCase();

        if (dictionary.find(inputWord) != null) { //if input word was typed correctly
            return inputWord;
        }

        ArrayList<String> edits = new ArrayList<String>();
        edits.add(inputWord);
        EditWord(edits);

        Word winner = new Word("",0);
        String w;

        w = CheckForWord(edits, winner);

        if (w != null) {
            return w;
        }

        /*for (int i = 0; i < edits.size(); i++) {
            Trie.Node n = (Trie.Node)dictionary.find(edits.get(i));
            if (n != null) {
                if (winner.getCount() < n.getValue()) {
                    winner = new Word(edits.get(i), n.getValue());
                }
                else if (winner.getCount() == n.getValue()) {
                    if (winner.getWord().compareTo(edits.get(i)) < 0) {
                        winner = new Word(edits.get(i), n.getValue());
                    }
                }
            }
        }
        if (winner.getCount() > 0) {
            return winner.getWord();
        }*/



        EditWord(edits);

        w = CheckForWord(edits, winner);

        if (w != null) {
            return w;
        }

        /*for (int i = 0; i < edits.size(); i++) {
            Trie.Node n = (Trie.Node)dictionary.find(edits.get(i));
            if (n != null) {
                if (winner.getCount() < n.getValue()) {
                    winner = new Word(edits.get(i), n.getValue());
                }
                else if (winner.getCount() == n.getValue()) {
                    if (winner.getWord().compareTo(edits.get(i)) < 0) { //?
                        winner = new Word(edits.get(i), n.getValue());
                    }
                }
            }
        }
        if (winner.getCount() > 0) { ///no idea if i;m doing this right
            return winner.getWord();
        }*/

        return null;
    }

    private String CheckForWord(ArrayList<String> edits, Word winner) {
        for (int i = 0; i < edits.size(); i++) {
            Trie.Node n = (Trie.Node)dictionary.find(edits.get(i));
            if (n != null) {
                if (winner.getCount() < n.getValue()) {
                    winner = new Word(edits.get(i), n.getValue());
                }
                else if (winner.getCount() == n.getValue()) {
                    if (winner.getWord().compareTo(edits.get(i)) > 0) {
                        winner = new Word(edits.get(i), n.getValue());
                    }
                }
            }
        }
        if (winner.getCount() > 0) {
            return winner.getWord();
        }
        return null;
    }

    private void EditWord(ArrayList<String> edits) {
        int k = edits.size();
        for (int i = 0; i < k; i++) {
            Deletion(edits, edits.get(i));
            Transposition(edits, edits.get(i));
            Alteration(edits, edits.get(i));
            Insertion(edits, edits.get(i));
        }
    }

    private void Deletion(ArrayList<String> edits, String inputWord) {

        for (int i = 0; i < inputWord.length(); i++) {
            StringBuilder wordRmChar = new StringBuilder(inputWord);
            wordRmChar.deleteCharAt(i);
            edits.add(wordRmChar.toString());//need to make the StringBuilder back to a String
        }
    }

    private void Transposition(ArrayList<String> edits, String inputWord) {

        for (int i = 0; i < inputWord.length() - 1; i++) {
            StringBuilder wordTrans = new StringBuilder(inputWord);
            char c = wordTrans.charAt(i);
            wordTrans.deleteCharAt(i);
            wordTrans.insert(i + 1, c);
            edits.add(wordTrans.toString());
        }
    }

    private void Alteration(ArrayList<String> edits, String inputWord) {
        for (int i = 0; i < inputWord.length(); i++) {
            StringBuilder altWord = new StringBuilder(inputWord);
            for (int j = 0; j < 26; j++) { //inner loop adds alphabet to each position from i
                altWord.deleteCharAt(i); //delete char at i so it can be replaced later
                altWord.insert(i, (char)(j + 'a'));
                edits.add(altWord.toString());
            }
        }
    }

    private void Insertion(ArrayList<String> edits, String inputWord) {
        for (int i = 0; i <= inputWord.length(); i++) {
            StringBuilder insertWord = new StringBuilder(inputWord);
            for (int j = 0; j < 26; j++) {
                insertWord.insert(i, (char)(j + 'a'));
                edits.add(insertWord.toString());
                insertWord.deleteCharAt(i); //delete added char after so new char can go there
            }
        }
    }

    private class Word {
        private String word;
        private int count;

        public Word(String w, int c) {
            this.word = w;
            this.count = c;
        }

        public int getCount() {
            return this.count;
        }

        public String getWord() {
            return this.word;
        }

    }
}
