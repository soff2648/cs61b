import java.util.Arrays;

/**
 * Class for doing Radix sort
 *
 * @author Akhil Batra, Alexander Hwang
 *
 */
public class RadixSort {
    /**
     * Does LSD radix sort on the passed in array with the following restrictions:
     * The array can only have ASCII Strings (sequence of 1 byte characters)
     * The sorting is stable and non-destructive
     * The Strings can be variable length (all Strings are not constrained to 1 length)
     *
     * @param asciis String[] that needs to be sorted
     *
     * @return String[] the sorted array
     */
    private static final int ASCIICODE = 256;

    public static String[] sort(String[] asciis) {
        int maxLength = 0;
        String[] items = new String[asciis.length];
        for (int i = 0; i < asciis.length; i++) {
            maxLength = Math.max(asciis[i].length(), maxLength);
            items[i] = asciis[i];
        }

//        for (int i = 0; i < asciis.length; i++) {
//            items[i] = fillStringToMaxLength(maxLength, asciis[i]);
//        }

        for (int i = maxLength - 1; i >= 0; i--) {
            items = sortHelperLSD(items, i);
        }

        //cleanStrings(items);
        return items;
    }

    private static void cleanStrings(String[] items) {
        for (int i = 0; i < items.length; i++) {
            items[i] = items[i].replaceAll("\\s+", "");
        }
    }

    /**
     * LSD helper method that performs a destructive counting sort the array of
     * Strings based off characters at a specific index.
     * @param asciis Input array of Strings
     * @param index The position to sort the Strings on.
     */
    private static String[] sortHelperLSD(String[] asciis, int index) {
        // Optional LSD helper method for required LSD radix sort
        int nullCode = 0;
        int[] counter = new int[ASCIICODE];
        for (int i = 0; i < asciis.length; i++) {
            if (index >= asciis[i].length()) {
                counter[nullCode] += 1;
            } else {
                int characterNum = (int) asciis[i].toCharArray()[index];
                counter[characterNum] += 1;
            }
        }

        int[] started = new int[ASCIICODE];
        int pos = 0;
        for (int i = 0; i < started.length; i++) {
            started[i] = pos;
            pos += counter[i];
        }

        String[] sorted = new String[asciis.length];
        for (int i = 0; i < asciis.length; i++) {
            if (index >= asciis[i].length()) {
                int place = started[nullCode];
                sorted[place] = asciis[i];
                started[nullCode] += 1;
            } else {
                int characterNum = (int) asciis[i].toCharArray()[index];
                int place = started[characterNum];
                sorted[place] = asciis[i];
                started[characterNum] += 1;
            }

        }


        return sorted;
    }


    /**
     * MSD radix sort helper function that recursively calls itself to achieve the sorted array.
     * Destructive method that changes the passed in array, asciis.
     *
     * @param asciis String[] to be sorted
     * @param start int for where to start sorting in this method (includes String at start)
     * @param end int for where to end sorting in this method (does not include String at end)
     * @param index the index of the character the method is currently sorting on
     *
     **/
    private static void sortHelperMSD(String[] asciis, int start, int end, int index) {
        // Optional MSD helper method for optional MSD radix sort
        return;
    }


    public static void main(String[] args) {
        String[] strings = new String[10];
        strings[0] = "Vanessa";
        strings[1] = "Ethan";
        strings[2] = "Adam";
        strings[3] = "Martin";
        strings[4] = "Vinson";
        strings[5] = "Alexander";
        strings[6] = "Smith";
        strings[7] = "Edwardo";
        strings[8] = "Schumacher";
        strings[9] = "Bob";
        String[] sorted = RadixSort.sort(strings);
        System.out.println(Arrays.toString(sorted));
    }
}
