public interface Deque<Item> {
    int size();

    void addFirst(Item item);

    void addLast(Item item);

    boolean isEmpty();

    void printDeque();

    Item removeFirst();

    Item removeLast();

    Item get(int index);

}
