import static org.junit.Assert.*;
import org.junit.Test;

public class TestArrayDequeGold {
    private static String[] methods = {"addFirst", "addLast", "removeFirst", "removeLast"};
    StudentArrayDeque<Integer> studentArrayDeque = new StudentArrayDeque<>();
    ArrayDequeSolution<Integer> arrayDequeSolution = new ArrayDequeSolution<>();
    String sequences = "" + System.lineSeparator();

    @Test
    public void testRandom() {
        while (true) {
            int randomMethod = StdRandom.uniform(0, 4);
            switch (randomMethod) {
                case 0:
                    testAddFirst();
                    break;
                case 1:
                    testAddLast();
                    break;
                case 2:
                    testRemoveFirst();
                    break;
                case 3:
                    testRemoveLast();
                    break;
                default:
                    break;
            }
        }

    }

    @Test
    public void testAddFirst() {
        Integer randomInt = StdRandom.uniform(100);
        sequences += "addFirst(" + randomInt + ")" + System.lineSeparator();
        studentArrayDeque.addFirst(randomInt);
        Integer result = studentArrayDeque.get(0);
        arrayDequeSolution.addFirst(randomInt);
        Integer expected = arrayDequeSolution.get(0);
        assertEquals(sequences, result, expected);
    }

    @Test
    public void testAddLast() {
        Integer randomInt = StdRandom.uniform(100);
        sequences += "addLast(" + randomInt + ")" + System.lineSeparator();
        studentArrayDeque.addLast(randomInt);
        var last = studentArrayDeque.size() - 1;
        Integer result = studentArrayDeque.get(last);
        arrayDequeSolution.addLast(randomInt);
        var last2 = arrayDequeSolution.size() - 1;
        Integer expected = arrayDequeSolution.get(last2);
        assertEquals(sequences, result, expected);
    }

    @Test
    public void testRemoveFirst() {
        sequences += "removeFirst()" + System.lineSeparator();
        if (studentArrayDeque.isEmpty() || arrayDequeSolution.isEmpty()) {
            return;
        }

        Integer result = studentArrayDeque.removeFirst();
        Integer expected = arrayDequeSolution.removeFirst();
        assertEquals(sequences, result, expected);

    }

    @Test
    public void testRemoveLast() {
        sequences += "removeLast()" + System.lineSeparator();
        if (studentArrayDeque.isEmpty() || arrayDequeSolution.isEmpty()) {
            return;
        }

        Integer result = studentArrayDeque.removeLast();
        Integer expected = arrayDequeSolution.removeLast();
        assertEquals(sequences, result, expected);
    }
}
