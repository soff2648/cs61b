import org.junit.Test;
import static org.junit.Assert.*;

public class TestPalindrome {
    /*// You must use this palindrome, and not instantiate
    // new Palindromes, or the autograder might be upset. */
    static Palindrome palindrome = new Palindrome();
    static OffByOne cc = new OffByOne();
    static OffByN cc2 = new OffByN(5);

    @Test
    public void testWordToDeque() {
        Deque d = palindrome.wordToDeque("persiflage");
        String actual = "";
        for (int i = 0; i < "persiflage".length(); i++) {
            actual += d.removeFirst();
        }
        assertEquals("persiflage", actual);
    }
    /*Uncomment this class once you've created your Palindrome class. */

    @Test
    public void testIsPalindrome() {

        String input1 = "cat";
        boolean output1 = palindrome.isPalindrome(input1);
        assertFalse(output1);

        String input2 = "flake";
        boolean output2 = palindrome.isPalindrome(input2, cc);
        assertTrue(output2);

        String input4 = "rotor";
        boolean output4 = palindrome.isPalindrome(input4);
        assertTrue(output4);

        String input3 = "a";
        boolean output3 = palindrome.isPalindrome(input3, cc);
        assertTrue(output3);

        String input5 = "&%";
        boolean output5 = palindrome.isPalindrome(input5, cc);
        assertFalse(output5);


    }
}
