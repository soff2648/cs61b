public class LinkedListDeque<T> implements Deque<T> {

    private class Node<T> {
        private T item;
        private Node next;
        private Node prev;

        private Node(T i, Node n, Node p) {
            this.item = i;
            this.next = n;
            this.prev = p;
        }
    }

    private Node sentinel;
    private int size;


    /**
     * generate DLL using circular sentinel topology
     */
    public LinkedListDeque() {
        sentinel = new Node(null, null, null);
        size = 0;
    }

//    public LinkedListDeque(LinkedListDeque other) {
//        size = other.size();
//        sentinel = new Node(null, null, null);
//        for (int i = 0; i < other.size(); i++) {
//            addLast((T) other.get(i));
//        }
//    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public void addFirst(T item) {
        size++;
        if (sentinel.next == null) {
            sentinel.next = new Node(item, sentinel, sentinel);
            sentinel.prev = sentinel.next;
        } else {
            var nextNode = sentinel.next;
            sentinel.next = new Node(item, nextNode, sentinel);
            nextNode.prev = sentinel.next;
        }
    }

    @Override
    public void addLast(T item) {
        size++;
        if (sentinel.prev == null) {
            sentinel.next = new Node(item, sentinel, sentinel);
            sentinel.prev = sentinel.next;
        } else {
            var lastNode = sentinel.prev;
            sentinel.prev = new Node(item, sentinel, lastNode);
            lastNode.next = sentinel.prev;
        }
    }

    @Override
    public void printDeque() {
        var currentNode = sentinel.next;
        while (currentNode != sentinel) {
            System.out.print(currentNode.item + " ");
            currentNode = currentNode.next;
        }
        System.out.println(" ");
    }

    @Override
    public T removeFirst() {
        if (size == 0) {
            return null;
        }
        size--;
        var nextNode = sentinel.next.next;
        var remove = sentinel.next;
        sentinel.next = nextNode;
        nextNode.prev = sentinel;

        return (T) remove.item;
    }

    @Override
    public T removeLast() {
        if (size == 0) {
            return null;
        }
        size--;
        var prevNode = sentinel.prev.prev;
        var remove = sentinel.prev;

        sentinel.prev = prevNode;
        prevNode.next = sentinel;

        return (T) remove.item;
    }

    @Override
    public T get(int index) {
        if (size <= index) {
            return null;
        }

        var current = sentinel.next;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        return (T) current.item;
    }

    public T getRecursive(int index) {
        if (size <= index) {
            return null;
        }

        return (T) getRecursive(sentinel.next, index);
    }

    private T getRecursive(Node next, int index) {
        if (index == 0) {
            return (T) next.item;
        }

        return (T) getRecursive(next.next, index - 1);
    }

}
