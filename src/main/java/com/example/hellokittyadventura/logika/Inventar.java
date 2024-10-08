package com.example.hellokittyadventura.logika;

import java.util.HashMap;
import java.util.Map;

public class Inventar {
    private Map<String, Vec> veci= new HashMap<>();


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
    }
    /** Metoda odebírá věc z inventáře **/
    public String odeberVec(String vec){
        if(!jePrazdny()){
            veci.remove(vec);
            return vec;
        }
        return null;
    }
}
