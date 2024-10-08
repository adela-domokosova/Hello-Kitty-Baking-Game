package com.example.hellokittyadventura.logika;

import com.example.hellokittyadventura.main.Pozorovatel;
import com.example.hellokittyadventura.main.PredmetPozorovani;
import com.example.hellokittyadventura.main.ZmenaHry;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Inventar implements PredmetPozorovani {
    private Map<String, Vec> veci= new HashMap<>();
    private Map<ZmenaHry, Set<Pozorovatel>> seznamPozorovatelu = new HashMap<>();



    //kontrola max 4 věci v inventaři
    public Boolean jePlny() {
        if(veci.size()== 4){
            return true;
        }
        return false;
    }
    public Boolean jePrazdny(){
        if(veci.isEmpty()){
            return true;
        }
        return false;
    }

    public Map<String, Vec> getVeci() {
        return this.veci;
    }


    /** Metoda vrací výpis atributů "ZobrazenyNazev" věcí v inventáři **/
    public String vypsatObsah(){
        String str = "Inventar obsahuje: ";
        for(Vec vec :veci.values()){
            str += vec.getZobrazenyNazev() + ", ";
        }
        return str;
    }


    @Override
    public String toString() {
        return "Inventar :" +
                "veci=" + veci +
                '}';
    }


    /** Metoda vkládá věc do inventáře **/
    public void vlozVec(Vec vec){
        veci.put(vec.getNazev(), vec);
        upozorneniPozorovatele(ZmenaHry.ZMENA_INVENTARE);
    }
    /** Metoda odebírá věc z inventáře **/
    public String odeberVec(String vec){
        if(!jePrazdny()){
            veci.remove(vec);
            upozorneniPozorovatele(ZmenaHry.ZMENA_INVENTARE);
            return vec;
        }
        return null;
    }

    //priklad z jine tridy
//    public void setAktualniProstor(Prostor prostor) {
//        aktualniProstor = prostor;
//        upozorneniPozorovatele(ZmenaHry.ZMENA_MISTNOSTI);
//    }
    private void upozorneniPozorovatele(ZmenaHry zmenaHry) {
        for(Pozorovatel pozorovatel : seznamPozorovatelu.get(zmenaHry)){
            pozorovatel.aktualizuj();
        }
    }

    @Override
    public void registruj(ZmenaHry zmenaHry, Pozorovatel pozorovatel) {
        seznamPozorovatelu.get(zmenaHry).add(pozorovatel);
    }
}
