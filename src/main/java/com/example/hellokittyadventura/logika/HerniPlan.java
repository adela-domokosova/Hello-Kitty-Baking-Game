package com.example.hellokittyadventura.logika;


import java.util.*;

/**
 *  Class HerniPlan - třída představující mapu a stav adventury.
 * 
 *  Tato třída inicializuje prvky ze kterých se hra skládá:
 *  vytváří všechny prostory,
 *  propojuje je vzájemně pomocí východů 
 *  a pamatuje si aktuální prostor, ve kterém se hráč právě nachází.
 *
 *@author     Michael Kolling, Lubos Pavlicek, Jarmila Pavlickova
 *@version    pro školní rok 2016/2017
 */
public class HerniPlan {
    
    private Prostor aktualniProstor;
    private final Inventar inventar = new Inventar();//Vytváří hráčův inventář
    private final Map<String, Prostor> prostory = new HashMap<>();

    /**Vytváří se zde dvě Mapy, pomocí kterých se vyhodnocuje splnění vítězných podmínek
     * winList obsahuje předměty, které jsou podmínkou por vítězství
     * odevzdaneWinList obsahuje předměty, které hráč už odevzdal a jdou potřebné pro výhru**/
    private Map<String, Vec> winList= new HashMap<>();//Vytváří seznam předmětů potřebných pro výhru = winning conditions
    private Map<String,Vec> odevzdaneWinList = new HashMap<>();//Do odevzdaneWinList se přidávají předměty příkazem odevzdat
    
     /**
     *  Konstruktor který vytváří jednotlivé prostory a propojuje je pomocí východů.
     *  Jako výchozí aktuální prostor nastaví halu.
     */
    public HerniPlan() {
        zalozProstoryHry();

    }
    /**
     *  Vytváří jednotlivé prostory a propojuje je pomocí východů.
     *  Jako výchozí aktuální prostor nastaví domeček.
     */
    private void zalozProstoryHry() {
        // vytvářejí se jednotlivé prostory
        Prostor kuchyn = new Prostor("kuchyň","babička tu čeká na všechny suroviny", false);
        Prostor spizirna = new Prostor("spižírna", "mohli by se tu nacházet některé potřebné suroviny", false);
        Prostor zahrada = new Prostor("zahrada","roste zde obrovská jabloň a spousta babiččíných záhonů", false);
        Prostor mlyn = new Prostor("mlýn"," Tady přece musíš najít mouku! \n Je tady pusto, zkus 'ahoj', jestli ti někdo odpoví", false);
        Prostor kurnik = new Prostor("kurník","kvok kvok", true);
        Prostor none = new Prostor("none", "none", false);

        prostory.put(kuchyn.getNazev(), kuchyn);
        prostory.put(spizirna.getNazev(), spizirna);
        prostory.put(zahrada.getNazev(), zahrada);
        prostory.put(mlyn.getNazev(), mlyn);
        prostory.put(kurnik.getNazev(), kurnik);
        prostory.put(none.getNazev(), none);

        // přiřazují se průchody mezi prostory (sousedící prostory)
        kuchyn.setVychod(spizirna);
        kuchyn.setVychod(zahrada);
        spizirna.setVychod(kuchyn);
        zahrada.setVychod(kuchyn);
        zahrada.setVychod(mlyn);
        zahrada.setVychod(kurnik);
        mlyn.setVychod(zahrada);
        kurnik.setVychod(zahrada);


        aktualniProstor = kuchyn;  // hra začíná v Kuchyni


        //předměty a jejich umístění na mapě
        //spižírna
//        Vec mouka = new Vec("mouka", "mouka", true, false);
//        spizirna.pridatVec(mouka);
        Vec skorice = new Vec("skořice", "skořice", true, true);
        spizirna.pridatVec(skorice);
        Vec brambory = new Vec("brambory", "brambory", false, false);
        spizirna.pridatVec(brambory);
        Vec cukr = new Vec("cukr", "cukr", false, true);
        spizirna.pridatVec(cukr);

        //zahrada
        Vec jablon = new Vec("jabloň", "jabloň", false, true);
        zahrada.pridatVec(jablon);
        //Klíč a jablka se na herním plánu objevují až po zatřesení s jabloní
        //do té doby jsou uloženy v hráči nepřístupném prostoru
        Vec klic = new Vec("klíč", "klíč", true, true);
        none.pridatVec(klic);
        Vec jablka = new Vec("jablka", "jablka", true, true);
        none.pridatVec(jablka);

        //kurník
        Vec slepice = new Vec("slepice", "slepice", false,true);
        kurnik.pridatVec(slepice);
        //vejce jsou uložena v prostoru odděleném od hracího plánu, odkud se dostávají s pomocí getListVejce
        //při příkazu sebrat
        Vec vejce1= new Vec("vejce1", "vejce", true, true);
        none.pridatVec(vejce1);
        Vec vejce2= new Vec("vejce2", "vejce", true, true);
        none.pridatVec(vejce2);
        Vec vejce3= new Vec("vejce3", "vejce", true, true);
        none.pridatVec(vejce3);

        //Lednice a věci v ní
        Vec lednice = new Vec("lednice", "lednice", false, true);
        kuchyn.pridatVec(lednice);
        //následující předměty se nepřidávají do prostoru, ale do Map<String,Vec> veciUvnitr (atribut objektu Vec)
        Vec mleko = new Vec("mléko", "mléko", true, true);
        lednice.pridatVecUvnitr(mleko);
        Vec syr = new Vec("sýr","sýr", false, false);
        lednice.pridatVecUvnitr(syr);
        Vec jogurt = new Vec("jogurt", "jogurt", true, true);
        lednice.pridatVecUvnitr(jogurt);
        Vec kefir = new Vec("kefír", "kefír", true, false);
        lednice.pridatVecUvnitr(kefir);

        //mlýn
        Vec mouka = new Vec("mouka", "mouka", true,true);
        mlyn.pridatVec(mouka);

        /** Vložení předmětů do WinList
         *  tímto listem lze snadno měnit podmínky pro výhru hráče
         *  Slouží k porovnání předmětů, které hráč už odevzdal a které ještě potřebuje odevzdat**/
        pridatDoWinList(mleko);
        pridatDoWinList(skorice);
        pridatDoWinList(jablka);
        pridatDoWinList(vejce1);
        pridatDoWinList(vejce2);
        pridatDoWinList(vejce3);
        pridatDoWinList(mouka);
    }
    
    /**Metoda vrací odkaz na aktuální prostor, ve ktetém se hráč právě nachází.**/
    public Prostor getAktualniProstor() {
        return aktualniProstor;
    }

    /**Vrací aktuální stav hráčova inventáře**/
    public Inventar getInventar() {
        return this.inventar;
    }

    public Map<String, Prostor> getProstory(){
        return this.prostory;
    }

    /**Metoda pro nastavení nového prostoru, ve kterém se hráč nachází, při přecházení mezi prostory**/
    public void setAktualniProstor(Prostor prostor) {
       aktualniProstor = prostor;
    }

    /**List, který určuje podmínky pro vítězství hráče. Tedy jaké předměty musí odevzdat v průběhu hry**/
    public void pridatDoWinList(Vec vec){
        this.winList.put(vec.getNazev(), vec);
    }

    /**Přidat item, který hráč odevzdal do OdevzdaneWinList, který se pak porovnává s WinList**/
    public void pridatDoOdevzdaneWinList(Vec vec){
        this.odevzdaneWinList.put(vec.getNazev(), vec);
    }
    public Map<String, Vec> getOdevzdaneWinList() {return odevzdaneWinList;}
    public Map<String, Vec> getWinList() {
        return this.winList;
    }

    /**
     * Vrátí String s vypsanými předměty, které hráč ještě musí odevzdat
     * **/
    public String chybi() {
        String str = "";
        //elementy, ktere hráč ještě nezískal
        for (Vec vec : this.getWinList().values()) {
            if (!this.getOdevzdaneWinList().containsValue(vec)) {
                str += vec.getZobrazenyNazev() + ", ";
            }
        }
        return str;
    }

    /**
     * Vrátí String s vypsanými předměty, které hráč už odevzdal
     * **/
    public String odevzdane() {
        String str = "";
        Set<Vec> stejne = new HashSet<>(this.getWinList().values());
        stejne.retainAll(this.getOdevzdaneWinList().values());
        for (Vec vec : stejne) {
            str += vec.getZobrazenyNazev() + ", ";
        }
        return str;
    }

    /**Vejce jsou od začátku hry uloženy v "none" prostoru, do kterého hráč nemá přístup.
     * když je potřeba vejce dát hráči do inventaře, tato metoda vytovoří jejich list, ze kterého se pak
     * pomocí indexu přidávají hráči při příkazu sebrat.**/
    public List<Vec> getListVejce(){
        ArrayList<Vec> vejceArray= new ArrayList<>();
        vejceArray.add(this.getProstory().get("none").getVeci().get("vejce1"));
        vejceArray.add(this.getProstory().get("none").getVeci().get("vejce2"));
        vejceArray.add(this.getProstory().get("none").getVeci().get("vejce3"));
        return vejceArray;
    }
}
