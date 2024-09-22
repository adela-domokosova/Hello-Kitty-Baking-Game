package com.example.hellokittyadventura.logika;

import java.util.*;


public class PrikazOdevzdat implements IPrikaz{
    private static final String NAZEV = "odevzdat";
    private final HerniPlan plan;
    private Hra hra;


    public PrikazOdevzdat(HerniPlan plan, Hra hra) {
        this.plan = plan;
        this.hra = hra;
    }
    /**Metoda prochází Věci, které má hráč v inventáři.
     * V inventáři se mohou nacházet 3 typy předmětů:
     * 1) klíč
     * - musí zůstat v inventáři, aby ho hráč mohl použít
     *
     * 2) vítězné předměty
     * - předměty se shodují s některým z předmětů v MAP WinList v herním plánu
     *   -> tedy předměty, které jsou na začátku hry zvoleny jako podmínka pro výhru
     * - jsou odebrány z inventáře a přidány do MAP odevzdaneWinList v herním plánu
     *
     * 3) nadbytečné
     * - některé předměty na mapě nejsou podmínkou k vítězství
     *   hráč je ale může sebrat a pak mu překážejí v inventáři.
     *   Zde jsou hráči odebrány z inventáře
     *
     *   Potom co jsou předměty odevzdány dojde k porovnání WinList a odevzdaneWinList
     *   obsahují-li oba stejné Věci -> hra je ukončena a hráč vyhrál.**/
    @Override
    public String provedPrikaz(String... parametry) {
        //nelze použít for loop když tu mapu měním v průběhu
        if (plan.getAktualniProstor().getNazev() == "kuchyň") {
            System.out.println("Tak co mi neseš?");
            Iterator<String> iterator = plan.getInventar().getVeci().keySet().iterator();
            while (iterator.hasNext()) {
                String key = iterator.next();
                Vec vkladamObj = plan.getInventar().getVeci().get(key);
                if (Objects.equals(key, "klíč")) {
                    //klíč, skip
                } else if (plan.getWinList().containsValue(vkladamObj)) {
                    //wincon
                    plan.pridatDoOdevzdaneWinList(vkladamObj);
                    iterator.remove();
                } else {
                    //remove
                    iterator.remove();
                }
            }
            ukoncitHru();//kontrola, jestli jsou odevzdané všechny předměty -> konec hry
            return "Už jsi odevzdal: " + plan.odevzdane();
        }else{
            //hráč není v kuchyni
            return "musíš do kuchyně";
        }
    }
    /**Do WinList se přidávají předměty, které jaou podmínkou pro výhru hned na začátku hry,
     * jinak se jeho obsah nemění.
     * Do odevzdanéWinList se přidávají jen předměty, jteré odpovídají jednou z těch ve WinList,
     * jiný předmět se tam nemůže dostat.
     * Stačí tedy v této metodě porovnat velikosti WinList a odevzdaneWinList, pro ověření,
     * jestli hráč odevzdal všechny potřebné Věci. **/
    private void ukoncitHru(){
        if (plan.getOdevzdaneWinList().size() == plan.getWinList().size()){
            hra.setKonecHry(true);
        }
    }
    @Override
    public String getNazev() {
        return NAZEV;
    }

}

