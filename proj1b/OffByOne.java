public class OffByOne implements CharacterComparator {

    @Override
    public boolean equalChars(char a, char b) {
        return Math.abs(a - b) == 1;
    }
}
