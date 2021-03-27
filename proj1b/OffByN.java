public class OffByN implements CharacterComparator {
    private int N;

    public OffByN(int N) {
        this.N = N;
    }

    @Override
    public boolean equalChars(char a, char b) {
        return Math.abs(a - b) == this.N;
    }

}
