package com.example.pocketnetworker;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class Controller {

    @FXML
    private Label classLbl;
    @FXML
    private Label hostsPerNet;
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
    @FXML
    private Label networkBinaryLbl;
    @FXML
    private Label broadcastBinaryLbl;

    public void calculate(ActionEvent e) {

        NetCalc netCalc = new NetCalc(ipAddressTextField.getText(), netmaskTextField.getText());

        classLbl.setText(netCalc.getNetworkClass());

        addressLbl.setText(netCalc.getIpAddressStr());
        addressBinaryLbl.setText(netCalc.getIpAddressBinaryStr());

        netmaskLbl.setText(netCalc.getNetmaskIpAddressStr());
        netmaskBinaryLbl.setText(netCalc.getNetmaskBinaryStr());

        wildcardLbl.setText(netCalc.getWildcardIpStr());
        wildcardBinaryLbl.setText(netCalc.getWildcardBinaryStr());

        networkLbl.setText(netCalc.getNetworkIpStr());
        networkBinaryLbl.setText(netCalc.getNetworkBinaryStr());

        broadcastLbl.setText(netCalc.getBroadcastIpStr());
        broadcastBinaryLbl.setText(netCalc.getBroadcastBinaryStr());

        hostsPerNet.setText(netCalc.getHostPerNetStr());
    }
}