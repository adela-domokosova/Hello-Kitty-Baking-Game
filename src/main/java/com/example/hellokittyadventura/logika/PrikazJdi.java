package com.example.hellokittyadventura.logika;

/** Změny v této třídě
 *  Při opuštění prostoru "mlýn" - mění se atribut prostoru "prosel" true -> false
 *  kontrola zmačených prostor, do terých se hráč snaží vstoupit (atribut jeZamcena)
 * **/

/**
 *  Třída PrikazJdi implementuje pro hru příkaz jdi.
 *  Tato třída je součástí jednoduché textové hry.
 *  
 *@author     Jarmila Pavlickova, Luboš Pavlíček
 *@version    pro školní rok 2016/2017
 */
public class PrikazJdi implements IPrikaz {
    public static final String NAZEV = "jdi";
    private HerniPlan plan;
    
    /**
    *  Konstruktor třídy
    *  
    *  @param plan herní plán, ve kterém se bude ve hře "chodit" 
    */    
    public PrikazJdi(HerniPlan plan) {
        this.plan = plan;
    }

    /**
     *  Provádí příkaz "jdi". Zkouší se vyjít do zadaného prostoru. Pokud prostor
     *  existuje, vstoupí se do nového prostoru. Pokud zadaný sousední prostor
     *  (východ) není, vypíše se chybové hlášení.
     *
     *@param parametry - jako  parametr obsahuje jméno prostoru (východu),
     *                         do kterého se má jít.
     *@return zpráva, kterou vypíše hra hráči
     */ 
    @Override
    public String provedPrikaz(String... parametry) {
        if (parametry.length == 0) {
            // pokud chybí druhé slovo (sousední prostor), tak ....
            return "Kam mám jít? Musíš zadat jméno východu";
        }

        String smer = parametry[0];

        // zkoušíme přejít do sousedního prostoru
        Prostor sousedniProstor = plan.getAktualniProstor().vratSousedniProstor(smer);

        if (sousedniProstor == null) {
            return "Tam se odsud jít nedá!";
        } else {

            if(sousedniProstor.isJeZamcena()){
                //kontrola jestli je místnost zamknutá
                return "musíš tuto místnost nejdříve otevřít";
            }else{
                //zajistí změnu atributu prostor prosel = false v prostoru mlýn
                //důležité pro příkaz Ahoj
                if(plan.getAktualniProstor().getNazev()=="mlýn"){
                    plan.getAktualniProstor().setProselFalse();
                }
                plan.setAktualniProstor(sousedniProstor);
                return sousedniProstor.dlouhyPopis();
            }

        }
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
