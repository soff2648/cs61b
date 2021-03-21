public class ArrayDeque<T> {
    private T[] items;
    private int nextFirst;
    private int nextLast;
    private int size;

    public ArrayDeque() {
        items = (T[]) new Object[8];
        nextFirst = 0;
        nextLast = nextFirst + 1;
        size = 0;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    private void resize(int capacity) {
        T[] resized = (T[]) new Object[capacity];
        int index = (nextFirst + 1) % size;
        for (int i = 0; i < size; i++) {
            resized[i] = items[index];
            index = (index + 1) % size;
        }
        items = resized;
        nextFirst = resized.length-1;
        nextLast = size;
    }

    public void addFirst(T a) {
        if (size == items.length) {
            resize(size * 2);
        }

        size++;
        items[nextFirst] = a;
        nextFirst = (nextFirst - 1 + items.length) % items.length;

    }

    public void addLast(T a) {
        if (size == items.length) {
            resize(size * 2);
        }

        size++;
        items[nextLast] = a;
        nextLast = (nextLast + 1) % items.length;
    }

    public void printDeque() {
        int indexOfFirst = (nextFirst + 1) % items.length;
        for (int counter = 0; counter < size; counter++) {
            System.out.print(items[indexOfFirst] + " ");
            indexOfFirst = (indexOfFirst + 1) % items.length;
        }
        System.out.println(" ");
    }

    public T removeFirst() {
        if (size == 0) {
            return null;
        }
        size--;
        int indexOfFirst = (nextFirst + 1) % items.length;
        var removed = items[indexOfFirst];
        items[indexOfFirst] = null;
        nextFirst = indexOfFirst;

        if ((double) size/items.length < 0.25 && items.length > 16) {
            resize(items.length / 2);
        }

        return removed;
    }

    public T removeLast() {
        if (size == 0) {
            return null;
        }
        size--;
        int indexOfLast = (nextLast - 1 + items.length) % items.length;
        var removed = items[indexOfLast];
        items[indexOfLast] = null;
        nextLast = indexOfLast;

        if ((double) size/items.length < 0.25 && items.length > 16) {
            resize(items.length / 2);
        }

        return removed;
    }

    public T get(int index) {
        var realIndex = (nextFirst + 1 + index) % items.length;
        return items[realIndex];
    }

}
