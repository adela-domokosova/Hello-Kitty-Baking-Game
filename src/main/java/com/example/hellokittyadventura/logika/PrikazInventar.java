package com.example.hellokittyadventura.logika;

public class PrikazInventar implements IPrikaz {
    private static final String NAZEV = "inventar";
    private final HerniPlan plan;

    public PrikazInventar(HerniPlan plan) {
        this.plan = plan;
    }

    /** Metoda vrací "ZobrazenyNazev" předmětů v inventaři **/
    @Override
    public String provedPrikaz(String... parametry) {
        if(plan.getInventar().jePrazdny()){
            return "Nic v inventari nemáš";
        }
        return plan.getInventar().vypsatObsah();
    }

    @Override
    public String getNazev() {
        return NAZEV;
    }
}
