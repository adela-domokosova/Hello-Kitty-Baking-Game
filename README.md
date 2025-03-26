# Hello-Kitty-Baking-Game

![HK/screen](https://adela-domokosova.github.io/pictures/HK-screen1.png)

##Vznik
Hra vynikala v průběhu dvou semestrů
4IT101	Programování v Javě
V tomto předmětu vznikl návrh hry, příběh a textová verze hry s pomocí základních funkcí javy.
4IT115	Softwarové inženýrství
Zde byla předchozí textová verze upravena a byl přidán interface, který jsem vytvářela pomocí javaFX.


Program obsahuje jednu úroveň jednoduché hry, ve které hráč (jako Hello Kitty) hledá na mapě suroviny, které jsou potřeba k upečení jablečného koláče. Hrou provází gamemaster (babička Hello Kitty), který poskytuje nápovědu, komentář k aktualnímu prostoru nebo k použitým příkazům.



##Unit testy
Součástí kódu jsou unit testy, které jsou psány pomocí JUnit5. Testovány jsou třídy spojené s logikou hry, tedy zejména příkazy a herní plán.
Cíle pro obhajobu práce bylo dosáhnout pokrytí >=70% metod a >=70% řádků kódu, což je v naprosté většině tříd splněno. 


##Grafické rozhraní
Grafické rozhraní hry je vytvořeno pomocí JavaFX a Scene Builderu. Struktura hlavního okna je definována v souboru home.fxml, který obsahuje rozvržení a prvky UI aje doplněn CSS prvky. Tento soubor je načítán pomocí FXMLLoader a propojen s HomeControllerem, který zajišťuje interaktivitu. Přestože hra byla v první fázy hratelná pouze v terminálu, nyní je díky interfacu plně hratelná pomocí myši.

###Návrhový vzor Observer
Pro aktualizaci UI podle průběhu hry byl v HomeController využit návrhový vzor Observer. Tento vzor umožňuje dynamicky reagovat na změny ve stavu hry – například při posunu mezi prostory nebo změně inventáře se odpovídající prvky UI automaticky aktualizují. Dynamicky se tak zobrazují například možné východy z aktuálního prostoru, předměty v prostoru, obsah hráčova batohu a poloha na mapě.



##Co jsem se naučila
Celý projekt utvrdil mé znalosti základních funkcí javy. Také jsem se naučila pracovat s JavaFX pro vytvoření herního grafického rozhraní.
