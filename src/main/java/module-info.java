module com.example.hellokittyadventura {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.hellokittyadventura.main to javafx.fxml;
    exports com.example.hellokittyadventura.main;
}