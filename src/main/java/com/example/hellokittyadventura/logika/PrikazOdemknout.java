package com.example.hellokittyadventura.logika;

import java.util.Map;
import java.util.Objects;

public class PrikazOdemknout implements IPrikaz{
    private static final String NAZEV = "odemknout";
    private final HerniPlan plan;

    public PrikazOdemknout(HerniPlan plan) {
        this.plan = plan;
    }

    /** Metoda odemyká zamčený prostor Kurníku.
     * Pro odemknutí musí mít hráč v inventáři klíč, který najde při zatřesení jabloní na zahradě
     * S klíčem musí být hráč na zahradě a použít příkaz "odemknout" s parametrm "kurník".
     * Metoda mění parametr prostoru jeZamcena na false. čímž umožní hráči přejít do prostoru.**/
    @Override
    public String provedPrikaz(String... parametry) {
        if (parametry.length == 0) {
            return "Co mám odemknout??";
        } else if ((plan.getAktualniProstor().getNazev() == "zahrada" && plan.getInventar().getVeci().containsKey("klíč") && Objects.equals(parametry[0], "kurník"))){
            //mám klíč,jsem na zahradě, odemykám správné dveře
            for(Prostor key: plan.getProstory().values()){
                key.setJeZamcena(); //změni parametr "jeZamcena" místnosti a tím ji odemkne
            }
            plan.getInventar().odeberVec("klíč"); //odstranit klíč z inventaře
            return "Odemknuto";
        } else if (plan.getInventar().getVeci().containsKey("klíč") && Objects.equals(parametry[0], "kurník")) {
            //mám klíč, ale nejsem na zahradě
            return "najdi zamknuté dveře";
        }else if (Objects.equals(parametry[0], "kurník")) {
            //nemám klíč
            return "Musíš najít klíč"; //nemám klíč, ale snažím se odemknout zamčené dveře
        } else if (plan.getInventar().getVeci().containsKey("klíč")) {
            //mám klíč ale použiju ho na špatné dvěře nebo ve špatném prostoru
            return "Tady klíč nepoužívej, najdi zamčené dveře"; //mám klíč ale nepoužiju ho správně
        } else{
            //nemám klíč
            return "Aby jdi mohl něco odemknout, potřebuješ klíč!!";
        }
    }

    @Override
    public String getNazev() {
        return NAZEV;
    }
}
