package com.example.hellokittyadventura.logika;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Vec {
    private String nazev;//používá se jako unikátní jmeno
    private String zobrazenyNazev;//název zobrazený hráči

    /** lzeVzit i dosahne musejí být true, aby bylo možné předmět vložit do invetáře **/
    private boolean lzeVzit;
    private boolean dosahne;//jen pro odlišnou hlášku, proč věc nelze vzít
    private boolean prozkoumal = false;//prikazOtevrit = prozkoumám lednici -> setProzkoumal = true, až po prozkoumání lze bráz předměty z lednice
    private final Map<String,Vec> veciUvnitr = new HashMap<>(); //obsahuje věci uvnitř věci

    /**KONSTRUKTOR**/
    public Vec(String nazev, String zobrazenyNazev, boolean lzeVzit, boolean dosahne) {
        this.nazev = nazev;
        this.zobrazenyNazev = zobrazenyNazev;
        this.lzeVzit = lzeVzit;
        this.dosahne = dosahne;
    }
    public String getNazev() {
        return nazev;
    }
    public String getZobrazenyNazev() {
        return zobrazenyNazev;
    }
    public Map<String, Vec> getVeciUvnitr() {
        return this.veciUvnitr;
    }
    public boolean isDosahne() {
        return dosahne;
    }//kontrola, jestli je doshane true/false
    public boolean isProzkoumal(){
        return this.prozkoumal;
    }//kontrola true/false
    public void setProzkoumal(){
        this.prozkoumal=true;
    }
    public boolean isLzeVzit() {
        return lzeVzit;
    }//kontrola true/false



    /**jestli jsou uvnitř veci uloženy další věci, metoda přijimá string a pomocí něj najde, je-li hledaná věc
     * uvnitř - ve hře použito při hledání věcí v lednici - příkaz sebrat**/
    public boolean vecObsahujeVec(String vec){
        return this.veciUvnitr.containsKey(vec);
    }
    public void pridatVecUvnitr(Vec vec){this.veciUvnitr.put(vec.getNazev(), vec);}//podobně jako předešlá metoda přidá věc do MAP



    /**
     * Metoda slouží k vypsání předmětů, které jsou uloženy uvnitř věci - Ve hře použito pro předměty uvnitř lednice
     * Vrací String se všemi jmény přemětů, které jsou uvnitř věci = jsou v MAP veciUvnitř.
     **/
    public String vypisVeciUvnitr(){
        if (this.veciUvnitr.isEmpty()){
            return "Tuto vec nemůžeš dál zkoumat";//v tomto nastavení předmětů nelze vybrat všechny věci z lednice, kó není potřeba
        }else{
            String str = "Našel jsi další předměty! Můžeš zkusit sebrat: ";
            for(String vec : this.veciUvnitr.keySet()){
                str += vec + ", ";
            }
            return str;
        }
    }

}
