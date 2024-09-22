package com.example.hellokittyadventura.logika;

import java.util.Objects;

public class PrikazZatrastJabloni implements IPrikaz{
    private static final String NAZEV = "zatrast";
    private final HerniPlan plan;
    private int index = 0;//kontrola, jestli byl příkaz už použit
    public PrikazZatrastJabloni(HerniPlan plan) {
        this.plan = plan;
    }
    private int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    /**Metoda musí být použita na jabloň v prostoru zahrada, při jejím prvním použití se v prostoru
     * objeví klíč a jablka, která hráč může sebrat a použít**/
    @Override
    public String provedPrikaz(String... parametry) {
        if (parametry.length == 0) {
            return "Čím mám zatřást??";
        }else if(plan.getAktualniProstor().getNazev() == "zahrada" && this.getIndex() == 0 && Objects.equals(parametry[0], "jabloň")){
            // Kontrolní index, jen při prvním zatřesení se objeví klíč a jablka
            this.setIndex(1);
            //Hráč se musí nacházet ja zahradě a paramet funkce musí být "jabloň"
            //klíč a jablka se prvně nacházejí v hráči nepřístupném prostoru "none"
            //zde se přidají na herní plán do prostoru "zahrada", aby je hráč mohl sebrat
            plan.getAktualniProstor().pridatVec(plan.getProstory().get("none").getVeci().get("klíč"));
            plan.getAktualniProstor().pridatVec(plan.getProstory().get("none").getVeci().get("jablka"));
            return "Z jabloně spadl neznámý klíč a jablka" + "\n" + plan.getAktualniProstor().popisVeci();
        } else if (plan.getAktualniProstor().getNazev() == "zahrada" && this.getIndex() != 0 && Objects.equals(parametry[0], "jabloň")) {
            //při dalším použití příkazu Zatřást se vrátí tato hláška
            return "Z jabloně už nic nespadne";
        } else {
            //hráč se nachází ve špatném prostoru, nebo vkládá špatný parametr
            return "Zkus zatřást něčím jiným";
        }
    }

    @Override
    public String getNazev() {
        return NAZEV;
    }
}
