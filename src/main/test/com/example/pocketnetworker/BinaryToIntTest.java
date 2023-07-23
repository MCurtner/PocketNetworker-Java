package com.example.pocketnetworker;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BinaryToIntTest {

    BinaryToInt binaryToInt;

    @BeforeEach
    void setUp() {
        binaryToInt = new BinaryToInt();
    }

    @AfterEach
    void tearDown() {
        binaryToInt = null;
    }

    @Test
    void binaryToInt() {
        assertEquals(192, binaryToInt.binaryToInt("11000000"));
        assertEquals(1, binaryToInt.binaryToInt("00000001"));
        assertEquals(10, binaryToInt.binaryToInt("00001010"));
        assertEquals(168, binaryToInt.binaryToInt("10101000"));
    }
}