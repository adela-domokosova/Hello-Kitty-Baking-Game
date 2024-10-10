package com.example.hellokittyadventura.logika;

import java.util.Random;

public class PrikazSebrat implements IPrikaz{
    /**
     * Příkaz sebrat zajišťuje sběr věcí do inventáře a řeší všechny vyjímky,
     * které při vkládání věci do inventáře mohou nastat.
     * Vkládání je rozděleno na tři specifické případy:
     * 1)vkládání věci z lednice
     * 2)sběr vajec v kurníku
     * 3)sběr všech ostatních věcí
     * **/
    private static final String NAZEV = "seber";
    private final HerniPlan plan;
    private int index=0;

    public PrikazSebrat(HerniPlan plan) {
        this.plan = plan;
    }

    @Override
    public String provedPrikaz(String... parametry) {
        if (parametry.length == 0) {
            return "Co mám sebrat??";
        } else if (plan.getAktualniProstor().getNazev() == "kuchyň" && plan.getAktualniProstor().getVeci().get("lednice").vecObsahujeVec(parametry[0]) && plan.getAktualniProstor().getVeci().get("lednice").isProzkoumal()) {
            //jsem v kuchyni, parametr je věc, která se nechází v lednici
            //je nutné předem použít příkaz "otevrit lednice" -> prozkoumal = true
            return sebratZLednice(parametry[0]);
        } else if (plan.getAktualniProstor().getNazev() =="kurník" && parametry[0].trim().equalsIgnoreCase("vejce")) {
            //jsem v kurníku, parametr je vejce
            return sebratZKurniku();
        } else if (plan.getAktualniProstor().jeZdeVec(parametry[0])) {
            //sebrat pro všechny ostatní případy, věc se nachází v aktualnim prostoru
            return sebratGeneral(parametry[0]);
        }
        return "zkus to jinde";//věc neexistuje nebo se nenachází v aktualním prostoru
    }

    /** 3) Sběr všech ostatních věcí **/
    private String sebratGeneral(String param){
        Vec pridavam = plan.getAktualniProstor().getVeci().get(param);
        if (pridavam.isLzeVzit() && !plan.getInventar().jePlny() && pridavam.isDosahne()) {
            //případ: věc lzeVzit = true a v inventaři je místo
            plan.getInventar().vlozVec(pridavam);
            plan.getAktualniProstor().odebratVec(param);
            return "vloženo do inventare";
            //check true lze vzit vec
        } else if (plan.getInventar().jePlny()) {
            //případ: v inventaři není místo
            return "nemas misto v inventary";
        }else if (!pridavam.isDosahne()) {
            //případ: dosahne = false, lzeVzit = false/true;
            return "Na to nedosáhneš! Je to moc vysoko";
        } else {
            //případ: lzeVzit = false (dosahne = true)
            return "Věc nemůžeš vzít!!";
        }}

    /** 1) Vkládání věcí z lednice **/
    private String sebratZLednice(String param){
        Vec pridavam = plan.getAktualniProstor().getVeci().get("lednice").getVeciUvnitr().get(param);
        //return metody je dán atributy (lzeVzit a dosahne) toho předmětu, který se snaží sebrat
        if (pridavam.isLzeVzit() && !plan.getInventar().jePlny() && pridavam.isDosahne()) {
            //vše splněno, v inventaři je místo
            plan.getInventar().vlozVec(pridavam);//přidat do inventare
            plan.getAktualniProstor().getVeci().get("lednice").getVeciUvnitr().remove(param);//odebrat z lednice
            return "vloženo do inventáře";
        }else if (plan.getInventar().jePlny()) {
            //inventář je plný
            return "nemas misto v inventary";
        } else if (!pridavam.isDosahne()) {
            //dosahne = false, lzeVzit = true/false
            return "Na to nedosáhneš! Je to moc vysoko";
        } else {
            //lze vzit = false (dosahne = true)
            return "toto si nemůžeš vzít";

    }}

    /** 2) Sběr vajec z kurníku **/
    private String sebratZKurniku(){
        boolean klov = klov();//klov je true/false podle vrácené hodnoty z klov()
        if(!klov){
            return "Klov Klov" + "\n" + "slepice tě napadla!!";
        }else if(index >=3) {
            // jdou k dispozici jen 3 vejce, po jejich sesbírání dostane hráč už jen tuto hlášku
            return "Už jsi všechna vajička sesbíral!";
        }else if(plan.getInventar().jePlny()){
            //vejce jsou k dispozici, ale není je kam uložit
            return "Máš plný inventář, vrať se, až budeš mít místo";
        }
        System.out.println("HEREEEEEEEEEEEE KLOW");
        // hodnota indexu slouží k určení, které vejce se hráči přidá do inventáře
        //přidává se pomocí metody v herním plánu getListVejce()
        plan.getInventar().vlozVec(plan.getListVejce().get(this.index));

        this.index ++;
        return "ziskal jsi vejce";
        }

    /** Faktor náhody při sběru vajec - return True -> vejce může sebrat, return False -> nelze sebrat
        Metoda vygeneruje náhodnou hodnotu 0 - 9 **/
    private boolean klov(){
        Random rand = new Random();
        int randInt = rand.nextInt(10);
        if (randInt >=7){
            return false;
        }
        return true;
    }

    @Override
    public String getNazev() {
        return NAZEV;
    }

}