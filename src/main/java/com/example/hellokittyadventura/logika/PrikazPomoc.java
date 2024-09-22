package com.example.hellokittyadventura.logika;

import java.util.Objects;

public class PrikazPomoc implements IPrikaz {
    private static final String NAZEV = "pomoc";
    private final HerniPlan plan;
    public PrikazPomoc(HerniPlan plan) {
        this.plan = plan;
    }

    /** Nápověda pro každou místnost, napoví, který předmět by v dané místnosti mohl být potřebný
     * k vítězství **/
    @Override
    public String provedPrikaz(String... parametry) {
        if (plan.getAktualniProstor().getNazev() == "kuchyň") {
            return "Zkus otevřít lednici";
        } else if (plan.getAktualniProstor().getNazev() =="spižírna") {
            return "Zkus se podívat po skořici";
        } else if (plan.getAktualniProstor().getNazev() == "zahrada") {
            return "Najdi jablka a klíč";
        }else if (plan.getAktualniProstor().getNazev() == "kurník"){
            return "Zkus sebrat vejce od slepic";
        }else{
            return "Někde tu musí být mouka!";
        }
        }

    @Override
    public String getNazev() {
        return NAZEV;
    }
}
