package com.example.pocketnetworker;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ControllerTest {

    Controller controller;

    @BeforeEach
    void setUp() {
        controller = new Controller();
    }

    @AfterEach
    void tearDown() {
        controller = null;
    }

    @Test
    void calculateNetworkClass() {
        assertEquals("A", controller.calculateNetworkClass(0));
        assertEquals("A", controller.calculateNetworkClass(127));
        assertEquals("B", controller.calculateNetworkClass(128));
        assertEquals("B", controller.calculateNetworkClass(191));
        assertEquals("C", controller.calculateNetworkClass(192));
        assertNotEquals("B", controller.calculateNetworkClass(0));
    }

    @Test
    void calculateNetmaskBinaryString() {
        assertEquals("00000000000000000000000000000000", controller.calculateNetmaskBinaryString("0"));
        assertEquals("10000000000000000000000000000000", controller.calculateNetmaskBinaryString("1"));
        assertEquals("11111111000000000000000000000000", controller.calculateNetmaskBinaryString("8"));
        assertEquals("11111111111111110000000000000000", controller.calculateNetmaskBinaryString("16"));
        assertEquals("11111111111111111110000000000000", controller.calculateNetmaskBinaryString("19"));
        assertEquals("11111111111111111111111100000000", controller.calculateNetmaskBinaryString("24"));
    }

    @Test
    void calculateWildcardBinaryString() {
        assertEquals("11111111111111111111111111111111", controller.calculateWildcardBinaryString("0"));
        assertEquals("01111111111111111111111111111111", controller.calculateWildcardBinaryString("1"));
        assertEquals("00000000111111111111111111111111", controller.calculateWildcardBinaryString("8"));
    }

    @Test
    void formattedBinaryString() {
        assertEquals("00000000.00000000.00000000.00000000", controller.formattedBinaryString("00000000000000000000000000000000"));
        assertEquals("10000000.00000000.00000000.00000000", controller.formattedBinaryString("10000000000000000000000000000000"));
        assertEquals("11111111.00000000.00000000.00000000", controller.formattedBinaryString("11111111000000000000000000000000"));
        assertEquals("11111111.11111111.00000000.00000000", controller.formattedBinaryString("11111111111111110000000000000000"));
    }
    @Test
    void calculateIpStringFromBinaryString() {
        assertArrayEquals(new int[]{192, 168, 1, 1}, controller.calculateBinaryStringToIntArray("11000000.10101000.00000001.00000001"));
    }

    @Test
    void formatIntArrayToStringIpAddress() {
        assertEquals("192.168.1.1", controller.formatIntArrayToStringIpAddress(new int[]{192, 168, 1, 1}));
    }
}