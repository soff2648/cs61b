public class Palindrome {

    public Deque<Character> wordToDeque(String word) {
        Deque<Character> result = new LinkedListDeque<>();
        if (word == null) {
            result = null;
        } else {
            for (char ch : word.toCharArray()) {
                result.addLast(ch);
            }
        }

        return result;
    }
}
