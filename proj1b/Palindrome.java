import java.util.Locale;

public class Palindrome {

    public Deque<Character> wordToDeque(String word) {
        Deque<Character> result = new ArrayDeque<>();
        if (word == null) {
            result = null;
        } else {
            for (char ch : word.toLowerCase().toCharArray()) {
                result.addLast(ch);
            }
        }

        return result;
    }

    public boolean isPalindrome(String word) {

        Deque<Character> dequeWord = wordToDeque(word);

        return isPalindrome(dequeWord);
    }

    private boolean isPalindrome(Deque<Character> dequeWord) {
        if (dequeWord.isEmpty() || dequeWord.size() == 1) {
            return true;
        }
        char front = dequeWord.removeFirst();
        char end = dequeWord.removeLast();
        if (front < 'a' || end < 'a' || front > 'z' || end > 'z') {
            return false;
        }

        return front == end && isPalindrome(dequeWord);
    }

    public boolean isPalindrome(String word, CharacterComparator cc) {
        Deque<Character> dequeWord = wordToDeque(word);

        return isPalindrome(dequeWord, cc);
    }

    private boolean isPalindrome(Deque<Character> dequeWord, CharacterComparator cc) {
        if (dequeWord.isEmpty() || dequeWord.size() == 1) {
            return true;
        }
        char front = dequeWord.removeFirst();
        char end = dequeWord.removeLast();
        if (front < 'a' || end < 'a' || front > 'z' || end > 'z') {
            return false;
        }

        return cc.equalChars(front, end) && isPalindrome(dequeWord, cc);
    }
}
