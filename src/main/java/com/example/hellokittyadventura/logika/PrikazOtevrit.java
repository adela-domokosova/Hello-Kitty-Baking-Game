package com.example.hellokittyadventura.logika;

import java.util.List;
import java.util.Objects;

public class PrikazOtevrit implements IPrikaz{
    private static final String NAZEV = "otevrit";
    private final HerniPlan plan;
    public PrikazOtevrit(HerniPlan plan) {
        this.plan = plan;
    }

    /** Metoda může být použita jen s parametrem lednice, v kuchyni
     *  každý předmět má defaultně nastaven atribut prozkoumal = false.
     *  V případě lednice je potřeba nejříve použít příkaz Otevřít, který nastaví prozkoumal = true
     *  to pak umožní hráči sebrat předměty, které se v lednici nacházení.
     *  Předměty v lednici nejsou součástí prostoru, nevypisujís e tedy hráči jako "Věci v prostoru"**/
    @Override
    public String provedPrikaz(String... parametry) {
        if (parametry.length == 0) {
            return "Co mám otevřit?";
        } else if (plan.getAktualniProstor().getNazev() =="kuchyň" && Objects.equals(parametry[0], "lednice")) {
            plan.getAktualniProstor().getVeci().get("lednice").setProzkoumal(); //prozkoumal = true
            System.out.println(plan.getAktualniProstor().getVeci() + " otevrit 1");
            //pridat do prostoru kuchyň
            plan.getAktualniProstor().pridatVeci(plan.getAktualniProstor().getVeci().get("lednice").getVeciUvnitr().values());
            System.out.println(plan.getAktualniProstor().getVeci()+ " otevrit2");

            //plan.getAktualniProstor().getVeci().get("lednice").odebratVeci(plan.getAktualniProstor().getVeci().get("lednice").getVeciUvnitr().values());
            return plan.getAktualniProstor().getVeci().get("lednice").vypisVeciUvnitr();
        }else{
            //špatný parametr nebo špatný aktualní prostor
            return "Zkus otevřít něco jiného";
        }
    }

    @Override
    public String getNazev() {
        return NAZEV;
    }
}
