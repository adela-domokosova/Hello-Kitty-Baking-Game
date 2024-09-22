package com.example.hellokittyadventura.logika;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/*******************************************************************************
 * Testovací třída HraTest slouží ke komplexnímu otestování
 * třídy Hra
 *
 * @author    Jarmila Pavlíčková
 * @version  pro školní rok 2016/2017
 */
public class HraTest {
    private Hra hra1;

    Vec test1 = new Vec("test1", "test", true,true);
    Vec test2 = new Vec("test2", "test", true,true);
    Vec test3 = new Vec("test3", "test", true,true);
    Vec test4 = new Vec("klíč", "klíč", true,true);

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
        hra1 = new Hra();
    }

    /***************************************************************************
     * Úklid po testu - tato metoda se spustí po vykonání každé testovací metody.
     */
    @AfterEach
    public void tearDown() {
    }

    /***************************************************************************
     * Testuje průběh hry, po zavolání každěho příkazu testuje, zda hra končí
     * a v jaké aktuální místnosti se hráč nachází.
     * Při dalším rozšiřování hry doporučujeme testovat i jaké věci nebo osoby
     * jsou v místnosti a jaké věci jsou v batohu hráče.
     *
     */
    @Test
    public void testVyhra(){
        assertEquals(false, this.hra1.konecHry());
        this.hra1.getHerniPlan().getOdevzdaneWinList().putAll(this.hra1.getHerniPlan().getWinList());
        hra1.zpracujPrikaz("odevzdat");
        assertEquals(true, this.hra1.konecHry());
    }


    @Test
    public void testPrubehHry() {
        assertEquals("kuchyň", hra1.getHerniPlan().getAktualniProstor().getNazev());
        hra1.zpracujPrikaz("jdi zahrada");
        assertEquals(false, hra1.konecHry());
        assertEquals("zahrada", hra1.getHerniPlan().getAktualniProstor().getNazev());
        hra1.zpracujPrikaz("jdi mlýn");
        assertEquals(false, hra1.konecHry());
        assertEquals("mlýn", hra1.getHerniPlan().getAktualniProstor().getNazev());
        hra1.zpracujPrikaz("konec");
        assertEquals(true, hra1.konecHry());
    }

    @Test
    public void testPrubehHry2() {
        assertEquals("kuchyň", hra1.getHerniPlan().getAktualniProstor().getNazev());
        hra1.zpracujPrikaz("jdi zahrada");
        assertEquals(false, hra1.konecHry());
        assertEquals("zahrada", hra1.getHerniPlan().getAktualniProstor().getNazev());
        hra1.zpracujPrikaz("jdi kurník");
        assertEquals(false, hra1.konecHry());
        assertEquals("zahrada", hra1.getHerniPlan().getAktualniProstor().getNazev());
        hra1.zpracujPrikaz("konec");
        assertEquals(true, hra1.konecHry());
    }
    @Test
    public void testPrubehHry3() {
        assertEquals("kuchyň", hra1.getHerniPlan().getAktualniProstor().getNazev());
        hra1.zpracujPrikaz("jdi spižírna");
        assertEquals(false, hra1.konecHry());
        assertEquals("spižírna", hra1.getHerniPlan().getAktualniProstor().getNazev());
        hra1.zpracujPrikaz("jdi kuchyň");
        assertEquals(false, hra1.konecHry());
        assertEquals("kuchyň", hra1.getHerniPlan().getAktualniProstor().getNazev());
        hra1.zpracujPrikaz("konec");
        assertEquals(true, hra1.konecHry());
    }
    @Test
    public void testNelzeSebrat() {
        this.hra1.zpracujPrikaz("jdi zahrada");
        this.hra1.zpracujPrikaz("zatrast jabloň");
        this.hra1.zpracujPrikaz("seber jabloň");
        Assertions.assertEquals(true, this.hra1.getHerniPlan().getInventar().jePrazdny());
    }
    @Test
    public void testLzeSebrat() {
        this.hra1.zpracujPrikaz("jdi spižírna");
        this.hra1.zpracujPrikaz("seber skořice");
        this.hra1.zpracujPrikaz("seber brambory");
        this.hra1.zpracujPrikaz("seber cukr");
        this.hra1.zpracujPrikaz("seber mouka");
        Assertions.assertEquals(true, this.hra1.getHerniPlan().getInventar().getVeci().containsKey("skořice"));
        Assertions.assertEquals(false, this.hra1.getHerniPlan().getInventar().getVeci().containsKey("brambory"));
        Assertions.assertEquals(false, this.hra1.getHerniPlan().getInventar().getVeci().containsKey("cukr"));
        Assertions.assertEquals(false, this.hra1.getHerniPlan().getInventar().getVeci().containsKey("mouka"));
    }

    @Test
    public void testOtevritLednice(){
        this.hra1.zpracujPrikaz("seber mléko");
        Assertions.assertEquals(false, this.hra1.getHerniPlan().getInventar().getVeci().containsKey("mléko"));
        this.hra1.zpracujPrikaz("otevrit lednice");
        this.hra1.zpracujPrikaz("seber mléko");
        Assertions.assertEquals(true, this.hra1.getHerniPlan().getInventar().getVeci().containsKey("mléko"));
    }


    @Test
    public void testPlnyInventar(){
        this.hra1.getHerniPlan().getInventar().vlozVec(this.test1);
        this.hra1.getHerniPlan().getInventar().vlozVec(this.test2);
        this.hra1.getHerniPlan().getInventar().vlozVec(this.test3);
        this.hra1.getHerniPlan().getInventar().vlozVec(this.test4);

        this.hra1.zpracujPrikaz("jdi spižírna");
        this.hra1.zpracujPrikaz("seber skořice");
        Assertions.assertEquals(true, this.hra1.getHerniPlan().getInventar().jePlny());
        Assertions.assertEquals(false, this.hra1.getHerniPlan().getInventar().getVeci().containsKey("skořice"));
    }
    @Test
    public void testVecAtributy(){
        Vec test = new Vec("test1", "test", true,true);
        Assertions.assertEquals("test1", test.getNazev());
        Assertions.assertEquals("test", test.getZobrazenyNazev());

        assertTrue(test.isLzeVzit());
        assertTrue(test.isDosahne());
        assertTrue(!test.isProzkoumal());
        assertTrue(test.getVeciUvnitr().isEmpty());
    }

    @Test
    public void testOdemknout(){
        this.hra1.zpracujPrikaz("jdi zahrada");
        this.hra1.getHerniPlan().getInventar().vlozVec(this.test4);

        Assertions.assertEquals(true, this.hra1.getHerniPlan().getInventar().getVeci().containsKey("klíč"));
        this.hra1.zpracujPrikaz("odemknout kurník");
        this.hra1.zpracujPrikaz("jdi kurník");
        Assertions.assertEquals("kurník", this.hra1.getHerniPlan().getAktualniProstor().getNazev());
    }

    @Test
    public void testOdevzdat(){
        this.hra1.getHerniPlan().getInventar().vlozVec(this.test1);
        this.hra1.getHerniPlan().getInventar().vlozVec(this.test4);
        this.hra1.getHerniPlan().getInventar().vlozVec(this.test2);

        this.hra1.getHerniPlan().pridatDoWinList(this.test2);
        this.hra1.zpracujPrikaz("odevzdat");
        Assertions.assertEquals(true, this.hra1.getHerniPlan().getOdevzdaneWinList().containsKey("test2"));
        Assertions.assertEquals(true, this.hra1.getHerniPlan().getInventar().getVeci().containsKey("klíč"));
        Assertions.assertEquals(false, this.hra1.getHerniPlan().getInventar().getVeci().containsKey("test1"));
    }

    @Test
    public void testVecChybiOdevzdat(){
        Assertions.assertEquals("jablka, skořice, vejce, vejce, mléko, vejce, mouka, ", this.hra1.getHerniPlan().chybi());
        this.hra1.getHerniPlan().pridatDoWinList(this.test2);
        this.hra1.getHerniPlan().pridatDoWinList(this.test3);
        this.hra1.getHerniPlan().pridatDoOdevzdaneWinList(this.test2);
        Assertions.assertEquals(true, this.hra1.getHerniPlan().getOdevzdaneWinList().containsKey("test2"));
        Assertions.assertEquals(false, this.hra1.getHerniPlan().getOdevzdaneWinList().containsKey("test3"));
        Assertions.assertEquals("test, ", this.hra1.getHerniPlan().odevzdane());
    }
    @Test
    public void testAhojMlýn(){
        this.hra1.zpracujPrikaz("jdi zahrada");
        this.hra1.zpracujPrikaz("jdi mlýn");
        this.hra1.getHerniPlan().getAktualniProstor().setProselTrue();
        Assertions.assertEquals("Nikdo tady už není, vrať se jindy",  this.hra1.zpracujPrikaz("ahoj"));
    }
    @Test
    public void testAhojJinde(){
        Assertions.assertEquals("Zkus to jinde",  this.hra1.zpracujPrikaz("ahoj"));

    }
    @Test
    public void testPomoc(){
        Assertions.assertEquals("Zkus otevřít lednici", this.hra1.zpracujPrikaz("pomoc"));
        this.hra1.zpracujPrikaz("jdi spižírna");
        Assertions.assertEquals("Zkus se podívat po skořici", this.hra1.zpracujPrikaz("pomoc"));
        this.hra1.zpracujPrikaz("jdi kuchyň");
        this.hra1.zpracujPrikaz("jdi zahrada");
        Assertions.assertEquals("Najdi jablka a klíč", this.hra1.zpracujPrikaz("pomoc"));
        this.hra1.zpracujPrikaz("jdi mlýn");
        Assertions.assertEquals("Někde tu musí být mouka!", this.hra1.zpracujPrikaz("pomoc"));
    }
}
