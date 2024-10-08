package com.example.hellokittyadventura.main;

import com.example.hellokittyadventura.logika.Hra;
import com.example.hellokittyadventura.logika.IHra;
import com.example.hellokittyadventura.logika.PrikazJdi;
import com.example.hellokittyadventura.logika.Prostor;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArrayBase;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import java.util.Optional;

public class HomeController{
    @FXML
    private ListView<Prostor> panelVychodu;
    @FXML
    private Button tlacitkoOdesli;
    @FXML
    private TextArea vystup;
    @FXML
    private TextField vstup;


    private IHra hra = new Hra();
    @FXML
    private ObservableList<Prostor> seznamVychodu = FXCollections.observableArrayList();

    //po vytvoření všech prvků FX se zavolá inicializátor,
    //rozdil od konstruktoru
    @FXML
    private void initialize(){
        vystup.appendText(hra.vratUvitani()+"\n\n");
        Platform.runLater(() -> vstup.requestFocus());
        panelVychodu.setItems(seznamVychodu);
        hra.getHerniPlan().registruj(ZmenaHry.ZMENA_MISTNOSTI, () -> aktualizujSeznamVychodu());
        hra.registruj(ZmenaHry.KONEC_HRY, () -> aktualizujKonecHry());
        aktualizujSeznamVychodu();
    }


    @FXML
    private void aktualizujSeznamVychodu(){
        seznamVychodu.clear();
        seznamVychodu.addAll(hra.getHerniPlan().getAktualniProstor().getVychody());
    }
    @FXML
    private void odesliVstup(ActionEvent actionEvent) {
        String prikaz = vstup.getText();
        vstup.clear();

        zpracujPrikaz(prikaz);

        }

        private void zpracujPrikaz(String prikaz) {
            vystup.appendText("> " + prikaz + "\n");
            String vysledek = hra.zpracujPrikaz(prikaz);
            vystup.appendText(vysledek + "\n\n");
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

    private void aktualizujKonecHry() {
        if (hra.konecHry()) {
            vystup.appendText(hra.vratEpilog());
        }
            vstup.setDisable(true);
            tlacitkoOdesli.setDisable(true);
            panelVychodu.setDisable(true);
    }

    @FXML
    private void klikPanelVychodu(MouseEvent mouseEvent) {
     Prostor cil = panelVychodu.getSelectionModel().getSelectedItem();
     if(cil==null)return;
     String prikaz = PrikazJdi.NAZEV+ " " +cil;
     zpracujPrikaz(prikaz);

    }
}
