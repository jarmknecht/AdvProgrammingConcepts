package hangman;

import java.io.*;
import java.nio.file.Files;
import java.util.*;

/**
 * Created by Jonathan on 1/24/18.
 */

public class Main {

    public static void main (String[] args) {
        EvilHangmanGame game = new EvilHangmanGame();

        if (args.length != 3) {
            System.out.println("Not Enough Arguments");
            System.out.println("Usage: java [main class name] [dictionary path] [length of word] [number of guesses]");
            return; //quit can't run game with bad arguments
        }
        File file = new File(args[0]);

        int wordLength = Integer.parseInt(args[1]);

        int numOfGuesses = Integer.parseInt(args[2]);

        int remainingWordLength = wordLength; //show how much unknown chars remain how manny letter in word guessed right

        if (wordLength < 2 || numOfGuesses < 1) {
            System.out.println("Wrong number of guesses or incorrect word length");
            return;
        }

        game.startGame(file, wordLength);

        /*StringBuilder sb = new StringBuilder();
        for (int i = 0; i < wordLength; i++) {
            sb.append("-");
        }*/
        //String currWord = sb.toString();

        //boolean win = false;

        //TreeSet<Character> charGuesses = new TreeSet<Character>(); //or array??
        Scanner in = null;

        while (numOfGuesses > 0) {
            System.out.println("You have " + numOfGuesses + " guesses left");
            StringBuilder sb = new StringBuilder();
            for (Character letter : game.guesses) {
                sb.append(letter + " ");
            }
            System.out.println("Used letters: " + sb.toString());
            System.out.println("Word: " + game.possibleWord);
            System.out.print("Enter guess: ");

            try {
                in = new Scanner(System.in);
                String guess = in.nextLine();

                while (!guess.matches("[a-zA-Z]")) {
                    System.out.println("\nInvalid Input!\n");
                    System.out.print("Enter guess: ");
                    guess = in.nextLine();
                }
                guess = guess.toLowerCase();
                char c = guess.charAt(0);
                //charGuesses.add(c);
                if (game.guesses.contains(c)) {
                    System.out.println(c + " was already guessed\n");
                }
                else {
                    try {
                        game.makeGuess(c);
                        if (!game.correctGuess) {
                            numOfGuesses--;
                            if (numOfGuesses > 0) {
                                System.out.println("Sorry, there are no " + c + "\'s\n");
                            }
                        }
                        else {
                            remainingWordLength -= game.numbers; //checks how many chars aren't unknown and subtracts from remaining
                            if (numOfGuesses > 0 && remainingWordLength != 0) {
                                System.out.println("Yes, there is " + game.numbers + " " + c + "\n");
                            }
                        }
                        game.numbers = 0;
                    }
                    catch (IEvilHangmanGame.GuessAlreadyMadeException e) {
                        //System.out.println("Guess already made"); //need?
                        e.printStackTrace();
                    }
                }

            }

            catch (Exception e) {
                //System.out.println("Can't input");
                e.printStackTrace();
            }

            if (remainingWordLength == 0) {
                System.out.println("You win!");
                System.out.println("The word was: " + game.possibleWord);
                return;
            }
        }

        if (in != null) {
            in.close();
        }

        System.out.println("You lose!");
        Iterator<String> iter = game.Dictionary.iterator();
        String word = iter.next();
        System.out.println("The word was: " + word);
    }
}
