package com.example.pocketnetworker;

public class IntToBinary {
    private static String binaryString;

    private static void intToBinary(int num) {
        binaryString = Integer.toBinaryString(num);
    }

    private static void addPadding() {
        binaryString = String.format("%8s", binaryString).replaceAll(" ", "0");
    }

    private static void addBlocks() {
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < binaryString.length(); i++) {
            stringBuffer.append(binaryString.charAt(i));
        }

        binaryString = stringBuffer.toString();
    }

    public static String convertToBinary(int num) {
        intToBinary(num);
        addPadding();
        addBlocks();

        return binaryString;
    }
}
