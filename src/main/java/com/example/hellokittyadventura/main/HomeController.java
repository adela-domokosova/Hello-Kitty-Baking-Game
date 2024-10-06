package com.example.hellokittyadventura.main;

import com.example.hellokittyadventura.logika.Hra;
import com.example.hellokittyadventura.logika.IHra;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.Optional;

public class HomeController {
    @FXML
    private Button tlacitkoOdesli;
    @FXML
    private TextArea vystup;
    @FXML
    private TextField vstup;

    @FXML
    private IHra hra = new Hra();


    //po vytvoření všech prvků FX se zavolá inicializátor,
    //rozdil od konstruktoru
    @FXML
    private void initialize(){
        vystup.appendText(hra.vratUvitani()+"\n\n");
        Platform.runLater(() -> vstup.requestFocus());
    }

    @FXML
    private void odesliVstup(ActionEvent actionEvent) {
        String prikaz = vstup.getText();
        vystup.appendText("> "+prikaz+"\n");
        String vysledek = hra.zpracujPrikaz(prikaz);
        vystup.appendText(vysledek+ "\n\n");
        vstup.clear();
        if(hra.konecHry()){
            vystup.appendText(hra.vratEpilog());
            vstup.setDisable(true);
            tlacitkoOdesli.setDisable(true);
        }
    }

    @FXML
    private void ukoncitHru(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to end the current game??");
        Optional<ButtonType> result = alert.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.OK){
            //bezpečně ukončit javaFX app
            Platform.exit();
        }

    }
}
