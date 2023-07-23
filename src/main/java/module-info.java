module com.example.pocketnetworker {
    requires javafx.controls;
    requires javafx.fxml;
            
        requires org.controlsfx.controls;
            requires com.dlsc.formsfx;
                        
    opens com.example.pocketnetworker to javafx.fxml;
    exports com.example.pocketnetworker;
}