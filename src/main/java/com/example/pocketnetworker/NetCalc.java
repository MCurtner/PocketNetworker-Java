package com.example.pocketnetworker;

import java.util.StringTokenizer;

public class NetCalc {

    private String ipAddressStr;
    private String netmaskStr;
    private String networkClass;
    private String ipAddressBinaryStr;
    private String netmaskIpAddressStr;
    private String netmaskBinaryStr;
    private String wildcardIpStr;
    private String wildcardBinaryStr;
    private String networkIpStr;
    private String networkBinaryStr;
    private String broadcastIpStr;
    private String broadcastBinaryStr;
    private String hostPerNetStr;

    public NetCalc(String ipAddressStr, String netmaskStr) {
        this.ipAddressStr = ipAddressStr;
        this.netmaskStr = netmaskStr;

        calc();
    }

    /*----------------Getter Methods ----------------*/
    public String getIpAddressStr() {
        return ipAddressStr;
    }

    public String getNetmaskStr() {
        return netmaskStr;
    }

    public String getNetworkClass() {
        return networkClass;
    }

    public String getIpAddressBinaryStr() {
        return ipAddressBinaryStr;
    }

    public String getNetmaskIpAddressStr() {
        return netmaskIpAddressStr;
    }

    public String getNetmaskBinaryStr() {
        return netmaskBinaryStr;
    }

    public String getWildcardIpStr() {
        return wildcardIpStr;
    }

    public String getWildcardBinaryStr() {
        return wildcardBinaryStr;
    }

    public String getNetworkIpStr() {
        return networkIpStr;
    }

    public String getNetworkBinaryStr() {
        return networkBinaryStr;
    }

    public String getBroadcastIpStr() {
        return broadcastIpStr;
    }

    public String getBroadcastBinaryStr() {
        return broadcastBinaryStr;
    }

    public String getHostPerNetStr() {
        return hostPerNetStr;
    }

    /*----------------Impl Methods ----------------*/
    private void calc() {
        int[] octetIntArray = splitIpAddress(ipAddressStr);

        String[] octetBinaryArray = new String[4];
        for (int i = 0; i < octetIntArray.length; i++) {
            octetBinaryArray[i] = IntToBinary.convertToBinary(octetIntArray[i]);
        }

        networkClass = calculateNetworkClass(octetIntArray[0]);

        ipAddressBinaryStr = formattedBinaryString(binaryStrArrayToString(octetBinaryArray));

        netmaskBinaryStr = formattedBinaryString(calculateNetmaskBinaryString(netmaskStr));
        netmaskIpAddressStr = formatIntArrayToStringIpAddress(binaryStringToIntArray(netmaskBinaryStr));;

        wildcardBinaryStr = formattedBinaryString(calculateWildcardBinaryString(netmaskStr));
        wildcardIpStr = formatIntArrayToStringIpAddress(binaryStringToIntArray(wildcardBinaryStr));

        networkBinaryStr = logicalANDing(ipAddressBinaryStr, netmaskBinaryStr);
        networkIpStr = formatIntArrayToStringIpAddress(binaryStringToIntArray(networkBinaryStr));

        broadcastBinaryStr = logicalORing(ipAddressBinaryStr, wildcardBinaryStr);
        broadcastIpStr = formatIntArrayToStringIpAddress(binaryStringToIntArray(broadcastBinaryStr));

        hostPerNetStr = Integer.toString(calculateHostPerNet(netmaskBinaryStr));
    }

    private String binaryStrArrayToString(String[] strArry) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < strArry.length; i++) {
            stringBuilder.append(strArry[i]);
        }

        return stringBuilder.toString();
    }

    /**
     * Convert the binary string to integer values and store in an array for further processing.
     *
     * @param binaryStr The binary string value.
     * @return An array of integer values of the ip address.
     */
    private int[] binaryStringToIntArray(String binaryStr) {
        int[] octetArry = new int[4];
        StringTokenizer tokenizer = new StringTokenizer(binaryStr, ".");
        int i = 0;
        while (tokenizer.hasMoreTokens()) {
            String strVal = tokenizer.nextToken();
            octetArry[i++] = BinaryToInt.binaryToInt(strVal);
        }

        return octetArry;
    }

    /**
     * Calculate the number of max usable hosts based on the netmask.
     *
     * @param netmaskBinaryStr The netmask binary string.
     * @return int value of max number of usable hosts (2^n - 2).
     */
    private int calculateHostPerNet(String netmaskBinaryStr) {
        int maxHosts = calculateMaxHosts(netmaskBinaryStr);
        return maxHosts - 2;
    }

    /**
     * Calculate the number of max host based on the netmask.
     *
     * @param netmaskBinaryStr The netmask binary string.
     * @return int value of max number of hosts 2^n.
     */
    private int calculateMaxHosts(String netmaskBinaryStr) {
        int emptyBits = 0;
        for (int i = 0; i < netmaskBinaryStr.length(); i++) {
            if (netmaskBinaryStr.charAt(i) == '0') {
                emptyBits += 1;
            }
        }

        return (int) Math.pow(2, emptyBits);
    }

    /**
     * Calcuate the netmask binary string based on the provided netmask CIDR value.
     *
     * @param netmaskStr The netmask CIDR value. e.g.: 24
     * @return Binary string of the netmask.
     */
    private String calculateNetmaskBinaryString(String netmaskStr) {
        final int totalDigits = 32;
        int netmaskNum = Integer.parseInt(netmaskStr);

        return "1".repeat(Math.max(0, netmaskNum)) +
                "0".repeat(Math.max(0, totalDigits - netmaskNum));
    }

    /**
     * Calculate the network class based on the first octet value.
     *
     * @return String value of class.
     */
    private String calculateNetworkClass(int num) {
        if (num >= 0 && num <= 127) {
            return "A";
        } else if (num >= 128 && num <= 191) {
            return "B";
        } else if (num >= 192 && num <= 223) {
            return "C";
        } else if (num >= 224 && num <= 239) {
            return "D";
        } else if (num >= 240 && num <= 255) {
            return "E";
        } else {
            return "Unkown";
        }
    }

    /**
     * Calcuate the wildcard binary string based on the provided netmask CIDR value.
     *
     * @param netmaskStr The wildcard CIDR value. e.g.: 24
     * @return Binary string of the wildcard.
     */
    private String calculateWildcardBinaryString(String netmaskStr) {
        final int totalDigits = 32;
        int netmaskNum = Integer.parseInt(netmaskStr);

        return "0".repeat(Math.max(0, netmaskNum)) +
                "1".repeat(Math.max(0, totalDigits - netmaskNum));
    }

    /**
     * Format the given octet array into an IP Address string.
     *
     * @param array Octet array of ip decimal value.
     * @return String formatted ip address.
     */
    private String formatIntArrayToStringIpAddress(int[] array) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < array.length; i++) {
            sb.append(array[i]);
            if (i < array.length - 1) {
                sb.append(".");
            }
        }
        return sb.toString();
    }

    /**
     * Formats the provided binary string into the proper decimal formatted version.
     *
     * @param binaryStr Unformatted binary string value.
     * @return Formatted binary string value.
     */
    private String formattedBinaryString(String binaryStr) {
        StringBuilder sb = new StringBuilder(binaryStr);
        for (int i = 0; i < binaryStr.length(); i++) {
            if (i == 8 || i == 17 || i == 26) {
                sb.insert(i, ".");
            }
        }

        return sb.toString();
    }

    /**
     * Calculate the logical AND of two binary strings.
     *
     * @param bs1 First binary string
     * @param bs2 Second binary string
     * @return Output of bitwise AND.
     */
    private String logicalANDing(String bs1, String bs2) {
        StringBuilder res = new StringBuilder();
        for (int i = 0; i < bs1.length(); i++) {
            res.append((char) ((bs1.charAt(i) - '0' & bs2.charAt(i) - '0') + '0'));
        }

        return res.toString();
    }

    /**
     * Calculate the logical OR of two binary strings.
     *
     * @param bs1 First binary string
     * @param bs2 Second binary string
     * @return Output of bitwise OR.
     */
    private String logicalORing(String bs1, String bs2) {
        StringBuilder res = new StringBuilder();
        for (int i = 0; i < bs1.length(); i++) {
            res.append((char) ((bs1.charAt(i) - '0' | bs2.charAt(i) - '0') + '0'));
        }

        return res.toString();
    }

    private int[] splitIpAddress(String ipAddressStr) {
        StringTokenizer tokenizer = new StringTokenizer(ipAddressStr, ".");
        int i = 0;
        int[] valArr = new int[4];
        while (tokenizer.hasMoreTokens()) {
            String strVal = tokenizer.nextToken();

            try {
                int val = Integer.parseInt(strVal, 10);
                if (val < 0 || val > 255) {
                    throw new IllegalArgumentException("Illegal value '" + val + "' at byte " + (i + 1) + " in the IP address.");
                }

                valArr[i++] = val;
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Illegal value '" + strVal + "' at token " + (i + 1) + " in the IP address.", e);
            }
        }

        return valArr;
    }
}
