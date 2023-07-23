package com.example.pocketnetworker;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.util.StringTokenizer;

public class Controller {

    public Label classLbl;
    @FXML
    private TextField ipAddressTextField;
    @FXML
    private TextField netmaskTextField;
    @FXML
    private Label addressLbl;
    @FXML
    private Label addressBinaryLbl;
    @FXML
    private Label netmaskLbl;
    @FXML
    private Label netmaskBinaryLbl;
    @FXML
    private Label wildcardLbl;
    @FXML
    private Label wildcardBinaryLbl;
    @FXML
    private Label networkLbl;
    @FXML
    private Label broadcastLbl;


    private String ipAddressStr;
    private String netmaskStr;
    private String netmaskBinaryStr;
    private int[] octetIntArray = new int[4];
    private String[] octetBinaryStrArray = new String[4];

    public void calculate(ActionEvent e) {
        getIPAddress();
        getNetmaskNumber();
        parseIpAddress();
        netmaskBinaryStr = calculateNetmaskBinaryString(netmaskStr);

        setClassLbl();
        setAddressLbl();
        setAddressBinaryLbl();
        setNetmaskLbl();
        setNetmaskBinaryLbl();
        setWildcardLbl();
        setWildcardBinaryLbl();
    }

    private void getIPAddress() {
        ipAddressStr = ipAddressTextField.getText();
    }

    private void getNetmaskNumber() {
        netmaskStr = netmaskTextField.getText();
    }

    private void setClassLbl() {
        classLbl.setText(calculateNetworkClass(octetIntArray[0]));
    }

    private void setAddressLbl() {
        addressLbl.setText(ipAddressStr);
    }

    private void setAddressBinaryLbl() {
        addressBinaryLbl.setText(binaryStringBuilder());
    }

    private void setNetmaskLbl() {
        String formattedStr = formattedBinaryString(netmaskBinaryStr);
        int[] intArray = calculateBinaryStringToIntArray(formattedStr);
        netmaskLbl.setText(formatIntArrayToStringIpAddress(intArray));
    }

    private void setNetmaskBinaryLbl() {
        String formattedStr = formattedBinaryString(netmaskBinaryStr);
        netmaskBinaryLbl.setText(formattedStr);
    }

    private void setWildcardLbl() {
        String formattedStr = formattedBinaryString(calculateWildcardBinaryString(netmaskStr));
        int[] intArray = calculateBinaryStringToIntArray(formattedStr);
        wildcardLbl.setText(formatIntArrayToStringIpAddress(intArray));
    }

    private void setWildcardBinaryLbl() {
        String formattedStr = formattedBinaryString(calculateWildcardBinaryString(netmaskStr));
        wildcardBinaryLbl.setText(formattedStr);
    }


    private String binaryStringBuilder() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < octetBinaryStrArray.length; i++) {
            stringBuilder.append(octetBinaryStrArray[i]);
            if (i != octetBinaryStrArray.length - 1) {
                stringBuilder.append(".");
            }
        }

        return stringBuilder.toString();
    }

    /**
     * Formats the provided binary string into the proper decimal formatted version.
     *
     * @param binaryStr Unformatted binary string value.
     * @return Formatted binary string value.
     */
    public String formattedBinaryString(String binaryStr) {
        StringBuilder sb = new StringBuilder(binaryStr);
        for (int i = 0; i < binaryStr.length(); i++) {
            if (i == 8 || i == 17 || i == 26) {
                sb.insert(i, ".");
            }
        }

        return sb.toString();
    }

    /**
     * Calculate the network class based on the first octet value.
     *
     * @return String value of class.
     */
    public String calculateNetworkClass(int num) {
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
     * Calcuate the netmask binary string based on the provided netmask CIDR value.
     *
     * @param netmaskStr The netmask CIDR value. e.g.: 24
     * @return Binary string of the netmask.
     */
    public String calculateNetmaskBinaryString(String netmaskStr) {
        final int totalDigits = 32;
        int netmaskNum = Integer.parseInt(netmaskStr);

        return "1".repeat(Math.max(0, netmaskNum)) +
                "0".repeat(Math.max(0, totalDigits - netmaskNum));
    }

    /**
     * Calcuate the wildcard binary string based on the provided netmask CIDR value.
     *
     * @param netmaskStr The wildcard CIDR value. e.g.: 24
     * @return Binary string of the wildcard.
     */
    public String calculateWildcardBinaryString(String netmaskStr) {
        final int totalDigits = 32;
        int netmaskNum = Integer.parseInt(netmaskStr);

        return "0".repeat(Math.max(0, netmaskNum)) +
                "1".repeat(Math.max(0, totalDigits - netmaskNum));
    }


    /**
     * Convert the binary string to integer values and store in an array for further processing.
     *
     * @param binaryStr The binary string value.
     * @return An array of integer values of the ip address.
     */
    public int[] calculateBinaryStringToIntArray(String binaryStr) {
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
     * Format the given octet array into an IP Address string.
     *
     * @param array Octet array of ip decimal value.
     * @return String formatted ip address.
     */
    public String formatIntArrayToStringIpAddress(int[] array) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < array.length; i++) {
            sb.append(array[i]);
            if (i < array.length - 1) {
                sb.append(".");
            }
        }
        return sb.toString();
    }

    private void parseIpAddress() {
        StringTokenizer tokenizer = new StringTokenizer(ipAddressStr, ".");
        int i = 0;
        while (tokenizer.hasMoreTokens()) {
            String strVal = tokenizer.nextToken();

            try {
                int val = Integer.parseInt(strVal, 10);

                if (val < 0 || val > 255) {
                    throw new IllegalArgumentException("Illegal value '" + val + "' at byte " + (i + 1) + " in the IP address.");
                }

                octetIntArray[i] = val;
                octetBinaryStrArray[i++] = IntToBinary.convertToBinary(val);
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Illegal value '" + strVal + "' at token " + (i + 1) + " in the IP address.", e);
            }
        }
    }
}