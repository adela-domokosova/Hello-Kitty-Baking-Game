package com.example.hellokittyadventura.main;

import com.example.hellokittyadventura.logika.*;
import javafx.application.Platform;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

import java.lang.reflect.Array;
import java.util.*;

//null případy u obrázků v batohu

public class HomeController{
    @FXML
    private HBox interakceProstory;
    @FXML
    private HBox interakcePredmety;
    @FXML
    private Button sbiratVejce;
    @FXML
    private Button odemknout;
    @FXML
    private ImageView inv1;
    @FXML
    private ImageView inv2;
    @FXML
    private ImageView inv3;
    @FXML
    private ImageView inv4;
    @FXML
    private List<ImageView> listInv = new ArrayList<>();
    @FXML
    private ListView<Vec> panelPredmetu;
    @FXML
    private ImageView hrac;
    @FXML
    private ListView<Prostor> panelVychodu;
    @FXML
    private TextArea vystup;
    @FXML
    private ObservableList<Prostor> seznamVychodu = FXCollections.observableArrayList();
    @FXML
    private ObservableList<Vec> seznamPredmetu = FXCollections.observableArrayList();
    @FXML
    private ObservableList<Vec> seznamVeci = FXCollections.observableArrayList();
    @FXML
    private SimpleStringProperty selectedString = new SimpleStringProperty();
    @FXML
    private SimpleObjectProperty<Vec> selectedVec = new SimpleObjectProperty<>();
    @FXML
    private ObservableList<String> seznamRozkazu = FXCollections.observableArrayList();

    private Vec none= new Vec("none", "none", false, false);
    private IHra hra = new Hra();
    private Inventar inv = new Inventar();
    private Map<String, Point2D> souradniceProstoru = new HashMap<>();

    //po vytvoření všech prvků FX se zavolá inicializátor,
    //rozdil od konstruktoru
    @FXML
    private void initialize(){
        odemknout.setDisable(true);
        kontrolaDisableButtonSbiratVejce();
        listInv.addAll(Arrays.asList(inv1, inv2, inv3, inv4));
        vystup.appendText(hra.vratUvitani()+"\n\n");
        panelVychodu.setItems(seznamVychodu);
        aktualizovatObrazkyInventar();
        aktualizujSeznamPredmetu();
        panelPredmetu.setItems(seznamPredmetu);
        aktualizujSeznamRozkazu();
        hra.getHerniPlan().getInventar().registruj(ZmenaHry.ZMENA_INVENTARE, () -> {
            aktualizujSeznamVeci();
        });
        hra.getHerniPlan().registruj(ZmenaHry.ZMENA_MISTNOSTI, () -> {
            aktualizujSeznamVychodu();
            aktualizujPolohuHrace();
            aktualizujSeznamPredmetu();
        });
        hra.registruj(ZmenaHry.KONEC_HRY, () -> {
            aktualizujKonecHry();
            aktualizujPolohuHrace();
        });
        aktualizujSeznamVychodu();
        vlozSouradniceProstoru();
        panelVychodu.setCellFactory(param -> new ListCellProstor());
        panelPredmetu.setCellFactory(param -> new ListCellVec());
        // Ensure single selection mode (this is the default behavior)
        panelPredmetu.getSelectionModel().setSelectionMode(javafx.scene.control.SelectionMode.SINGLE);

        // Monitor the selected item
        //vybranyPredmet();
        panelPredmetu.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue != null){
                selectedVec.set(newValue);
                selectedString.set(newValue.getNazev());
            }});

    }



    private void vlozSouradniceProstoru() {
        souradniceProstoru.put("kuchyň", new Point2D(179,58));
        souradniceProstoru.put("spižírna", new Point2D(259,20));
        souradniceProstoru.put("zahrada", new Point2D(97,98));
        souradniceProstoru.put("kurník", new Point2D(101,181));
        souradniceProstoru.put("mlýn", new Point2D(20,60));
    }


    @FXML
    private void aktualizujSeznamVeci(){
        seznamVeci.clear();
        seznamVeci.addAll(hra.getHerniPlan().getInventar().getVeci().values());
        aktualizovatObrazkyInventar();
    }
    @FXML
    private void aktualizujSeznamVychodu(){
        seznamVychodu.clear();
        seznamVychodu.addAll(hra.getHerniPlan().getAktualniProstor().getVychody());
    }

    private void aktualizujPolohuHrace(){
        String prostor = hra.getHerniPlan().getAktualniProstor().getNazev();
        kontrolaDisableButtonSbiratVejce();
        hrac.setLayoutX(souradniceProstoru.get(prostor).getX());
        hrac.setLayoutY(souradniceProstoru.get(prostor).getY());
    }
    @FXML
    private void aktualizujSeznamPredmetu(){
        seznamPredmetu.clear();
        System.out.println("test"+hra.getHerniPlan().getAktualniProstor().getVeci().values());
        seznamPredmetu.addAll(hra.getHerniPlan().getAktualniProstor().getVeci().values());
        System.out.println(seznamPredmetu);
        aktualizovatObrazkyInventar();
    }

    @FXML
    private void aktualizujSeznamRozkazu(){
        seznamRozkazu.clear();
        seznamRozkazu.addAll("seber", "otevrit", "zatrast");
    }


        private void zpracujPrikaz(String prikaz) {
        vystup.clear();
//            vystup.appendText("> " + prikaz + "\n");
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

    public String getSelectedString() {
        return selectedString.get();
    }

    @FXML
    private Vec getSelectedVec(){
        return selectedVec.get();
    }

    private void aktualizujKonecHry() {
        if (hra.konecHry()) {
            vystup.appendText(hra.vratEpilog());
        }
            panelVychodu.setDisable(true);
            panelPredmetu.setDisable(true);
            interakceProstory.setDisable(true);
            interakcePredmety.setDisable(true);

    }

    @FXML
    private void klikPanelVychodu(MouseEvent mouseEvent) {
     Prostor cil = panelVychodu.getSelectionModel().getSelectedItem();
     if(cil==null)return;
     String prikaz = PrikazJdi.NAZEV+ " " +cil.getNazev();
     zpracujPrikaz(prikaz);
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

    //zjistit který item je vybraný


    //klik na sebrat a odeslat rozkaz sebrat *item*
    public void klikButtonSebrat(MouseEvent mouseEvent) {
        if(getSelectedString() == "klíč"){
            odemknout.setDisable(false);
        }
        String rozkaz = "seber " + getSelectedString();
        //jestli lednice obsahuje toto vymaž to
        if(hra.getHerniPlan().getAktualniProstor().getNazev()=="kuchyň" && hra.getHerniPlan().getProstory().get("kuchyň").getVeci().get("lednice").vecObsahujeVec(getSelectedString())){
            hra.getHerniPlan().getAktualniProstor().getVeci().get("lednice").odebratVec(getSelectedString());
        }
        zpracujPrikaz(rozkaz);
        aktualizujSeznamPredmetu();
    }

    public void klikButtonZatrast(MouseEvent mouseEvent) {
        String rozkaz = "zatrast " + getSelectedString();
        zpracujPrikaz(rozkaz);
        aktualizujSeznamPredmetu();
    }

    public void klikButtonOtevrit(MouseEvent mouseEvent) {
        String rozkaz = "otevrit " + getSelectedString();
        zpracujPrikaz(rozkaz);
        hra.getHerniPlan().getInventar().odeberVec("klíč");
        aktualizujSeznamPredmetu();
    }

    @FXML
    private void klikButtonOdemknout(MouseEvent mouseEvent) {
        if(hra.getHerniPlan().getAktualniProstor().getNazev() == "zahrada"){

            String rozkaz = "odemknout " + getSelectedString();
            zpracujPrikaz(rozkaz);
            aktualizujSeznamPredmetu();
            aktualizujSeznamVeci();
            odemknout.setDisable(true);
        }else{
            String vysledek = "musíš jít k zamknutým dveřím";
            vystup.appendText(vysledek + "\n\n");
        }
    }
    public void klikButtonOdevzdat(MouseEvent mouseEvent) {
        String rozkaz = "odevzdat";
        zpracujPrikaz(rozkaz);
        aktualizujSeznamPredmetu();
        aktualizujSeznamVeci();
    }
    @FXML
    private void kontrolaDisableButtonSbiratVejce(){
        if(hra.getHerniPlan().getAktualniProstor().getNazev() != "kurník"){
            sbiratVejce.setDisable(true);
            System.out.println("print true");
        }else{
            sbiratVejce.setDisable(false);
        }
    }

@FXML
    private void klikButtonSbiratVejce(MouseEvent mouseEvent) {
        String rozkaz = "seber vejce";
        zpracujPrikaz(rozkaz);
        aktualizujSeznamPredmetu();
        aktualizujSeznamVeci();
    }
    ///////////////////////////////////////////////
    //change image in batoh
    //zavolam si aktuali inventar
    //forloop dvojta -> seznamVEci a seznam obrazkovych variables
    @FXML
    public void aktualizovatObrazkyInventar() {
        int size = 4 - seznamVeci.size();
        for(int i= 0; i < size+1; i++){
            seznamVeci.add(none);
        }

        for(int i =0; i<4; i++){
            Image newImage = new Image(getClass().getResourceAsStream("inventar/"+ seznamVeci.get(i) +".png"));
            listInv.get(i).setImage(newImage);
        }
        }


}


