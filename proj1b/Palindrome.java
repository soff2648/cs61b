public class Palindrome {

    public Deque<Character> wordToDeque(String word) {
        Deque<Character> result = new ArrayDeque<>();
        if (word == null) {
            result = null;
        } else {
            for (char ch : word.toCharArray()) {
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

        return dequeWord.removeFirst() == dequeWord.removeLast() && isPalindrome(dequeWord);
    }
}
