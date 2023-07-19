# GMDIBende README.md
*Plugin utile per la creazione di bende in gioco.*

# 1. Lista Type TITLE
```YAML
TITLE
ACTION-BAR
```
# 2. Spiegazione permissi
```YAML
Bandages:
  default:
    Time: 5
    Permission-use: "none"
    Item-amount: 1
    Item-name: "&fBenda"
    Item-type: PAPER
    Item-model-data: 10051
    Health: 5
    Hunger: 1
    Item-lore:
      - "&7Click destro per usarla!"
      - "&bPlugin sviluppato da &e@GabryOsas / @GMDIDevelopment"
```
*In questo caso il permesso non esiste perchè equivale a "none"*
# 3. FAQ
*Ogni benda deve avere un nome diverso altrimenti il plugin non funzionerà correttamente.*

*Si prega di usare una benda alla volta altrimenti i messaggi possono risultare accavallati.*
# 4. API UTILIZZATE
```xml
        <repository>
            <id>jitpack.io</id>
            <url>https://jitpack.io</url>
        </repository>
        <dependency>
            <groupId>commons-io</groupId>
            <artifactId>commons-io</artifactId>
            <version>2.11.0</version>
        </dependency>
```
