package com.crack.snap.make.easy.stack;

/**
 * @author Mohan Sharma
 */
public class ConstantStack<T> {

    private int top = -1;
    private final int capacity;
    private final Object[] elementData;
    private final static int DEFAULT_CAPACITY = 10;

    public ConstantStack() {
        this(DEFAULT_CAPACITY);
    }

    public ConstantStack(int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("Illegal Capacity: " + capacity);
        }
        this.elementData = new Object[capacity];
        this.capacity = capacity;
    }

    public boolean push(T element) {
        if (top == capacity - 1) {
            throw new IllegalStateException("Stack is full.");
        }
        elementData[++top] = element;
        return true;
    }

    @SuppressWarnings("unchecked")
    public T pop() {
        if (isEmpty()) {
            throw new IllegalStateException("Stack is empty.");
        }
        T data = (T) elementData[top];
        elementData[top--] = null; // Clear the reference for garbage collection
        return data;
    }

    @SuppressWarnings("unchecked")
    public T peek() {
        if (isEmpty()) {
            throw new IllegalStateException("Stack is empty.");
        }
        return (T) elementData[top];
    }

    public boolean isEmpty() {
        return top < 0;
    }
}
