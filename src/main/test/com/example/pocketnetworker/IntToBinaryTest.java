package com.example.pocketnetworker;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class IntToBinaryTest {

    IntToBinary intToBinary;
    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
        intToBinary = null;
    }

    @Test
    void convertToBinary() {
        assertEquals("11000000", IntToBinary.convertToBinary(192));
        assertEquals("00000001", IntToBinary.convertToBinary(1));
    }
}