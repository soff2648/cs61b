
package synthesizer;

import java.util.Iterator;

public class ArrayRingBuffer<T> extends AbstractBoundedQueue<T> {
    /* Index for the next dequeue or peek. */
    private int first;            // index for the next dequeue or peek
    /* Index for the next enqueue. */
    private int last;
    /* Array for storing the buffer data. */
    private T[] rb;

    /**
     * Create a new ArrayRingBuffer with the given capacity.
     */
    public ArrayRingBuffer(int capacity) {
        first = 0;
        last = 0;
        this.fillCount = 0;
        this.capacity = capacity;
        rb = (T[]) new Object[capacity];
    }

    /**
     * Adds x to the end of the ring buffer. If there is no room, then
     * throw new RuntimeException("Ring buffer overflow"). Exceptions
     * covered Monday.
     */
    public void enqueue(T x) {
        if (isFull()) {
            throw new RuntimeException("Ring buffer overflow");
        }

        this.rb[last] = x;
        last += 1;
        this.fillCount += 1;
        checkCircleLast();
    }


    private void checkCircleLast() {
        if (last == this.capacity) {
            last = 0;
        }
    }

    /**
     * Dequeue oldest item in the ring buffer. If the buffer is empty, then
     * throw new RuntimeException("Ring buffer underflow"). Exceptions
     * covered Monday.
     */
    public T dequeue() {
        if (isEmpty()) {
            throw new RuntimeException("Ring buffer overflow");
        }
        T returnFirst = rb[first];
        fillCount -= 1;
        first += 1;
        checkCircleFirst();

        return returnFirst;
    }

    private void checkCircleFirst() {
        if (first == this.capacity) {
            first = 0;
        }
    }

    /**
     * Return oldest item, but don't remove it.
     */
    public T peek() {
        if (isEmpty()) {
            throw new RuntimeException("Ring buffer overflow");
        }
        return rb[first];
    }

    @Override
    public Iterator<T> iterator() {
        return new ArrayRingBufferIterator();
    }

    private class ArrayRingBufferIterator implements Iterator<T> {
        int count;
        int index;

        public ArrayRingBufferIterator() {
            index = first;
            count = 0;
        }

        @Override
        public boolean hasNext() {
            return count < fillCount;
        }

        @Override
        public T next() {
            T returnItem = rb[index];
            index += 1;
            count += 1;
            if (index == capacity) {
                index = 0;
            }
            return returnItem;
        }
    }
}
