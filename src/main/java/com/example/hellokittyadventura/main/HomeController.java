package com.example.hellokittyadventura.main;

import com.example.hellokittyadventura.logika.*;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArrayBase;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

import java.util.*;

public class HomeController{
    @FXML
    private ListView<Vec> panelPredmetu;
    @FXML
    private ImageView hrac;
    @FXML
    private ListView<Prostor> panelVychodu;
    @FXML
    private Button tlacitkoOdesli;
    @FXML
    private TextArea vystup;
    @FXML
    private TextField vstup;
    @FXML
    private ObservableList<Prostor> seznamVychodu = FXCollections.observableArrayList();

    private IHra hra = new Hra();
    private Inventar inv = new Inventar();
    private Map<String, Point2D> souradniceProstoru = new HashMap<>();
    private List<String> veciInventar = new ArrayList<>();
    private ObservableList<Vec> seznamVeci = FXCollections.observableArrayList();


    //po vytvoření všech prvků FX se zavolá inicializátor,
    //rozdil od konstruktoru
    @FXML
    private void initialize(){
        vystup.appendText(hra.vratUvitani()+"\n\n");
        Platform.runLater(() -> vstup.requestFocus());
        panelVychodu.setItems(seznamVychodu);
        panelPredmetu.setItems(seznamVeci);
        hra.getHerniPlan().registruj(ZmenaHry.ZMENA_MISTNOSTI, () -> {aktualizujSeznamVychodu();
            aktualizujPolohuHrace();
            aktualizujSeznamPredmetu();});
        hra.registruj(ZmenaHry.KONEC_HRY, () -> {aktualizujKonecHry();
        aktualizujPolohuHrace();});
        //hra.getHerniPlan().registruj(ZmenaHry.ZMENA_INVENTARE, () -> {
        //    aktualizujBatoh();
        //});
        hra.getHerniPlan().registruj(ZmenaHry.ZMENA_PROSTOR, () ->{
            aktualizujVeci();

        });
        aktualizujSeznamVychodu();
        aktualizujSeznamPredmetu();
        vlozSouradniceProstoru();
        panelVychodu.setCellFactory(param -> new ListCellProstor());
    }

    private void aktualizujVeci() {

    }


    private void vlozSouradniceProstoru() {
        souradniceProstoru.put("kuchyň", new Point2D(179,58));
        souradniceProstoru.put("spižírna", new Point2D(259,20));
        souradniceProstoru.put("zahrada", new Point2D(97,98));
        souradniceProstoru.put("kurník", new Point2D(101,181));
        souradniceProstoru.put("mlýn", new Point2D(20,60));
    }




    private void aktualizujPolohuHrace(){
        String prostor = hra.getHerniPlan().getAktualniProstor().getNazev();
        hrac.setLayoutX(souradniceProstoru.get(prostor).getX());
        hrac.setLayoutY(souradniceProstoru.get(prostor).getY());
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
     String prikaz = PrikazJdi.NAZEV+ " " +cil.getNazev();
     zpracujPrikaz(prikaz);

    }

    //jedna vrátí tu aktualini array se stringy těch png

    @FXML
    private List<String> aktualizujInventar(List<String> veciInventar){
        return veciInventar;
    }
    //druha je přiřadí těm pozicím
    //nejprve zobrazit itemy v prostoru
    private void aktualizujBatoh() {
        //aktualizujInventar()
    }
    @FXML
    private void napovedaKlik(ActionEvent actionEvent) {
        Stage napovedaStage = new Stage();
        WebView wv = new WebView();
        Scene napovedaScena = new Scene(wv);
        napovedaStage.setScene(napovedaScena);
        napovedaStage.show();
        wv.getEngine().load(getClass().getResource("napoveda.html").toExternalForm());
    }
    @FXML
    private void aktualizujSeznamVychodu(){
        seznamVychodu.clear();
        seznamVychodu.addAll(hra.getHerniPlan().getAktualniProstor().getVychody());
    }
    @FXML
    private void aktualizujSeznamPredmetu(){
        seznamVeci.clear();
        seznamVeci.addAll(hra.getHerniPlan().getAktualniProstor().getVeci().values());
    }
    public void klikPanelPredmetu(MouseEvent mouseEvent) {
    }
}
