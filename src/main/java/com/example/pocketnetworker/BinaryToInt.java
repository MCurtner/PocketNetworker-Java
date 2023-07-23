package com.example.pocketnetworker;

public class BinaryToInt {

    /**
     * Convert a binary string value to an int value.
     *
     * @param binaryStr The binary string value.
     * @return Integer value of the binary string.
     */
    public static int binaryToInt(String binaryStr) {
        return Integer.parseInt(binaryStr, 2);
    }
}
