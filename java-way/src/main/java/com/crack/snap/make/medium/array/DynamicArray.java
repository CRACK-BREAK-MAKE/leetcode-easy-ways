package com.crack.snap.make.medium.array;

import java.util.Arrays;

/**
 * @author Mohan Sharma
 */
public class DynamicArray<T> {

    private static int DEFAULT_CAPACITY = 10;
    private int size;
    private Object[] elementData;
    private static Object[] DEFAULT_EMPTY_ELEMENT_DATA = {};

    public DynamicArray() {
        this.elementData = DEFAULT_EMPTY_ELEMENT_DATA;
    }

    public DynamicArray(int capacity) {
        if (capacity > 0) {
            this.elementData = new Object[capacity];
        } else if (capacity == 0) {
            this.elementData = DEFAULT_EMPTY_ELEMENT_DATA;
        } else {
            throw new IllegalArgumentException("Illegal Capacity: " + capacity);
        }
    }

    public boolean add(T element) {
        addInternal(element, elementData, size);
        return true;
    }

    public void addInternal(T element, Object[] elementData, int s) {
        if (s == elementData.length) {
            elementData = grow();
        }
        elementData[s] = element;
        size++;
    }

    public Object[] grow() {
        var minCapacity = size + 1;
        var oldCapacity = elementData.length;
        if (oldCapacity > 0 || elementData != DEFAULT_EMPTY_ELEMENT_DATA) {
            var newCapacity = oldCapacity + (oldCapacity >>  1);
            if (newCapacity < minCapacity) {
                newCapacity = minCapacity;
            } else if (newCapacity >= Integer.MAX_VALUE) {
                newCapacity = Integer.MAX_VALUE;
            }
            return elementData = Arrays.copyOf(elementData, newCapacity);
        }
        else {
            return elementData = new Object[Math.max(DEFAULT_CAPACITY, minCapacity)];
        }
    }

    public T remove(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        T oldValue = (T) elementData[index];
        int numMoved = size - index - 1;
        if (numMoved > 0) {
            System.arraycopy(elementData, index + 1, elementData, index, numMoved);
        }
        elementData[--size] = null; // clear to let GC do its work
        return oldValue;
    }
}
