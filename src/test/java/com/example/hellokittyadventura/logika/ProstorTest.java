package com.example.hellokittyadventura.logika;


import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/*******************************************************************************
 * Testovací třída ProstorTest slouží ke komplexnímu otestování
 * třídy Prostor
 *
 * @author    Jarmila Pavlíčková
 * @version   pro skolní rok 2016/2017
 */
public class ProstorTest
{
    //== Datové atributy (statické i instancí)======================================

    //== Konstruktory a tovární metody =============================================
    //-- Testovací třída vystačí s prázdným implicitním konstruktorem ----------

    //== Příprava a úklid přípravku ================================================

    /***************************************************************************
     * Metoda se provede před spuštěním každé testovací metody. Používá se
     * k vytvoření tzv. přípravku (fixture), což jsou datové atributy (objekty),
     * s nimiž budou testovací metody pracovat.
     */
    @BeforeEach
    public void setUp() {
    }

    /***************************************************************************
     * Úklid po testu - tato metoda se spustí po vykonání každé testovací metody.
     */
    @AfterEach
    public void tearDown() {
    }

    //== Soukromé metody používané v testovacích metodách ==========================

    //== Vlastní testovací metody ==================================================

    /**
     * Testuje, zda jsou správně nastaveny průchody mezi prostory hry. Prostory
     * nemusí odpovídat vlastní hře, 
     */
    @Test
    public  void testLzeProjit() {
        //tady byla nutná změna, protože jsem k obj prostor přidala bool je zamcena
        Prostor prostor1 = new Prostor("hala", "vstupní hala budovy VŠE na Jižním městě", false);
        Prostor prostor2 = new Prostor("bufet", "bufet, kam si můžete zajít na svačinku", false);
        prostor1.setVychod(prostor2);
        prostor2.setVychod(prostor1);
        assertEquals(prostor2, prostor1.vratSousedniProstor("bufet"));
        assertNull(prostor2.vratSousedniProstor("pokoj"));
    }

    @Test
    public void testVeci(){
        Prostor prostor = new Prostor("park", "park", false);
        Vec vec1 = new Vec("strom", "strom", true, true);
        Vec vec2 = new Vec("jezero", "jezero", false, true);
        prostor.pridatVec(vec1);
        prostor.pridatVec(vec2);
        assertTrue(prostor.jeZdeVec("strom"));
        assertTrue(prostor.jeZdeVec("jezero"));

    }
    @Test
    public void testZamknuta(){
        Prostor prostor = new Prostor("park", "park", true);
        Assertions.assertEquals(true, prostor.isJeZamcena());
        prostor.setJeZamcena();
        Assertions.assertEquals(false, prostor.isJeZamcena());
    }
    @Test
    public void testProsel(){
        Prostor prostor = new Prostor("park", "park", false);
        Assertions.assertEquals(false, prostor.isProsel());
        prostor.setProselTrue();
        Assertions.assertEquals(true, prostor.isProsel());
        prostor.setProselFalse();
        Assertions.assertEquals(false, prostor.isProsel());
    }
    @Test
    public void testOdebratVec(){
        Prostor prostor = new Prostor("park", "park", false);
        Vec vec1 = new Vec("strom", "strom", true, true);
        prostor.pridatVec(vec1);
        prostor.odebratVec("strom");
        Assertions.assertEquals(false, prostor.jeZdeVec("strom"));}

    @Test
    public void testSoudniProstory(){
        Prostor park = new Prostor("park", "park", false);
        Prostor obchod = new Prostor("obchod", "obchod", false);
        park.setVychod(obchod);
        Assertions.assertEquals(null, park.vratSousedniProstor("kuchyň"));
        Assertions.assertEquals("obchod", park.vratSousedniProstor("obchod").getNazev());
    }
}
