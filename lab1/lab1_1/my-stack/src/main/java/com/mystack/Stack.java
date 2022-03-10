package com.mystack;

import java.util.LinkedList;
import java.util.NoSuchElementException;

public class Stack<T> {
    private LinkedList<T> stack;
    private int bound;

    public Stack() {
        this.stack = new LinkedList<T>();
        this.bound = -1;
    }

    public Stack(int bound) {
        this.stack = new LinkedList<T>();
        this.bound = bound;
    }

    public int size() {
        return stack.size();
    }

    public boolean isEmpty() {
        return stack.size() > 0 ? false : true;
    }

    public void push(T x) {
        if(bound > 0) {
            if(stack.size() >= bound) {
                throw new IllegalStateException();
            }
        }
        stack.push(x);
    }

    public T pop() {
        return stack.pop();
    }

    public T peek() {
        if(stack.size() > 0) {
            return stack.peek();
        }
        throw new NoSuchElementException();
    }
}
