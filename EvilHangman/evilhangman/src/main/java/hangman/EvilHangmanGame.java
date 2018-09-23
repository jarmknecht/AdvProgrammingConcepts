package hangman;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * Created by Jonathan on 1/24/18.
 */

public class EvilHangmanGame implements IEvilHangmanGame {

    public EvilHangmanGame() {

    }

    public Set<String> Dictionary;
    public Map<String, Set<String>> Partition;
    public Set<Character> guesses;
    public String possibleWord;
    public boolean correctGuess;
    public int numbers;


    @Override
    public void startGame(File dictionary, int wordLength) {
        Dictionary = new HashSet<String>();
        Partition = new HashMap<String, Set<String>>();
        guesses = new TreeSet<Character>();
        StringBuilder sb = new StringBuilder();
        numbers = 0;

        for (int i = 0; i < wordLength; i++) {
            sb.append("-");
        }
        possibleWord = sb.toString();
        Scanner sc = null;
        try {
            sc = new Scanner(dictionary);
            while (sc.hasNext()) {
                String word;
                word = sc.next();
                if (word.length() == wordLength && word.matches("[a-zA-Z]+")) {
                    word = word.toLowerCase();
                    Dictionary.add(word);
                }
            }
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        finally {
            if (sc != null) {
                sc.close();
            }
        }
    }

    @Override
    public Set<String> makeGuess(char guess) throws GuessAlreadyMadeException {
        correctGuess = false;
        if (guesses.contains(guess)) {
            throw new GuessAlreadyMadeException();
        }
        guesses.add(guess);
        PartitionDictionary(guess);
        return Dictionary;
    }

    private void PartitionDictionary(char guess) {
        for(String word : Dictionary) { //for each word in dictionary
            StringBuilder str = new StringBuilder();
            for (int i = 0; i < word.length(); i++) {
                if (word.charAt(i) == guess) {
                    str.append(guess);
                }
                else {
                    str.append("-");
                }
            }
            if (Partition.containsKey(str.toString())) { // function on a map do you have this key already
                Partition.get(str.toString()).add(word);
            }
            else {
                Set<String> words = new HashSet<String>();
                words.add(word);
                Partition.put(str.toString(), words); //creates key and sets it pointing to set
            }
        }
        ReplacePartitions();
    }

    private void ReplacePartitions() {
        Set<String> value = new HashSet<String>();
        String key = "";
        for (Map.Entry<String, Set<String>> tmp : Partition.entrySet()) { //go through Partition by key and look at values there .Entry brings back a pair of them
            if (tmp.getValue().size() >= value.size()) { //have empty set and set in map which is larger
                if (tmp.getValue().size() == value.size()) {
                    if (BreakTie(key, tmp.getKey())) {
                        key = tmp.getKey();
                        value = tmp.getValue();
                    }
                }
                else { //new set size is larger than the place holder
                    key = tmp.getKey();
                    value = tmp.getValue();
                }
            }
        }
        boolean tmp = false;
        for (int i = 0; i < key.length(); i++) {
            if (key.charAt(i) != '-') {
                numbers++;
                tmp = true; //they made a correct guess cause the new key is not all '-'
            }
        }
        if (tmp) {
            correctGuess = true;
            UpdatePossibleWord(key); //only takes in new keys
        }
        Dictionary = value;
        Partition.clear(); //Parition is a map of new dictionary so always clear
    }

    private boolean BreakTie(String oldKey, String newKey) {
        if (CheckNumLetters(oldKey) < CheckNumLetters(newKey)) { //if old key has less letter which means more '-' don't update
            return false;
        }
        if (CheckNumLetters(oldKey) > CheckNumLetters(newKey)) { //old key has more letters so true
            return true;
        }
        return CheckWhichIsFurthest(oldKey, newKey); //checking with same correct letter
    }

    private boolean CheckWhichIsFurthest(String k1str, String k2str) {
        for (int i = k1str.length() - 1; i >= 0; i--) {
            if (k1str.charAt(i) != '-') { //old string has right most don't need to replace it
                if(k2str.charAt(i) == '-') {
                    return false;
                }
            }
            else if(k2str.charAt(i) != '-') { //new has right most
                return true;
            }
        }
        return false;
    }

    private int CheckNumLetters(String word) { //checks to see which one has least amount of -
        int number = 0;
        for (int i = 0; i < word.length(); i++) {
            if (word.charAt(i) != '-') {
                number++;
            }
        }
        return number;
    }

    private void UpdatePossibleWord(String word) { //updating possible word by combining old word (gameboard) with new key
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < word.length(); i++) {
            if (word.charAt(i) != '-') {
                sb.append(word.charAt(i));
            }
            else {
                sb.append(possibleWord.charAt(i));
            }
        }
        possibleWord = sb.toString();
    }

}
