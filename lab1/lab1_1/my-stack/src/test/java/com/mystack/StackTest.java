package com.mystack;

import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;

public class StackTest {

    TqsStack<Integer> stack;

    @BeforeEach
    public void initEach(){
        stack = new TqsStack<>();
    }

    @Test
    @DisplayName("A stack is empty on construction")
    void isEmpty() {
        Assertions.assertTrue(stack.isEmpty());
    }
    
    @Test
    @DisplayName("A stack has size 0 on construction")
    void size() {
        Assertions.assertEquals(stack.size(), 0);
    }

    @Test
    @DisplayName("After n pushes to an empty stack, n > 0, the stack is not empty and its size is n")
    void nPushes() {
        stack.push(1);
        Assertions.assertEquals(stack.size(), 1);
        Assertions.assertFalse(stack.isEmpty());
    }

    @Test
    @DisplayName("If one pushes x then pops, the value popped is x")
    void pushAndPop() {
        stack.push(1);
        Assertions.assertEquals(stack.pop(), 1);
    }

    @Test
    @DisplayName("If one pushes x then peeks, the value returned is x, but the size stays the same")
    void pushAndPeek() {
        stack.push(1);
        int size = stack.size();
        Assertions.assertEquals(stack.peek(), 1);
        Assertions.assertEquals(stack.size(), size);
    }

    @Test
    @DisplayName("If the size is n, then after n pops, the stack is empty and has a size 0")
    void nPops() {
        int size = stack.size();
        for(int i = 0; i < size; i++) {
            stack.pop();
        }
        Assertions.assertTrue(stack.isEmpty());
        Assertions.assertEquals(stack.size(), 0);
    }

    @Test
    @DisplayName("Popping from an empty stack does throw a NoSuchElementException")
    void popEmptyStack() {
        if (stack.isEmpty()) {
            Assertions.assertThrows(NoSuchElementException.class, () -> { stack.pop(); });
        }
    }

    @Test
    @DisplayName("Peeking into an empty stack does throw a NoSuchElementException")
    void peekEmptyStack() {
        if (stack.isEmpty()) {
            Assertions.assertThrows(NoSuchElementException.class, () -> { stack.peek(); });
        }
    }

    @Test
    @DisplayName("For bounded stacks only: pushing onto a full stack does throw an IllegalStateException")
    void pushBoundedStack() {
        TqsStack<Integer> s = new TqsStack<>(3);

        for(int i = 0; i < 3; i++) {
            s.push(i);
        }

        Assertions.assertThrows(IllegalStateException.class, () -> {s.push(1);});
    }
}


