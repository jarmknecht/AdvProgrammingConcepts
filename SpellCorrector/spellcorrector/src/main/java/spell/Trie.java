package spell;

/**
 * Created by Jonathan on 1/19/18.
 */

public class Trie implements ITrie {

    private int wordCount;
    private int nodeCount;
    private Node root;

    public Trie() {
        this.wordCount = 0;
        this.nodeCount = 1;
        this.root = new Node();
    }


    public void add(String word) {
        Node current = this.root;

        word = word.toLowerCase();

        for (int i = 0; i < word.length(); i++) {
            char c = word.charAt(i);
            int index = c - 'a';

            if (current.children[index] == null) {
                current.children[index] = new Node();
                setNodeCount();
                current = current.children[index];
            }

            else {
                current = current.children[index];
            }

        }
        if (current.getValue() == 0) {
            setWordCount();
        }
        current.setFrequency();
    }

    public INode find(String word) {
        Node current = this.root;

        word = word.toLowerCase();

        for (int i = 0; i < word.length(); i++) {
            char c = word.charAt(i);
            int index = c - 'a';

            if (current.children[index] == null) {
                return null;
            }
            else {
                current = current.children[index];
            }

        }

        if (current.getValue() != 0) {
            return current;
        }

        return null;
    }

    public void setWordCount() {
        this.wordCount++;
    }

    public int getWordCount() {
        return this.wordCount;
    }

    public void setNodeCount() {
        this.nodeCount++;
    }

    public int getNodeCount() {
        return this.nodeCount;
    }

    @Override
    public String toString() {
        StringBuilder output = new StringBuilder();
        StringBuilder word = new StringBuilder();
        toString(this.root, word, output);

        return output.toString();
    }

    private void toString(Node n, StringBuilder word, StringBuilder output) {
        if (n.getValue() > 0) { //that means we found a word and need to append it
            output.append(word);
            output.append("\n");
        }

        for (int i = 0; i < 26; i++) {
            if (n.children[i] == null) {
                continue;
            }
            if (n.children[i] != null) {
                word.append((char) (i + 'a')); //casting ints to a char
                toString(n.children[i], word, output);
            }

        }
        if (word.length() > 0) {
            word.deleteCharAt(word.length() - 1);
        }
    }

    @Override
    public int hashCode() {
        final int primeNum = 23;
        return ((this.getNodeCount() + this.getWordCount()) * primeNum);
    }

    @Override
    public boolean equals(Object o) {
        //System.out.println("called");
        if (this == o) {
            return true;
        }
        if (o == null) {
            //System.out.println("null break");
            return false;
        }
        if (this.getClass() != o.getClass()) { //not same object type
            //System.out.println("type break");
            return false;
        }
        Trie other = (Trie) o;

        if (this.getWordCount() != other.getWordCount()) { //not same # of word so different tries
            //System.out.println("word count break");
            return false;
        }

        if (this.getNodeCount() != other.getNodeCount()) {
            //System.out.println("node count break");
            return false;
        }

        return RecurseTrie(this.root, other.root);
    }

    private boolean RecurseTrie(Node realRoot, Node otherRoot) { //recursive helper function
        if (realRoot.getValue() != otherRoot.getValue()) {
            //System.out.println("real root freq != other root freq break");
            return false;
        }

        for (int i = 0; i < 26; i++) {
            if (realRoot.children[i] == null && otherRoot.children[i] == null) {
                continue;
            }
            if (realRoot.children[i] == null && otherRoot.children[i] != null) {
                //System.out.println("real null other !null break");
                return false;
            }

            if (realRoot.children[i] != null && otherRoot.children[i] == null) {
                return false;
            }

            if (!RecurseTrie(realRoot.children[i], otherRoot.children[i])) {
                //System.out.println("recursive break");
                return false;
            }
        }

        return true;
    }

    public class Node implements ITrie.INode {
        private int frequency;
        private Node[] children;

        public Node() {
            this.frequency = 0;
            this.children = new Node[26]; //add another node with 26 char and 0 freq
        }

        public int getValue() {
            return this.frequency;
        }

        public void setFrequency() {
            this.frequency++;
        }
    }
}