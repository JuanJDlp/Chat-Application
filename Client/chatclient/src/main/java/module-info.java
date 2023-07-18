module com.arkjj {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.arkjj to javafx.fxml;
    exports com.arkjj;
}
