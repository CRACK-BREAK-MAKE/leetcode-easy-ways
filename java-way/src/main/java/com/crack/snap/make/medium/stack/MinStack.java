package com.crack.snap.make.medium.stack;

/**
 * @author Mohan Sharma
 */

import java.util.Stack;

/**
 * Problem: Design a stack that supports push, pop, top, and retrieving the minimum element in constant time.
 * You must implement a solution with O(1) time complexity for each function.
 *
 * Intuition: First approach comes to mind is maintain a global variable for min. When we push a value, check if the value is
 * less than the global min and update the global min but the problem with this approach is when we pop the value which is
 * equivalent to the global min, there is no way to get the previous min.
 *
 * Approach 1:
 * To solve this issue we can maintain min for each element in another data structure and since we will need the element in
 * the order of insertion, we can use a stack to store the min values. Before we push a value to the stack, we will first
 * get the top of the min stack and compare it with the value to be pushed. If the value is less than the top of the min stack,
 * we will push the value and also push this new min to the min stack. When we pop the value, we pop from both stacks
 * Now top will return the top of the stack and getMin will return the top of the min stack.
 *
 * Approach 2:
 * The problem that we encountered with the global min variable was there was no way to track the previous min when we pop the
 * value which is equal to the global min. To solve this issue we can use a trick, we can store the previous min in the stack itself.
 * Let's first have a global min of MAX_VALUE and when we push a value, we check if the value is less than the global min.
 * If it is, we will push the global min to the stack and then the value as well. When we pop the value, we check
 * if the popped value is equal to the global min variable. If it is, we will pop again to get the previous min and update the
 * global min variable.
 */
public class MinStack {

    /*
    Approach 1:
    private final Stack<Integer> minStack;
    private final Stack<Integer> stack;

    public MinStack() {
        minStack = new Stack<>();
        stack = new Stack<>();
    }

    public void pushMinStack(int val) {
        var min = val;
        if (!minStack.isEmpty()) {
            min = Math.min(min, minStack.peek());
        }
        stack.push(val);
        minStack.push(min);
    }

    public void popMinStack() {
        if (stack.isEmpty()) {
            throw new IllegalStateException("Stack is empty.");
        }
        stack.pop();
        minStack.pop();
    }

    public int topMinStack() {
        if (stack.isEmpty()) {
            throw new IllegalStateException("Stack is empty.");
        }
        return stack.peek();
    }

    public int getMinMinStack() {
        if (minStack.isEmpty()) {
            throw new IllegalStateException("Stack is empty.");
        }
        return minStack.peek();
    }*/

    // Approach 2:
    private int globalMin = Integer.MAX_VALUE;
    private final Stack<Integer> stack;

    public MinStack() {
        stack = new Stack<>();
    }

    public void push(int val) {
        if (val <= globalMin) {
            stack.push(globalMin);
            globalMin = val;
        }
        stack.push(val);
    }

    public void pop() {
        if (stack.isEmpty()) {
            throw new IllegalStateException("Stack is empty.");
        }
        if (globalMin == stack.pop()) {
            globalMin = stack.pop();
        }
    }

    public int topMin() {
        if (stack.isEmpty()) {
            throw new IllegalStateException("Stack is empty.");
        }
        return stack.peek();
    }

    public int getMin() {
        if (stack.isEmpty()) {
            throw new IllegalStateException("Stack is empty.");
        }
        return globalMin;
    }

    public static void main(String[] args) {
        var obj = new MinStack();
        obj.push(3);
        obj.push(5);
        obj.push(2);
        obj.push(1);
        System.out.println(obj.getMin()); // 1
        obj.pop();
        System.out.println(obj.getMin()); // 2
        obj.pop();
        System.out.println(obj.getMin()); // 3
        obj.pop();
        System.out.println(obj.getMin());
    }
}
