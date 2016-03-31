package com.example.tdd;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        Adder adder = new Adder();
        assertEquals(4, adder.add(2,3));
    }
}