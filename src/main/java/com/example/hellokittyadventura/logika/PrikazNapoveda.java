package com.example.hellokittyadventura.logika;

import java.util.HashMap;
import java.util.Map;

/**
 *  Třída PrikazNapoveda implementuje pro hru příkaz napoveda.
 *  Tato třída je součástí jednoduché textové hry.
 *  
 *@author     Jarmila Pavlickova, Luboš Pavlíček
 *@version    pro školní rok 2016/2017
 *  
 */
class PrikazNapoveda implements IPrikaz {
    
    private static final String NAZEV = "nápověda";
    private SeznamPrikazu platnePrikazy;
    private final HerniPlan plan;



    /**
    *  Konstruktor třídy
    *  
    *  @param platnePrikazy seznam příkazů,
    *                       které je možné ve hře použít,
    *                       aby je nápověda mohla zobrazit uživateli. 
    */    
    public PrikazNapoveda(SeznamPrikazu platnePrikazy, HerniPlan plan) {
        this.platnePrikazy = platnePrikazy;
        this.plan = plan;
    }
    
    /**
     *  Vrací základní nápovědu po zadání příkazu "napoveda".
     *  Vrací zprávu s primárním úkolem hráče ve hře, předměty, které ještě musí odevzdat pro výhru,
     *  a seznam dostupných příkazů
     *
     *  @return napoveda ke hre
     */
    @Override
    public String provedPrikaz(String... parametry) {
        return "Tvým úkolem je najít všechny suroviny, které potřebuješ na upečení jablečného koláče\n"+
                "Musíš ještě odevzdet tyto suroviny: " + plan.chybi() + "\n"
        + "Můžeš zadat tyto příkazy:\n"
        + platnePrikazy.vratNazvyPrikazu();
    }

     /**
     *  Metoda vrací název příkazu (slovo které používá hráč pro jeho vyvolání)
     *  
     *  @ return nazev prikazu
     */
    @Override
      public String getNazev() {
        return NAZEV;
     }

}
