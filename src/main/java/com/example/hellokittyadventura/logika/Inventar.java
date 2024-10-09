package com.example.hellokittyadventura.logika;

import com.example.hellokittyadventura.main.Pozorovatel;
import com.example.hellokittyadventura.main.PredmetPozorovani;
import com.example.hellokittyadventura.main.ZmenaHry;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Inventar implements PredmetPozorovani {
    private Map<String, Vec> veci= new HashMap<>();
    private Map<ZmenaHry, Set<Pozorovatel>> seznamPozorovatelu = new HashMap<>();

    public Inventar() {
        for(ZmenaHry zmenaHry : ZmenaHry.values()){
            seznamPozorovatelu.put(zmenaHry, new HashSet<>());
        }
    }

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
            return vec;
        }
        return null;
    }

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
